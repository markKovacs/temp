package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@ToString
@Table(name = "location_types")
public class Location {

    @Id
    private String id;

    private String name;

    @JsonIgnore
    private String courseType;

    private String mapLocation;

    private String emailAddress;

    private String subjectPrefix;

    private String nextCourseStart;

    private String address;
}
