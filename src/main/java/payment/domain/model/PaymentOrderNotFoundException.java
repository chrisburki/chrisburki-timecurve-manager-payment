package payment.domain.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaymentOrderNotFoundException extends RuntimeException {

  private PaymentOrderNotFoundException(String ident) {
    super("Payment Order with id " + ident + " does not exist");
  }

  public static PaymentOrderNotFoundException paymentNotFound(String ident) {
    return new PaymentOrderNotFoundException(ident);
  }

}
