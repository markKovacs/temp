package com.codecool.appsystem.admin.model.stat;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "v_active_inactive_application")
public class ActiveApplicationsData {

    @Id
    private String locationId;

    private Integer active;

    private Integer inactive;

}
