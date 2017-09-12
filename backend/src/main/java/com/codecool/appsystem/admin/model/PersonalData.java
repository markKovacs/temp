package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(of = "userId")
@ToString
@NoArgsConstructor
public class PersonalData {

    @Id
    private Integer id;

    private String name;
    private String maidenName;
    private String address;
    private String gmailAccount;

    private String mothersName;

    private String placeOfBirth;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    private String idDocumentType;
    private String idNumber;

}
