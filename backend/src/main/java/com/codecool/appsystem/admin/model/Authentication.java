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
    @Column(name = "id", length = 40)
    @JsonIgnore
    private String id;

    private String token;

    @JsonIgnore
    private String refreshToken;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    private String authProvider;

}
