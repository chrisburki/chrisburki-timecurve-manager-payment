package payment.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.rpc.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@Slf4j
public class PaymentBookingProdConfig {

  @Value(value = "${topic.command-booking}")
  private String topicCommand;

  @Value(value = "${topic.payment-reply-booking}")
  private String topicPaymentReplyBooking;

  public static String topicReplyBooking = "payment-reply-booking";

  final String subscriptionReply = "paymentBookingReplySub";

  public static final String replyTopic = "REPLY_TOPIC";
  public static final String correlationId = "CORRELATION_ID";

  private final PubSubAdmin pubSubAdmin;

  public PaymentBookingProdConfig(PubSubAdmin pubSubAdmin) {
    this.pubSubAdmin = pubSubAdmin;
  }

  @Bean
  public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
    return new JacksonPubSubMessageConverter(objectMapper);
  }
  @Bean
  public void createTopics() {
    try {
      pubSubAdmin.createTopic(topicPaymentReplyBooking);
      pubSubAdmin.createTopic(topicCommand);
    } catch (ApiException e) {
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
//      if (e.getStatusCode() != Status.Code.ALREADY_EXISTS) {
//        throw e;
//      }
    }
  }

  @Bean
  public void createSubscription() {
    try {
      pubSubAdmin.createSubscription(subscriptionReply, topicPaymentReplyBooking);
    } catch (ApiException e) {
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
//      if (e.getStatusCode() != Status.Code.ALREADY_EXISTS) {
//        throw e;
//      }
    }
  }
}
