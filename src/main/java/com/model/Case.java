package com.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "todolist")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "value")
    private String value;
    @Column(name = "dead_line")
    private String deadLine;

//    public Case(String value, String deadLine) {
//        this.value = value;
//        this.deadLine = deadLine;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public String getDeadLine() {
//        return deadLine;
//    }
//
//    public void setDeadLine(String deadLine) {
//        this.deadLine = deadLine;
//    }
}
