package com.codecool.appsystem.admin.repository.stat;

import com.codecool.appsystem.admin.model.stat.MonthlyScreeningsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyScreeningsDataRepository extends JpaRepository<MonthlyScreeningsData, String> {

    List<MonthlyScreeningsData> findByLocation(String location);
}
