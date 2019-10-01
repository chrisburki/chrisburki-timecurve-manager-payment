package payment.application;

import static payment.domain.model.PaymentOrderNotFoundException.paymentNotFound;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payment.domain.PaymentOrderMessageOutHdl;
import payment.domain.PaymentSpi;
import payment.domain.api.PaymentBookingCommand;
import payment.domain.api.PaymentBookingDimension;
import payment.domain.api.PaymentBookingItemType;
import payment.domain.api.PaymentBookingStatus;
import payment.domain.api.PaymentOrderCommand;
import payment.domain.api.PaymentOrderExternalEvent;
import payment.domain.model.PositionDetail;
import payment.infrastructure.persistence.PaymentOrderEntity;
import payment.infrastructure.persistence.PaymentOrderEntityRepository;

@Service
@Slf4j
public class PaymentOrderService {

  private final PaymentOrderEntityRepository repository;
  private final PaymentSpi paymentSpi;
  private final PaymentOrderMessageOutHdl messageOutHdl;

  public PaymentOrderService(
      PaymentOrderEntityRepository repository, PaymentSpi paymentSpi,
      PaymentOrderMessageOutHdl messageOutHdl) {
    this.repository = repository;
    this.paymentSpi = paymentSpi;
    this.messageOutHdl = messageOutHdl;
  }

  //
  // helper
  //
  <T> T nvl(T arg0, T arg1) {
    return (arg0 == null) ? arg1 : arg0;
  }

  /*
   * Search for existing payment based on id - primary key
   * *****************************************************
   * */
  public PaymentOrderEntity getOrderById(String id) {
    return repository.findById(id).orElseThrow(() -> paymentNotFound(id));
  }

  /*
   * list Orders
   * ***********
   * */
  public Collection<PaymentOrderEntity> listOrders() {
    return repository.findAll();
  }

  //
  // Command
  //
  private PaymentOrderEntity addOrder(PaymentOrderEntity order) {

    if (order.getId() == null) {
      order.setId(UUID.randomUUID().toString());
    }
    return repository.save(order);
  }

  private void sendPaymentBookingCommand(PaymentOrderEntity order) {
    Integer payerRowNr = 1;
    Integer auxRowNr = 2;
    PaymentBookingCommand paymentBookingCommand = PaymentBookingCommand.builder()
        .extId(null)
        .sequenceNr(null)
        .orderId(order.getId())
        .tenantId(order.getTenantId())
        .dimension(PaymentBookingDimension.SUBLEDGER)
        .status(order.getOrderStatus().bookingStatus())
        .useCase("Domestic Payment")
        .date1(LocalDate.now())
        .date2(order.getValueDate())
        .gsn(order.getGsn())
        .build();
    paymentBookingCommand
        .createBookingItem(payerRowNr, order.getPayer_pos_id(), PaymentBookingItemType.BASIC, 1L,
            order.getAmount().negate(), null, null, order.getAmount().negate(), null, null);
    paymentBookingCommand
        .createBookingItem(auxRowNr, order.getAuxiliary_pos_id(), PaymentBookingItemType.BASIC, 1L,
            order.getAmount(), null, null, order.getAmount(), null, null);
    messageOutHdl.sendBookingCommand(paymentBookingCommand);

  }

  /*
   * Process Command
   * ***************
   * */
  public PaymentOrderExternalEvent processPaymentCommand(PaymentOrderCommand paymentOrderCommand) {
    //@todo: check for duplicates

    // auxiliary position (may replicate from other SCS)
    final String auxiliaryContainerId = "AUX_CONT_PAY_1";

    PaymentOrderEntity payment;

    // find existing payment
    if (paymentOrderCommand.getId() != null) {
      //@todo: add checks for updates, what is allowed what not
      payment = getOrderById(paymentOrderCommand.getId());
      payment.setOrderStatus(payment.getOrderStatus());
      payment.setAmount(nvl(paymentOrderCommand.getAmount(), payment.getAmount()));
      payment
          .setCurrency_iso(nvl(paymentOrderCommand.getCurrency_iso(), payment.getCurrency_iso()));

    } else {
      // create new payment

      // get or create auxiliary position
      PositionDetail auxiliaryPosition = paymentSpi
          .addPosition(paymentOrderCommand.getTenantId(), auxiliaryContainerId,
              paymentOrderCommand.getCurrency_iso());
      // get money account position
      PositionDetail payerPosition = paymentSpi
          .getMoneyAccount(paymentOrderCommand.getTenantId(), paymentOrderCommand.getPayer_iban());

      // create or update order
      payment = new PaymentOrderEntity(
          paymentOrderCommand.getTenantId(),
          paymentOrderCommand.getOrderStatus().commandModelStatus(),
          paymentOrderCommand.getOrderType(),
          paymentOrderCommand.getTransactionDate(),
          paymentOrderCommand.getValueDate(),
          paymentOrderCommand.getPayer_iban(),
          payerPosition.getPositionId(),
          paymentOrderCommand.getBeneficiary_iban(),
          paymentOrderCommand.getBeneficiary_address(),
          auxiliaryPosition.getPositionId(),
          paymentOrderCommand.getAmount(),
          paymentOrderCommand.getCurrency_iso()
      );
    }

    // store order
    PaymentOrderEntity paymentResult = addOrder(payment);

    // send messages: booking
    sendPaymentBookingCommand(paymentResult);

    return PaymentOrderExternalEvent.builder()
        .id(paymentResult.getId())
        .orderStatus(paymentResult.getOrderStatus())
        .gsn(paymentResult.getGsn())
        .build();
  }
}
