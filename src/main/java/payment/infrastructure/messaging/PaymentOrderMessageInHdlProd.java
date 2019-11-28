package payment.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.pubsub.v1.PubsubMessage;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import payment.application.PaymentOrderService;
import payment.domain.PaymentOrderMessageInHdl;
import payment.domain.api.PaymentBookingReplyEvent;
import payment.domain.api.PaymentOrderExternalEvent;

@Component
@Slf4j
@Profile("prod")
public class PaymentOrderMessageInHdlProd implements PaymentOrderMessageInHdl {

  private final PaymentOrderService paymentOrderService;

  public PaymentOrderMessageInHdlProd(PaymentOrderService paymentOrderService) {
    this.paymentOrderService = paymentOrderService;
  }

  @Override
  public void receiveBookingReplyEvent(PaymentBookingReplyEvent event, MessageHeaders headers) {
    log.info("send receiveBookingReplyEvent message");
  }

  protected void consumeCommand(BasicAcknowledgeablePubsubMessage command) {
    // extract wrapped message
    PubsubMessage message = command.getPubsubMessage();
    Map<String, String> attributeMap = message.getAttributesMap();

    String correlationIdVal = "";

    if (attributeMap.containsKey(PaymentBookingProdConfig.correlationId)) {
      correlationIdVal = attributeMap.get(PaymentBookingProdConfig.correlationId);
    }

    // process message
    String messageJson = message.getData().toStringUtf8();
    log.info("message received: corrId: "+ correlationIdVal + " msg: " + messageJson);

    // acknowledge that message was received
    command.ack();

    // converted into domain event
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      PaymentBookingReplyEvent bookingReply = objectMapper
          .readValue(messageJson, PaymentBookingReplyEvent.class);
      PaymentOrderExternalEvent externalEvent = paymentOrderService.procecssBookingReply(bookingReply);

    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Implementation of a {@link Consumer} functional interface.
   */
  public Consumer<BasicAcknowledgeablePubsubMessage> consumer() {
    return basicAcknowledgeablePubsubMessage -> consumeCommand(basicAcknowledgeablePubsubMessage);
  }
}
