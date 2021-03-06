package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.stat.ActiveApplicationsData;
import com.codecool.appsystem.admin.model.stat.MonthlyScreeningsData;
import com.codecool.appsystem.admin.model.stat.ScreeningsData;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class CompositeStatDTO {

    private String locationId;

    private ActiveApplicationsData activeApplicationsData;

    private List<MonthlyScreeningsData> monthlyScreeningsData;

    private Collection<ScreeningsData> screeningsStatData;

    private Collection<TestsStatDataDTO> testsStatData;

}
