package com.codecool.appsystem.admin.model.dto;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApplicantInfoDTO extends RestResponseDTO {

    private String name;
    private Integer adminId;
    private String location;
    private String status;
    private Long attempts;
    private Boolean blacklisted;
    private Date processStartedAt;
    private String email;
    private String phoneNumber;
}
