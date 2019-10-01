package payment.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import payment.domain.model.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "paymentOrder")
public class PaymentOrderEntity {

  @Id
  @Setter
  private String id;
  private Long gsn;
  private String tenantId;
  private OrderStatus orderStatus;
  private String orderType;
  private LocalDate transactionDate;
  private LocalDate valueDate;
  private String payer_iban;
  private String payer_pos_id;
  private String beneficiary_iban;
  private String beneficiary_address;
  private String auxiliary_pos_id;
  private BigDecimal amount;
  private String currency_iso;

  public PaymentOrderEntity(String tenantId, OrderStatus orderStatus,
      String orderType, LocalDate transactionDate, LocalDate valueDate, String payer_iban,
      String payer_pos_id, String beneficiary_iban, String beneficiary_address,
      String auxiliary_pos_id, BigDecimal amount, String currency_iso) {
    this.tenantId = tenantId;
    this.orderStatus = orderStatus;
    this.orderType = orderType;
    this.transactionDate = transactionDate;
    this.valueDate = valueDate;
    this.payer_iban = payer_iban;
    this.payer_pos_id = payer_pos_id;
    this.beneficiary_iban = beneficiary_iban;
    this.beneficiary_address = beneficiary_address;
    this.auxiliary_pos_id = auxiliary_pos_id;
    this.amount = amount;
    this.currency_iso = currency_iso;
  }

  @PostConstruct
  public void onPrePersist() {
    gsn = currGsn();
  }

  private Long currGsn() {
    final Long shift = 100000L;
    LocalDateTime currDateTime = LocalDateTime.now();
    return currDateTime.toLocalDate().getLong(ChronoField.EPOCH_DAY) * shift + currDateTime
        .getLong(ChronoField.SECOND_OF_DAY);
  }

}
