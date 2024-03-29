package payment.infrastructure.rest;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExternalPosition implements Serializable {

  private Long id;

  @NotNull
  private String tenantId;

  @NotNull
  private String containerId;

  // @NotNull
  private String tag;

  // @NotNull
  private String name;

  @NotNull
  private PositionValueType valueType;

  @NotNull
  private String valueTag;

  // @NotNull
  private Boolean doBalanceCheck;

}
