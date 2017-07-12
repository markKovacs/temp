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
@Table(name = "test_results")
@ToString
public class TestResult {

    @Id
    private String id;
    // FKs
    private String applicationId;
    private String testId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date started;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;

    private Integer points;

    private Boolean isPending;

    private Double percent;

    private String motivationText;

    private boolean passed;

    private String comment;

}
