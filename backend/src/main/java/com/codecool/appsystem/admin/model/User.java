package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;


@Data
@Entity
@Table(name = "system_user")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "id")
@ToString
public class User {

    @Id
    @JsonIgnore
    private Integer id;

    private String email;

    private String givenName;
    private String familyName;
    private String middleName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;

    private String gender;

    private Integer birthDate;

    private String photoUrl;

    private String phoneNumber;

    private Boolean canApply;
    private Boolean gmailAccount;

    private Boolean contractSigned;

    private String locationId;

    private String auth0UserId;

    private Boolean isBlacklisted;

    @OneToOne(mappedBy = "user")
    @Where(clause = "active = true")
    private Application application;

    @Column(length = 80)
    private String userHash;

    public String getFullName() {
        if (!StringUtils.isEmpty(middleName)) {
            return givenName + " " + middleName + " " + familyName;
        }
        return givenName + " " + familyName;
    }
}

