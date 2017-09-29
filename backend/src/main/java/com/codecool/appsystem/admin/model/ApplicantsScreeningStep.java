package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id")
    private ScreeningStep step;

    private String interviewer;
    private Integer points;
    private String comment;
    private String status;

    @OneToMany(mappedBy = "applicantsScreeningStepId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ApplicantsScreeningStepCriteria> criteria = new ArrayList<>();

    public ApplicantsScreeningStep(ScreeningStep step, Application application){
        this.step = step;
        this.application = application;
    }

    @Override
    public String toString() {
        return "ApplicantsScreeningStep{" +
                "id='" + id + '\'' +
                ", application=" + application.getId() +
                ", step=" + step +
                ", interviewer='" + interviewer + '\'' +
                ", points=" + points +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", criteria=" + criteria.size() +
                '}';
    }
}
