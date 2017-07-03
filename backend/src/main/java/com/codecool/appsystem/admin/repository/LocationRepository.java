package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

    Location findByName(String name);
}
