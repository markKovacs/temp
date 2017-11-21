package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStepCriteria;
import com.codecool.appsystem.admin.model.Rubric;
import com.codecool.appsystem.admin.model.ScreeningStepCriteria;
import com.codecool.appsystem.admin.model.dto.RubricDTO;
import com.codecool.appsystem.admin.repository.ApplicantsScreeningStepCriteriaRepository;
import com.codecool.appsystem.admin.repository.RubricRepository;
import com.codecool.appsystem.admin.repository.ScreeningStepCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RubricService {

    @Autowired
    private RubricRepository rubricRepository;
    @Autowired
    private ScreeningStepCriteriaRepository criteriaRepository;

    public List<RubricDTO> getRubricsDTOByCriteria(String criteriaId) {
        return convertRubricListToRubricDTOList(rubricRepository.findAllByCriteriaIdOrderByOrder(criteriaId));
    }

    public void saveRubrics(List<RubricDTO> rubrics) {
        rubricRepository.save(convertRubricDtoListToRubricList(rubrics));
    }

    private List<Rubric> convertRubricDtoListToRubricList(List<RubricDTO> rubricDTOS){
        List<Rubric> rubrics = new ArrayList<>();
        for(RubricDTO rubricDTO: rubricDTOS){
            rubrics.add(Rubric.builder()
                    .id(rubricDTO.getId())
                    .criteria(criteriaRepository.findOne(rubricDTO.getCriteriaId()))
                    .text(rubricDTO.getText())
                    .order(rubricDTO.getOrder())
                    .build());
        }
        return rubrics;
    }

    private List<RubricDTO> convertRubricListToRubricDTOList(List<Rubric> rubrics){
        List<RubricDTO> rubricDTOS = new ArrayList<>();
        for(Rubric rubric: rubrics){
            rubricDTOS.add(RubricDTO.builder()
                    .id(rubric.getId())
                    .criteriaId(rubric.getCriteria().getId())
                    .text(rubric.getText())
                    .order(rubric.getOrder())
                    .build());
        }
        return rubricDTOS;
    }

    public void deleteRubric(Integer id) {
        rubricRepository.delete(id);
    }
}
