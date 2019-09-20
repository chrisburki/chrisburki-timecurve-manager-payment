package payment.domain;

import payment.domain.model.PositionDetail;

public interface PaymentSpi {
  public PositionDetail addPosition(String tenantId, String containerId, String currencyIso);
  public PositionDetail getMoneyAccount(String tenantId, String key);
}
