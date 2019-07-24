package com.haidarvm.indekskepuasan.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "department")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "available")
    private Integer available;

    @Column(name = "slug")
    private String slug;

    @Column(name = "logo")
    private String logo;

    @Column(name = "text_service")
    private String textService;

    @Column(name = "bg_color")
    private String bgColor;

    @Column(name = "satisfy_img")
    private String satisfy_img;

    @Column(name = "dissatisfy_img")
    private String dissatisfy_img;

    @Column(name = "device_id")
    private String device_id;

}
