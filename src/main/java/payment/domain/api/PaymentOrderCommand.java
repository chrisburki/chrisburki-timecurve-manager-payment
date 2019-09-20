package payment.domain.api;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import payment.domain.model.OrderStatus;


@Builder
@Getter
@ToString
@Slf4j
public class PaymentOrderCommand implements Serializable {


  private String tenantId;

  @NotNull
  private PaymentOrderStatus orderStatus;

  private String orderType;

  private LocalDate transactionDate;

  private LocalDate valueDate;

  private String payer_iban;

  private String beneficiary_iban;

  private String beneficiary_address;

  private Double amount;

  private String currency_iso;

  private String id;

}
