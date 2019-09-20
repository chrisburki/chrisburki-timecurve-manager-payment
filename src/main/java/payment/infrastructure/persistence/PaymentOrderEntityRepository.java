package payment.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentOrderEntityRepository extends MongoRepository<PaymentOrderEntity, String> {
}
