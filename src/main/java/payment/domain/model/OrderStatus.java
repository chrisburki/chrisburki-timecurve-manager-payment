package payment.domain.model;

import payment.domain.api.PaymentBookingStatus;

public enum OrderStatus {
  OPEN_PEND("open_pend", "o_p", "Open Pending", PaymentBookingStatus.OPEN),
  OPEN("open", "o", "Open", PaymentBookingStatus.OPEN),
  APPROVED_PEND("approved_pend", "a_p", "Approved Pending", PaymentBookingStatus.APPROVED),
  APPROVED("approved", "a", "Approved", PaymentBookingStatus.APPROVED),
  DONE_PEND("done_pend", "d_p", "Done Pending", PaymentBookingStatus.BOOKED),
  DONE("done", "d", "Done", PaymentBookingStatus.BOOKED);

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
}
