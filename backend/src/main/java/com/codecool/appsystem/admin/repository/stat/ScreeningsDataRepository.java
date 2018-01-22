package com.codecool.appsystem.admin.repository.stat;

import com.codecool.appsystem.admin.model.stat.ScreeningsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningsDataRepository extends JpaRepository<ScreeningsData, Integer> {

    List<ScreeningsData> findByLocationId(String locationId);

}
