package com.codecool.appsystem.admin.model.stat;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "v_screening_data_by_date_and_location")
public class ScreeningsData {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    private String locationId;
    private String screening;
    private Integer count;
    private Boolean scheduleSignedBack;
    private Boolean finalResult;


}

