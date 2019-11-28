package payment.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import payment.domain.api.PaymentBookingCommand;
import payment.domain.api.PaymentOrderExternalEvent;

public interface PaymentOrderMessageOutHdl {

  /*Publish Domain Event to message broker*/
//  void sendDomainEvent(PaymentOrderDomainEvent event);

  /*Publish External Event to message broker*/
//  void sendExternalEvent(PaymentOrderExternalEvent event);

  /*Send Booking Command to message broker*/
  void sendBookingCommand(PaymentBookingCommand event);

}
