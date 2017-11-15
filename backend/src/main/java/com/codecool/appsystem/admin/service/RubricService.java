package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Rubric;
import com.codecool.appsystem.admin.repository.RubricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RubricService {

    @Autowired
    private RubricRepository rubricRepository;

    public List<Rubric> getRubricsByCriteria(String criteriaId) {
        List<Rubric> test = rubricRepository.findAllByCriteriaIdOrderByOrder(criteriaId);
        return rubricRepository.findAllByCriteriaIdOrderByOrder(criteriaId);
    }

    public void saveRubrics(List<Rubric> rubrics) {


        rubricRepository.save(rubrics);
    }

    public void deleteRubric(Integer id) {
        rubricRepository.delete(id);
    }
}
