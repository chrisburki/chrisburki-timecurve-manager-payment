package payment.domain;

import org.springframework.messaging.MessageHeaders;
import payment.domain.api.PaymentBookingReplyEvent;
import payment.domain.api.PaymentOrderCommand;

public interface PaymentOrderMessageInHdl {

  /*Receive command Message*/
//  void receiveCommand(PaymentOrderCommand event, MessageHeaders headers);

  /*Receive Booking Reply Message*/
  void receiveBookingReplyEvent(PaymentBookingReplyEvent event, MessageHeaders headers);

}
