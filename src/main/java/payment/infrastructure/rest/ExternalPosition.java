package payment.infrastructure.rest;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ExternalPosition implements Serializable {
  private Long id;
  private String tenantId;
  private String containerId;
  private String tag;
  private String name;
  private PositionValueType valueType;
  private String valueTag;
  private Boolean doBalanceCheck;
}
