package com.mat3.school.repository;

import com.mat3.school.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);

//    @Query(value = "select * from contact_msg c where c.status = :status", nativeQuery = true)
    @Query("select c from Contact c where c.status = :status")
    Page<Contact> findByStatusWithQuery(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Contact c set c.status = ?1 where c.contactId = ?2")
    int updateStatusById(String status, int id);
}
