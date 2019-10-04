package payment.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import payment.application.PaymentOrderService;
import payment.domain.PaymentOrderMessageInHdl;
import payment.domain.PaymentOrderMessageOutHdl;
import payment.domain.api.PaymentBookingReplyEvent;
import payment.domain.api.PaymentOrderExternalEvent;

@Component
@Slf4j
@Profile("int")
public class PaymentOrderMessageInHdlInt implements PaymentOrderMessageInHdl {

  private final PaymentOrderService paymentOrderService;

  public PaymentOrderMessageInHdlInt(PaymentOrderService paymentOrderService) {
    this.paymentOrderService = paymentOrderService;
  }

  @KafkaListener(topics = "${spring.kafka.topic.payment-reply-booking}")
  @Override
  public void receiveBookingReplyEvent(@Payload PaymentBookingReplyEvent event, @Headers MessageHeaders headers) {
    System.out.println("Received Int Booking Reply Message: " + event.toString());
    String correlationId = headers.get(KafkaHeaders.CORRELATION_ID).toString();
    PaymentOrderExternalEvent externalEvent = paymentOrderService.procecssBookingReply(event);
  }
}