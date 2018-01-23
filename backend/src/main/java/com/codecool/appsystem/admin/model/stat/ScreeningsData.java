package com.codecool.appsystem.admin.model.stat;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "v_screenings_by_date_and_location")
public class ScreeningsData {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    private String locationId;

    private Integer total;
    private Integer scheduleSignedBack;

    @Column(name = "final_result_y")
    private Integer finalResultY;

    @Column(name = "final_result_n")
    private Integer finalResultN;


}

