package com.codecool.appsystem.admin.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
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

}
