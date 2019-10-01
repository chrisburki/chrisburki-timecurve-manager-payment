package payment.domain.api;

public enum PaymentBookingItemType {
  BASIC("basic", "basic", "Basic", false, true, true),
  IFRS("ifrs", "ifrs", "IFRS", true, false, false),
  AVERAGE("average", "avg", "Average Book Values", false, true, false),
  FIFO("fifo", "fifo", "First In First Out", true, true, false);

  private final Object[] values;

  PaymentBookingItemType(Object... vals) {
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

  public Boolean hasItems() {
    return (Boolean) values[3];
  }

  public Boolean hasDate2() {
    return (Boolean) values[4];
  }

  public Boolean buildApprovedBalance() {
    return (Boolean) values[5];
  }
}
