package com.mat3.school.repository;

import com.mat3.school.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer> {
    @Query("SELECT sc FROM SchoolClass sc JOIN sc.teachers t WHERE t.personId = :teacherId")
    List<SchoolClass> findAllByTeacherId(@Param("teacherId") int teacherId);
}
