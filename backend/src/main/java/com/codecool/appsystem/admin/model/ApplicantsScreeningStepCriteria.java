package com.codecool.appsystem.admin.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@ToString(exclude = "criteria")
public class ApplicantsScreeningStepCriteria {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "criteria_id", referencedColumnName = "id")
    private ScreeningStepCriteria criteria;

    private String applicantsScreeningStepId;

    private Integer points;
    private String comment;
    private String status;

}
