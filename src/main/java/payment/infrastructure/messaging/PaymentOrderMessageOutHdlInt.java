package payment.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import payment.domain.PaymentOrderMessageOutHdl;
import payment.domain.api.PaymentBookingCommand;

@Component
@Slf4j
@Profile("int")
public class PaymentOrderMessageOutHdlInt implements PaymentOrderMessageOutHdl {

  private final KafkaTemplate<String, PaymentBookingCommand> kafkaTemplate;

  @Value(value = "${spring.kafka.topic.command-booking}")
  private String bookingCommandTopic;

  @Value(value = "${spring.kafka.topic.payment-reply-booking}")
  private String replyBookingTopic;

  public PaymentOrderMessageOutHdlInt(
      KafkaTemplate<String, PaymentBookingCommand> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  //see: https://callistaenterprise.se/blogg/teknik/2018/10/26/synchronous-request-reply-over-kafka/

  @Override
  public void sendBookingCommand(PaymentBookingCommand event) {
    log.debug("Send Booking Command" + event.toString());
//    Object replyChannel = get(replyBookingTopic);
    Message<PaymentBookingCommand> message = MessageBuilder
        .withPayload(event)
        .setHeader(KafkaHeaders.TOPIC, bookingCommandTopic)
//        .setReplyChannel(replyBookingTopic)
        .build();
    kafkaTemplate.send(message);
  }
}
