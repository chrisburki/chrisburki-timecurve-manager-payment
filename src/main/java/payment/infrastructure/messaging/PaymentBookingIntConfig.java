package payment.infrastructure.messaging;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import payment.domain.api.PaymentBookingCommand;
import payment.domain.api.PaymentBookingReplyEvent;

@Configuration
@Profile("int")
@Slf4j
@EnableKafka
public class PaymentBookingIntConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapServerAddress;

  // Producer for Payment Booking Command
  @Bean
  public ProducerFactory<String, PaymentBookingCommand> paymentBookingCommandProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, PaymentBookingCommand> paymentBookingCommandKafkaTemplate() {
    return new KafkaTemplate<>(paymentBookingCommandProducerFactory());
  }

  // Consumer for Booking Command
  @Bean
  public ConsumerFactory<String, PaymentBookingReplyEvent> paymentBookingReplyMessageConsumerFactory() {
    JsonDeserializer<PaymentBookingReplyEvent> deserializer = new JsonDeserializer<>(PaymentBookingReplyEvent.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "foo");
    configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
    return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PaymentBookingReplyEvent> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PaymentBookingReplyEvent> factory
        = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(paymentBookingReplyMessageConsumerFactory());
    return factory;
  }
}
