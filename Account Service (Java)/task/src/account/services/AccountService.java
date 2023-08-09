package account.services;

import account.data.*;
import account.exceptions.InvalidPeriodException;
import account.exceptions.PaymentNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private UserRepository userRepo;
    @Transactional
    public void addPayments(List<PaymentDto> dtos) {
        logger.info("addPayments -+- "+ dtos);
        for (PaymentDto dto : dtos) {
            Payment payment = new Payment();
            User user = userRepo.findByEmailIgnoreCase(dto.employee()).orElseThrow(() ->
                    new UsernameNotFoundException("Not found " + dto.employee()));
            payment.setUser(user);
            try {
                LocalDate date = LocalDate.parse("01-" + dto.period(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                logger.info("date ------- "+ date);
                payment.setPeriod(date);
            }
            catch (DateTimeParseException e) {
                throw new InvalidPeriodException("invalid period - "+ dto.period());
            }
            payment.setSalary(dto.salary());
            paymentRepo.save(payment);
        }
    }

    public PaymentResponse getPayment(User user, String period) {
        Optional<Payment> optional;
        try {
            LocalDate date = LocalDate.parse("01-" + period, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            optional = paymentRepo.findByUserAndPeriod(user, date);
        } catch (DateTimeParseException e) {
            throw new InvalidPeriodException("invalid period - " + period);
        }
        return getPaymentResponse(user, optional.get());
    }

    public List<PaymentResponse> getPayments(User user) {
        List<Payment> payments;
        payments = paymentRepo.findByUserOrderByPeriodDesc(user);
        List<PaymentResponse> result = new ArrayList<>();
        for (Payment payment : payments) {
            result.add(getPaymentResponse(user, payment));
        }
        return result;
    }

    private PaymentResponse getPaymentResponse(User user, Payment payment) {
        return new PaymentResponse(user.getName(), user.getLastname(),
                payment.getPeriod().format(DateTimeFormatter.ofPattern("MMMM-yyyy")),
                converter.apply(payment.getSalary()));
    }
    final private Function<Long, String> converter = x -> x / 100 + " dollar(s) " +
            x % 100 + " cent(s)";

    @Transactional
    public void changePayment(PaymentDto dto) {
        User user = userRepo.findByEmailIgnoreCase(dto.employee()).orElseThrow(() ->
                new UsernameNotFoundException("Not found " + dto.employee()));
        LocalDate date;
        try {
            date = LocalDate.parse("01-" + dto.period(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidPeriodException("invalid period - "+ dto.period());
        }
        Payment payment = paymentRepo.findByUserAndPeriod(user, date).orElseThrow(PaymentNotFoundException::new);
        payment.setSalary(dto.salary());
        paymentRepo.save(payment);
    }

}
