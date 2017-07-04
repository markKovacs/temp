package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationScreeningInfoRepository extends JpaRepository<ApplicationScreeningInfo, String> {

    ApplicationScreeningInfo findFirstByApplicationIdOrderByScreeningGroupTimeDesc(String appId);
    ApplicationScreeningInfo findByApplicationId(String id);

}
