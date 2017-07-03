package com.codecool.appsystem.admin.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test_answers")
public class TestAnswer {

    @Id
    private String questionId;

    private String correctAnswer;

}