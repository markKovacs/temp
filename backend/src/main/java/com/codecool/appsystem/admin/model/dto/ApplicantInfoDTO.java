package com.codecool.appsystem.admin.model.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApplicantInfoDTO extends RestResponseDTO {

    private Integer id;
    private String name;
    private String location;
    private String status;
    private Integer attempts;
    private Boolean blacklisted;
    private Date processStartedAt;
    private String email;
    private String phoneNumber;
}
