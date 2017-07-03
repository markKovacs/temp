package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@Table(name = "tests")
@ToString
public class Test {

    @Id
    @Column(length = 40)
    private String id = UUID.randomUUID().toString();

    private String name;
    private String formUrl;
    private Integer maxPoints;
    private Integer threshold;
    private Boolean enabled;
    private String locationId;
    private Integer orderInBundle;
    private Integer estimatedTime;

    @Column(columnDefinition = "TEXT")
    private String formAsJson;


}
