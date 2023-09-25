package vivt.volkov.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "statistic")
public class StatisticRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long login_count;

    public StatisticRecord() {}
    public StatisticRecord(User user) {
        this.user = user;
        this.login_count = 0;
    }

    public User getUser() { return user; }
    public long getLoginCount() { return login_count; }
    public void incrementLogin() { login_count += 1; }

    public Long getId() { return id; }
    @Override
    public String toString() {
        return String.format("StatisticRecord(user=%s, login_count=%d)",
                user.getLogin(), login_count);
    }
}
