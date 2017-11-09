package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @OneToMany(mappedBy = "user")
    @OrderBy("processStartedAt desc NULLS LAST")
    private List<Application> applications = new ArrayList<>();

    @Column(length = 80)
    private String userHash;

    public String getFullName() {
        if (!StringUtils.isEmpty(middleName)) {
            return givenName + " " + middleName + " " + familyName;
        }
        return givenName + " " + familyName;
    }

    public Application getActiveApplication(){

        for(Application app : applications){
            if(Boolean.TRUE.equals(app.getActive())){
                return app;
            }
        }
        return null;

    }

    public Application getApplicationById(String applicationId) {

        for (Application app: applications){
            if (app.getId().equals(applicationId)){
                return app;
            }
        }
        return null;
    }
}

