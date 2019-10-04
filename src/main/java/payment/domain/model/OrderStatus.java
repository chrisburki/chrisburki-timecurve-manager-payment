package payment.domain.model;

import payment.domain.api.PaymentBookingStatus;

public enum OrderStatus {
  OPEN("open", "o", "Open", PaymentBookingStatus.OPEN, null, false),
  OPEN_PEND("open_pend", "o_p", "Open Pending", PaymentBookingStatus.OPEN, OrderStatus.OPEN, true),
  APPROVED("approved", "a", "Approved", PaymentBookingStatus.APPROVED, null, false),
  APPROVED_PEND("approved_pend", "a_p", "Approved Pending", PaymentBookingStatus.APPROVED,
      OrderStatus.APPROVED, true),
  DONE("done", "d", "Done", PaymentBookingStatus.BOOKED, null, false),
  DONE_PEND("done_pend", "d_p", "Done Pending", PaymentBookingStatus.BOOKED, OrderStatus.DONE,
      true);

  private final Object[] values;

  OrderStatus(Object... vals) {
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

  public PaymentBookingStatus bookingStatus() {
    return (PaymentBookingStatus) values[3];
  }

  public OrderStatus processedStatus() {
    return (OrderStatus) values[4];
  }

  public Boolean statusIsPend() {
    return (Boolean) values[5];
  }
}
