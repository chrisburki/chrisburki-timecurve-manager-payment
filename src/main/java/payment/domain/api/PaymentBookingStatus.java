package payment.domain.api;

public enum PaymentBookingStatus {
  OPEN("open", "o", "Open"),
  APPROVED("approved", "a", "Approved"),
  BOOKED("booked", "b", "Booked");

  private final Object[] values;

  PaymentBookingStatus(Object... vals) {
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
}
