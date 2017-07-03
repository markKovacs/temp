package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "courses")
@ToString
public class Course {

    @Id
    private Integer id;

    private String name;
    private String locationId;
    private Boolean open;


    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    private Boolean filled;
    private Boolean enabled;
}
