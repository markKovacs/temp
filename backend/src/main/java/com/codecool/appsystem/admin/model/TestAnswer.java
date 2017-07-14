package com.codecool.appsystem.admin.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "test_answers")
public class TestAnswer {

    @Id
    private String id = UUID.randomUUID().toString();

    private String questionId;

    private String correctAnswer;

}