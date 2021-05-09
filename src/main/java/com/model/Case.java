package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "new_case")
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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
