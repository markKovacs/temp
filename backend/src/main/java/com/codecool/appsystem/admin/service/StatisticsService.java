package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.dto.TestsStatDataDTO;
import com.codecool.appsystem.admin.model.stat.*;
import com.codecool.appsystem.admin.repository.stat.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private ActiveApplicationsDataRepository activeApplicationsDataRepository;

    @Autowired
    private MonthlyScreeningsDataRepository monthlyScreeningsDataRepository;

    @Autowired
    private ScreeningsDataRepository screeningsDataRepository;

    @Autowired
    private StartedTestsDataRepository startedTestsDataRepository;

    @Autowired
    private SuccessTestsDataRepository successTestsDataRepository;

    public ActiveApplicationsData getActiveApplicationsData(String location) {
        return activeApplicationsDataRepository.findByLocationId(location);
    }

    public List<MonthlyScreeningsData> getMonthlyScreenings(String location) {
        return monthlyScreeningsDataRepository.findByLocation(location);
    }

    public Collection<ScreeningsData> getScreeningData(String location) {
        return screeningsDataRepository.findByLocationId(location);
    }

    public Collection<TestsStatDataDTO> getTestsStat(String location) {
        List<StartedTestsData> startedTestsData = startedTestsDataRepository.findByLocationId(location);
        List<SuccessTestsData> successTestsData = successTestsDataRepository.findByLocationId(location);

        // -?(\d{1}\s{1})

        Map<TestsStatDataDTOKey, TestsStatDataDTO> result = new HashMap<>();


        startedTestsData.forEach(data -> {

            Long day = data.getDay().toInstant().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
            //String day = data.getDay().toInstant().truncatedTo(ChronoUnit.DAYS).toString().split("T")[0];
            String test = data.getTest().replaceAll("-?(\\d{1}\\s{1})", "");

            TestsStatDataDTOKey key = new TestsStatDataDTOKey(day, data.getLocationId(), test);

            if (!result.containsKey(key)) {
                result.put(key, new TestsStatDataDTO(day, data.getLocationId(), test));
            }

            TestsStatDataDTO dto = result.get(key);

            dto.setCountStarted(dto.getCountStarted() + data.getNo());

        });

        successTestsData.forEach(data -> {

            Long day = data.getDay().toInstant().truncatedTo(ChronoUnit.DAYS).toEpochMilli();
            //String day = data.getDay().toInstant().truncatedTo(ChronoUnit.DAYS).toString().split("T")[0];
            String test = data.getTest().replaceAll("-?(\\d{1}\\s{1})", "");

            TestsStatDataDTOKey key = new TestsStatDataDTOKey(day, data.getLocationId(), test);
            TestsStatDataDTO dto = result.get(key);

            dto.setCountSuccess(dto.getCountSuccess() + data.getNo());

        });

        List<TestsStatDataDTO> resultList = new ArrayList<>(result.values());
        resultList.sort((o1, o2) -> {
            Integer o1val = 0;
            Integer o2val = 0;
            if (o1.getTest().contains("Prerequisites")) {
                o1val = 1;
            }
            if (o1.getTest().contains("English")) {
                o1val = 2;
            }
            if (o1.getTest().contains("Logic")) {
                o1val = 3;
            }
            if (o1.getTest().contains("Motivation")) {
                o1val = 4;
            }

            if (o2.getTest().contains("Prerequisites")) {
                o2val = 1;
            }
            if (o2.getTest().contains("English")) {
                o2val = 2;
            }
            if (o2.getTest().contains("Logic")) {
                o2val = 3;
            }
            if (o2.getTest().contains("Motivation")) {
                o2val = 4;
            }

            return o1val.compareTo(o2val);
        });
        resultList.sort(Comparator.comparing(TestsStatDataDTO::getDay).reversed());

        return resultList;

    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    private class TestsStatDataDTOKey {
        private Long day;
        private String location;
        private String test;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    private class ScreeningsStatDataDTOKey {
        private Long day;
        private String location;
    }


    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.toInstant().truncatedTo(ChronoUnit.DAYS).toString().split("T")[0]);
    }
}
