package account.data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "period" }) })
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate period;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
