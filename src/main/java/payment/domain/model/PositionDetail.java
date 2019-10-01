package payment.domain.model;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PositionDetail {

  @NotNull
  private String positionId;

  @NotNull
  private String tenantId;

  @NotNull
  private String tag;

  @NotNull
  private String name;

  @NotNull
  private Boolean needBalanceCheck;

}
