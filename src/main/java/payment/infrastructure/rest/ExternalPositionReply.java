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
public class ExternalPositionReply implements Serializable {

  @NotNull
  private Long id;

  @NotNull
  private String tag;

  @NotNull
  private String name;

  @NotNull
  private Boolean needBalanceCheck;

}
