package payment.infrastructure.rest;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@Getter
@Setter
@ToString
@Slf4j
public class ExternalPositionCommand implements Serializable {
  @NotNull
  private String tenantId;
  @NotNull
  private String containerId;
  private String tag;
  @NotNull
  private PositionValueType valueType;
  @NotNull
  private String valueTag;
}
