package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
@Table(name = "applications")
@AllArgsConstructor
@Builder
@ToString
public class Application {

    @Id
    @JsonIgnore
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @OneToOne(mappedBy = "application")
    private ApplicationScreeningInfo applicationScreeningInfo;

    private Integer courseId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date processStartedAt;

    private Boolean finalResult;

    private Boolean active;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String comment;

    @OneToMany(mappedBy = "application")
    private List<ApplicantsScreeningStep> screeningSteps = new ArrayList<>();

    @OneToMany(mappedBy = "application")
    @OrderBy("started")
    private List<TestResult> testResults = new ArrayList<>();

}
