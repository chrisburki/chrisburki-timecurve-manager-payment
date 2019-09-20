package payment.domain.model;

public enum OrderStatus {
  OPEN_PEND("open_pend", "o_p", "Open Pending"),
  OPEN("open", "o", "Open"),
  APPROVED_PEND("approved_pend", "a_p", "Approved Pending"),
  APPROVED("approved", "a", "Approved"),
  DONE_PEND("done_pend","d_p","Done Pending"),
  DONE("done","d","Done");

  private final Object[] values;

  OrderStatus(Object... vals) {
    values = vals;
  }

public String intl_id() {return (String) values[0];}
  public String abbreviation() {return (String) values[0];}
  public String description() {return (String) values[0];}
}
