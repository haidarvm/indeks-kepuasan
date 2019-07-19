package com.haidarvm.indekskepuasan.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "scores")
public class Score extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "score")
    private Integer score;
}
