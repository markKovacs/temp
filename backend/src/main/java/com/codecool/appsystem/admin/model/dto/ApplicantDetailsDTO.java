package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicantDetailsDTO extends RestResponseDTO{


}
