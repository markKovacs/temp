package com.codecool.appsystem.admin.model.stat;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "v_monthly_screenings")
public class MonthlyScreeningsData {

    @Id
    private String month;

    private String location;

    @Column(name = "final_result_y")
    private Integer finalResultY;

    @Column(name = "final_result_n")
    private Integer finalResultN;

}
