package com.codecool.appsystem.admin.model.stat;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "v_success_tests_by_date_and_location")
public class SuccessTestsData {

    @Id
    private Integer id;
    private Integer no;
    private String test;

    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    private String locationId;

}
