package com.codecool.appsystem.admin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rubric {

    @Id
    @SequenceGenerator(sequenceName = "rubric_id_seq", name = "entity_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_sequence")
    private Integer id;

    private String criteriaId;
    private String text;

    @Column(name = "rubric_order")
    private Integer order;
}
