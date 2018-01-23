package com.codecool.appsystem.admin.repository.stat;

import com.codecool.appsystem.admin.model.stat.SuccessTestsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuccessTestsDataRepository extends JpaRepository<SuccessTestsData, Integer> {

    List<SuccessTestsData> findByLocationId(String locationId);
}
