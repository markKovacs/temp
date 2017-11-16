package com.codecool.appsystem.admin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(of = "id")
public class ScreeningStepCriteria {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String name;
    private String screeningStepId;
    private Boolean enabled = Boolean.TRUE;

    @OneToMany(mappedBy = "criteria")
    private List<Rubric> rubrics = new ArrayList<>();

}
