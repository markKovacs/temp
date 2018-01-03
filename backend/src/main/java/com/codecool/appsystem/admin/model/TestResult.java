package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "test_results")
@ToString
public class TestResult {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;

    @Temporal(TemporalType.TIMESTAMP)
    private Date started;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finished;

    private Integer points;

    private Double percent;

    private Boolean passed;

    @Column(columnDefinition = "TEXT")
    private String savedAnswers;

    private String comment;

    private Boolean pending;


}
