package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface ApplicationScreeningInfoRepository extends JpaRepository<ApplicationScreeningInfo, String> {

    ApplicationScreeningInfo findFirstByApplicationIdOrderByScreeningGroupTimeDesc(String appId);
    ApplicationScreeningInfo findByApplicationId(String id);
    List<ApplicationScreeningInfo> findByScreeningGroupTime(Date groupTime);


}
