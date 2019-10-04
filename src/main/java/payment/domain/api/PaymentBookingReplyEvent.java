package payment.domain.api;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentBookingReplyEvent {
  // correlation-id
  @NotNull
  private String orderId;

  @NotNull
  private Long bookingExtId;

  @NotNull
  private Integer bookingSequenceNr;

  @NotNull
  private String tenantId;

  @NotNull
  private PaymentBookingStatus bookingStatus;
}
