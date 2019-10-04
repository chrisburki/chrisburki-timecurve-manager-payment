package payment.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import payment.domain.PaymentOrderMessageInHdl;
import payment.domain.api.PaymentBookingReplyEvent;

@Component
@Slf4j
@Profile("dev")
public class PaymentOrderMessageInHdlDev implements PaymentOrderMessageInHdl {

  @Override
  public void receiveBookingReplyEvent(PaymentBookingReplyEvent event, MessageHeaders headers) {
    log.info("send receiveBookingReplyEvent message");
  }
}
