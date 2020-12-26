package main.model;
import javax.persistence.*;

@Entity
@Table(name="cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String value;

    @Column(name = "Dead_Line")
    private String deadLine;

//    public Case(int id, String value, String deadLine) {
//        this.id = id;
//        this.value = value;
//        this.deadLine = deadLine;
//    }

//    public Case(String value, String deadLine) {
//        this.value = value;
//        this.deadLine = deadLine;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
}
