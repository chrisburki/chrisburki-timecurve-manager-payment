package payment.infrastructure.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import payment.domain.PaymentSpi;
import payment.domain.model.PositionDetail;

@Component
@Slf4j
@Profile("prod")
public class PaymentSpiProd implements PaymentSpi {

//  @Value("${EWOM_POSITION_SERVICE_HOST}")
//  @Value("${TIMECURVE_MANAGER_SERVICE_HOST}")
//  private String ewomPositionHost;

//  @Value("${EWOM_POSITION_SERVICE_PORT}")
//  @Value("${TIMECURVE_MANAGER_SERVICE_PORT}")
//  private String ewomPositionPort;

  private String positionService = "timecurve-manager";

  String ewomPositionUrl = "http://" + positionService + "/positions/";

  @Override
  public PositionDetail addPosition(String tenantId, String containerId, String currencyIso) {
    log.debug("Add position with URL: " + ewomPositionUrl);
    RestTemplate restTemplate = new RestTemplate();

    ExternalPositionCommand position = ExternalPositionCommand.builder()
        .tenantId(tenantId)
        .containerId(containerId)
        .valueType(PositionValueType.CURRENCY)
        .valueTag(currencyIso)
        .build();
    HttpEntity<ExternalPositionCommand> request = new HttpEntity<>(position);
    ExternalPositionReply response = restTemplate
        .postForObject(ewomPositionUrl, request, ExternalPositionReply.class);
    PositionDetail positionDetail = PositionDetail.builder()
        .positionId(response.getId().toString())
        .tenantId(tenantId)
        .needBalanceCheck(response.getNeedBalanceCheck())
        .build();
    return positionDetail;
  }


  @Override
  public PositionDetail getMoneyAccount(String tenantId, String key) {
    log.debug("Ask for position with URL: " + ewomPositionUrl + "/" + key);
    RestTemplate restTemplate = new RestTemplate();

    ExternalPosition response = restTemplate.getForObject(
        ewomPositionUrl + "/" + key, ExternalPosition.class);
    PositionDetail positionDetail = PositionDetail.builder()
        .positionId(response.getId().toString())
        .tenantId(response.getTenantId())
        .needBalanceCheck(response.getDoBalanceCheck())
        .build();
    return positionDetail;
  }
}
