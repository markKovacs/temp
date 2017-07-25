package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@Builder
public class ApplicantsScreeningStepCriteria {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    @JsonIgnore
    private String criteriaId;

    @JsonIgnore
    private String applicantsScreeningStepId;

    private Integer points;
    private String comment;
    private String status;

}
