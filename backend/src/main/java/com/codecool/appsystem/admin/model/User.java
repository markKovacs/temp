package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


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
    private String id;

    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer adminId;

    private String givenName;
    private String familyName;
    private String middleName;

    private String registeredAt;

    private String gender;

    private Integer birthDate;

    private String photoUrl;

    private String phoneNumber;

    private boolean canApply;
    private boolean gmailAccount;

    private String locationId;

    private String auth0UserId;

    @Column(length = 80)
    private String userHash;

    public User(String id) {
        this.id = id;
    }

    public String getFullName() {
        return givenName + " " + familyName;
    }
}

