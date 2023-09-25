package vivt.volkov.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "journal")
public class JournalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private ActionType action;

    private long record_time;
    private String access_from_host;


    public JournalRecord() {}
    public JournalRecord(User user, ActionTypes action, String host) {
        this.user = user;
        this.access_from_host = host;
        this.record_time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        setActionType(action);
    }

    @Override
    public String toString() {
        return String.format("JournalRecord(user=%s, action=%s, host=%s)",
                user.getId(), action.getName(), access_from_host);
    }

    public LocalDateTime getRecordTime() {
        return LocalDateTime.ofEpochSecond(record_time, 0, ZoneOffset.UTC);
    }

    public ActionType getAction() {
        return action;
    }

    public void setActionType(ActionTypes type) {
        switch (type) {
            case LOGIN -> {
                this.action = new ActionType("login");
            }
            case REGISTRATION -> {
                this.action = new ActionType("registration");
            }
        }
    }
}
