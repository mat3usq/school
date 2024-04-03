package com.mat3.school.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository<Courses> extends CrudRepository<Courses, Integer> {
}
