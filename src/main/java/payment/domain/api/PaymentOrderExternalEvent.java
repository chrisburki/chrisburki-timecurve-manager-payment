package payment.domain.api;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import payment.domain.model.OrderStatus;

@Getter
@Builder
@ToString
public class PaymentOrderExternalEvent implements Serializable {

  @NotNull
  private String id;
  @NotNull
  private OrderStatus orderStatus;
  @NotNull
  private Long gsn;

}
