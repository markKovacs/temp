package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@ToString
public class ApplicationScreeningInfo {

    @Id
    private String id;

    private String applicationId;
    private String screeningDay;
    private String screeningGroupTime;
    private String screeningPersonalTime;
    private Boolean scheduleSignedBack;
    private String mapLocation;

}
