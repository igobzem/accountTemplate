package account.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUserAndPeriod(User user, LocalDate date);
    List<Payment> findByUserOrderByPeriodDesc(User user);
}
