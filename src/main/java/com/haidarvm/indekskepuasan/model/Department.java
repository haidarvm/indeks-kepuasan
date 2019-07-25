package com.haidarvm.indekskepuasan.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ColumnDefault("1")
    @Column(name = "available")
    private Integer available;

    @Column(name = "logo")
    private String logo;

    @Column(name = "text_service")
    private String textService;

    @Column(name = "bg_color")
    private String bgColor;

    @Column(name = "satisfy_img")
    private String satisfyImg;

    @Column(name = "dissatisfy_img")
    private String dissatisfyImg;

    @Column(name = "device_id")
    private String deviceId;

}
