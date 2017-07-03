package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
