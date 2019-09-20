package payment.infrastructure.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import payment.domain.PaymentSpi;
import payment.domain.model.PositionDetail;

@Component
@Slf4j
@Profile("test")
public class PaymentSpiTestImpl implements PaymentSpi {

  @Override
  public PositionDetail addPosition(String tenantId, String containerId, String currencyIso) {

    final String positionId = "P1";
    final Boolean doBalanceCheck = false;

    PositionDetail positionDetail = PositionDetail.builder()
        .positionId(positionId)
        .tenantId(tenantId)
        .needBalanceCheck(doBalanceCheck)
        .build();
    return positionDetail;
  }

  @Override
  public PositionDetail getMoneyAccount(String tenantId, String key) {
    final String positionId = "P2";
    final Boolean doBalanceCheck = true;

    PositionDetail positionDetail = PositionDetail.builder()
        .positionId(positionId)
        .tenantId(tenantId)
        .needBalanceCheck(doBalanceCheck)
        .build();
    return positionDetail;
  }
}
