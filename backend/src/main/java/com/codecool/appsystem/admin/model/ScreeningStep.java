package com.codecool.appsystem.admin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ScreeningStep {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String name;
    private String locationId;
    private Boolean enabled = Boolean.TRUE;

    @OneToMany(mappedBy = "screeningStepId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ScreeningStepCriteria> criterias;

}
