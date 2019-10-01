package payment.domain.api;

import payment.domain.model.OrderStatus;

public enum PaymentOrderStatus {
  OPEN("open", "o", "Open", OrderStatus.OPEN_PEND),
  APPROVED("approved", "a", "Approved", OrderStatus.APPROVED_PEND),
  DONE("done", "d", "Done", OrderStatus.DONE_PEND);

  private final Object[] values;

  PaymentOrderStatus(Object... vals) {
    values = vals;
  }

  public String intl_id() {
    return (String) values[0];
  }

  public String abbreviation() {
    return (String) values[1];
  }

  public String description() {
    return (String) values[2];
  }

  public OrderStatus commandModelStatus() {
    return (OrderStatus) values[3];
  }

}

