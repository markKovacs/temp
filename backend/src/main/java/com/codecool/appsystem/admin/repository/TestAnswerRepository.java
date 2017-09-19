package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestAnswerRepository extends JpaRepository<TestAnswer, Integer> {

    List<TestAnswer> findByQuestionIdOrderByCorrectAnswerAsc(String id);

    Optional<TestAnswer> findByQuestionIdAndCorrectAnswer(String questionId, String answerId);

}
