package payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import payment.application.PaymentOrderService;
import payment.domain.api.PaymentOrderCommand;
import payment.domain.api.PaymentOrderExternalEvent;
import payment.domain.api.PaymentOrderStatus;
import payment.domain.model.OrderStatus;
import payment.infrastructure.persistence.PaymentOrderEntity;
import payment.infrastructure.persistence.PaymentOrderEntityRepository;

@SpringBootApplication
@Slf4j
public class PaymentApplication {

  public static void main(String[] args) {
    SpringApplication.run(PaymentApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(PaymentOrderService paymentOrderService) {
    return (args) -> {
      //Payment 1
      PaymentOrderCommand command1 = PaymentOrderCommand.builder()
          .orderStatus(PaymentOrderStatus.OPEN)
          .tenantId("AAA")
          .orderType("domestic")
          .transactionDate(LocalDate.now())
          .valueDate(LocalDate.now())
          .payer_iban("CH1231231231108")
          .beneficiary_iban("CH1231231231123")
          .beneficiary_address("Michelle Burki")
          .amount(new BigDecimal(1000))
          .currency_iso("CHF")
          .build();
      PaymentOrderExternalEvent paymentResult1 = paymentOrderService
          .processPaymentCommand(command1);
      log.info("payment1: " + paymentResult1.getId());

      //Payment 2
      PaymentOrderCommand command2 = PaymentOrderCommand.builder()
          .orderStatus(PaymentOrderStatus.OPEN)
          .tenantId("AAA")
          .orderType("domestic")
          .transactionDate(LocalDate.now())
          .valueDate(LocalDate.now())
          .payer_iban("CH1231231231108")
          .beneficiary_iban("CH1231231231129")
          .beneficiary_address("Aniél Burki")
          .amount(new BigDecimal(2000))
          .currency_iso("CHF")
          .build();
      PaymentOrderExternalEvent paymentResult2 = paymentOrderService
          .processPaymentCommand(command2);
      log.info("payment2: " + paymentResult2.getId());

    };
  }
}
