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
@ToString
public class ApplicationScreeningInfo {

    @Id
    @Column(name = "id", length = 40)
    private String id = UUID.randomUUID().toString();

    private String applicationId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date screeningGroupTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date screeningPersonalTime;

    private Boolean scheduleSignedBack;

    private String mapLocation;

}
