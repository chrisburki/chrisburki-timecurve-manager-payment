package payment.infrastructure.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.application.PaymentOrderService;
import payment.domain.api.PaymentOrderCommand;
import payment.domain.api.PaymentOrderExternalEvent;
import payment.infrastructure.persistence.PaymentOrderEntity;

@RestController
@RequestMapping("/payments")
public class PaymentOrderController {

  private final PaymentOrderService orderService;

  public PaymentOrderController(
      PaymentOrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders")
  ResponseEntity<List<PaymentOrderEntity>> listOrders() {
    return new ResponseEntity<>(new ArrayList<>(orderService.listOrders()), HttpStatus.OK);
  }

  @GetMapping("/orders/{id}")
  ResponseEntity<PaymentOrderEntity> getOrder(@PathVariable("id") String id) {
    return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
  }

  @PostMapping("/orders")
  ResponseEntity<PaymentOrderExternalEvent> createOrder(
      @RequestBody PaymentOrderCommand paymentOrderCommand) {
    return new ResponseEntity<>(orderService.processPaymentCommand(paymentOrderCommand), HttpStatus.OK);
  }
}
