package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@EqualsAndHashCode(of = "id")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Authentication {

    @Id
    @JsonIgnore
    private Integer id;

    private String token;

    @JsonIgnore
    private String refreshToken;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    private String authProvider;

}
