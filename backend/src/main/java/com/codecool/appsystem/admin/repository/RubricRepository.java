package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Rubric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RubricRepository extends JpaRepository<Rubric, Integer> {

    List<Rubric> findAllByCriteriaIdOrderByOrder(String criteriaId);
}
