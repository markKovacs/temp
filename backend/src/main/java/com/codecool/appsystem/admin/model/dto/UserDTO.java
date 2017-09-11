package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO extends RestResponseDTO {

    private boolean success;
    private String message;
    private String fullName;
    private String givenName;
    private String familyName;
    private String middleName;
    private String registeredAt;
    private String gender;
    private Integer birthDate;
    private Boolean gmailAccount;
    private Integer id;
    private String photoUrl;
    private String phoneNumber;
    private String locationId;

    public static UserDTO buildSuccess(){
        UserDTO dto = new UserDTO();
        dto.setSuccess(true);
        return dto;
    }

    public static UserDTO buildFailed(String msg){
        UserDTO dto = new UserDTO();
        dto.setSuccess(false);
        dto.setMessage(msg);
        return dto;
    }

}
