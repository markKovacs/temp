package com.codecool.appsystem.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantsScreeningStep {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String applicationId;
    private String stepId;

    private String interviewer;
    private Integer points;
    private String comment;
    private String status;

    @OneToMany(mappedBy = "applicantsScreeningStepId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ApplicantsScreeningStepCriteria> criterias = new ArrayList<>();

    public ApplicantsScreeningStep(String stepId, String applicationId){
        this.stepId = stepId;
        this.applicationId = applicationId;
    }

}
