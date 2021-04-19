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
}
