package vivt.volkov.models;

import javax.persistence.*;


@Entity
@Table(name = "actions")
public class ActionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public String getName() { return name; }

    public ActionType() {}
    public ActionType(String name) {
        this.name = name;
    }
}
