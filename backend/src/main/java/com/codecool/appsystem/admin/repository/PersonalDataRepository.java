package com.codecool.appsystem.admin.repository;


import com.codecool.appsystem.admin.model.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDataRepository extends JpaRepository<PersonalData, String> {
}
