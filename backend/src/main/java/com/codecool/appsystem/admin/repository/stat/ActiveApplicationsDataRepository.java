package com.codecool.appsystem.admin.repository.stat;

import com.codecool.appsystem.admin.model.stat.ActiveApplicationsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveApplicationsDataRepository extends JpaRepository<ActiveApplicationsData, String> {

    ActiveApplicationsData findByLocationId(String location);
}
