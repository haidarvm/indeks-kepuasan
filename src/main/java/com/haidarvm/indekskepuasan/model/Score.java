package com.haidarvm.indekskepuasan.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "score")
public class Score extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;

    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime created;

    @Column(name = "score")
    private Integer score;

    @Column(name = "device_id")
    private String deviceId;

    @Transient
    public Integer total;

    @Transient
    public Integer satisfy;

    @Transient
    public Integer dissatisfy;

}
