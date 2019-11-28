package payment.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile("prod")
@Slf4j
public class PaymentBookingProdInConfig {

  private final PaymentOrderMessageInHdlProd paymentOrderMessageInHdlProd;
  private final PubSubTemplate pubSubTemplate;
  private final PaymentBookingProdConfig paymentBookingProdConfig;

  public PaymentBookingProdInConfig(
      PaymentOrderMessageInHdlProd paymentOrderMessageInHdlProd,
      PubSubTemplate pubSubTemplate,
      PaymentBookingProdConfig paymentBookingProdConfig) {
    this.paymentOrderMessageInHdlProd = paymentOrderMessageInHdlProd;
    this.pubSubTemplate = pubSubTemplate;
    this.paymentBookingProdConfig = paymentBookingProdConfig;
  }

  /**
   * It's called only when the application is ready to receive requests. Passes a consumer
   * implementation when subscribing to a Pub/Sub topic.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void subscribe() {
    log.info("Subscribing {} to {}", paymentOrderMessageInHdlProd.getClass().getSimpleName(),
        paymentBookingProdConfig.subscriptionReply);
    pubSubTemplate.subscribe(paymentBookingProdConfig.subscriptionReply, paymentOrderMessageInHdlProd.consumer());
  }
}
