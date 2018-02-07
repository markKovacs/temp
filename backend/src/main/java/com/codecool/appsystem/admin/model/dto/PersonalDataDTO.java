package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.PersonalData;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class PersonalDataDTO {

    private Integer id;

    private String name;
    private String maidenName;
    private String address;
    private String gmailAccount;

    private String mothersName;

    private String placeOfBirth;

    private Date birthDate;

    private String idDocumentType;
    private String idNumber;

    private Date screeningDate;

    public static PersonalDataDTO fromEntity(PersonalData personalData){
        PersonalDataDTO dto = new PersonalDataDTO();
        BeanUtils.copyProperties(personalData, dto);
        return dto;
    }

}
