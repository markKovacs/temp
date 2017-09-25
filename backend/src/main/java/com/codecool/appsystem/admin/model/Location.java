package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String country;

    @JsonIgnore
    private String courseType;

    @JsonIgnore
    private String mapLocation;

    @JsonIgnore
    private String emailAddress;

    @JsonIgnore
    private String subjectPrefix;

    @JsonIgnore
    private String nextCourseStart;

    @JsonIgnore
    private String address;

    @OneToMany(mappedBy = "location")
    @OrderBy("orderInBundle")
    @JsonIgnore
    private List<Test> tests = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    @OrderBy("order")
    private List<EmailTemplate> emailTemplates = new ArrayList<>();

}
