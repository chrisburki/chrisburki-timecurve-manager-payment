package payment.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import payment.domain.PaymentOrderMessageOutHdl;
import payment.domain.api.PaymentBookingCommand;

@Component
@Slf4j
@Profile("prod")
public class PaymentOrderMessageOutHdlProd implements PaymentOrderMessageOutHdl {

  @Value(value = "${topic.command-booking}")
  private String topicCommand;

  private final PubSubTemplate pubSubTemplate;

  public PaymentOrderMessageOutHdlProd(
      PubSubTemplate pubSubTemplate) {
    this.pubSubTemplate = pubSubTemplate;
  }

  @Override
  public void sendBookingCommand(PaymentBookingCommand event) {
    log.info("Publish Booking Command ==[{}]: rplyT:" + PaymentBookingProdConfig.topicReplyBooking
        + " corrId: " + event.getOrderId(), event);

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      PubsubMessage pubsubMessage =
          PubsubMessage.newBuilder()
              .setData(ByteString.copyFromUtf8(objectMapper.writeValueAsString(event)))
              .putAttributes(PaymentBookingProdConfig.replyTopic,
                  PaymentBookingProdConfig.topicReplyBooking)
              .putAttributes(PaymentBookingProdConfig.correlationId, event.getOrderId())
              .build();
      log.info("Publish Booking Command Payload: ");
      pubSubTemplate.publish(topicCommand, pubsubMessage);
    } catch (JsonParseException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
    Message<PaymentBookingCommand> message = MessageBuilder
        .withPayload(event)
        .setHeader(PaymentBookingProdConfig.replyTopic, PaymentBookingProdConfig.topicReplyBooking)
        .setHeader(PaymentBookingProdConfig.correlationId, event.getOrderId())
        .build();
*/

  }
}
