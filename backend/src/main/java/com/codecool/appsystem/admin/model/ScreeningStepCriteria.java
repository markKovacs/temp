package com.codecool.appsystem.admin.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class ScreeningStepCriteria {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String name;
    private String screeningStepId;
    private Boolean enabled = Boolean.TRUE;

}