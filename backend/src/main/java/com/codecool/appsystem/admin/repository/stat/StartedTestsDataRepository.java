package com.codecool.appsystem.admin.repository.stat;

import com.codecool.appsystem.admin.model.stat.StartedTestsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartedTestsDataRepository extends JpaRepository<StartedTestsData, Integer> {

    List<StartedTestsData> findByLocationId(String locationId);
}
