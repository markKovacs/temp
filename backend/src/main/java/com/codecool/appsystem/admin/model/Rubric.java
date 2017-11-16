package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Rubric {

    @Id
    @SequenceGenerator(sequenceName = "rubric_id_seq", name = "entity_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_id_sequence")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "criteria_id", referencedColumnName = "id")
    @JsonIgnore
    private ScreeningStepCriteria criteria;
    private String text;

    @Column(name = "rubric_order")
    private Integer order;
}
