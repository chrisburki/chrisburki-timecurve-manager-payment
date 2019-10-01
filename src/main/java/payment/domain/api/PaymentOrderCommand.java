package payment.domain.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Builder
@Getter
@ToString
@Slf4j
public class PaymentOrderCommand implements Serializable {

  @NotNull
  private String tenantId;

  @NotNull
  private PaymentOrderStatus orderStatus;

  @NotNull
  private String orderType;

  @NotNull
  private LocalDate transactionDate;

  @NotNull
  private LocalDate valueDate;

  @NotNull
  private String payer_iban;

  private String beneficiary_iban;

  private String beneficiary_address;

  @NotNull
  private BigDecimal amount;

  @NotNull
  private String currency_iso;

  private String id;

}
