package vivt.volkov.models;

import javax.persistence.*;


@Entity
@Table(name = "statistic")
public class StatisticRecord {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int login_count;

    public StatisticRecord() {}
    public StatisticRecord(User user) {
        this.user = user;
        this.login_count = 0;
    }

    public User getUser() { return user; }
    public int getLoginCount() { return login_count; }
    public void incrementLogin() { login_count += 1; }

    @Override
    public String toString() {
        return String.format("StatisticRecord(user=%s, login_count=%d)",
                user.getLogin(), login_count);
    }
}
