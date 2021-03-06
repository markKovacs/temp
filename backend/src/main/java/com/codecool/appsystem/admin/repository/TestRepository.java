package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Location;
import com.codecool.appsystem.admin.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, String> {

    List<Test> findByLocationId(String locationId);

    List<Test> findByLocationIdAndEnabledIsTrueOrderByOrderInBundleAsc(String locationId);

    Test findByMotivationVideoIsTrueAndLocationAndEnabledIsTrue(Location location);
}
