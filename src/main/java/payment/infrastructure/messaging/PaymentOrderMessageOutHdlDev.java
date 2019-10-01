package payment.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import payment.domain.PaymentOrderMessageOutHdl;
import payment.domain.api.PaymentBookingCommand;

@Component
@Slf4j
@Profile("dev")
public class PaymentOrderMessageOutHdlDev implements PaymentOrderMessageOutHdl {

  @Override
  public void sendBookingCommand(PaymentBookingCommand event) {
    log.info("Send Booking Command" + event.toString());
  }
}
