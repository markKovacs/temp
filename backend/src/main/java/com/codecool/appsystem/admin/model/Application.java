package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "applications")
@AllArgsConstructor
@Builder
@ToString
public class Application {

    @Id
    @JsonIgnore
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    private Integer applicantId;
    private Integer courseId;
    private String locationId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date processStartedAt;

    private Boolean finalResult;

    private Boolean contractSigned;
    private Boolean active;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String comment;
}
