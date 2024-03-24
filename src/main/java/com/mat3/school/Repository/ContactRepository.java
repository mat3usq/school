package com.mat3.school.Repository;

import com.mat3.school.model.Contact;
import com.mat3.school.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        return jdbcTemplate.update("INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT," +
                        "MESSAGE,STATUS,CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?,?)",
                contact.getName(), contact.getMobileNum(),
                contact.getEmail(), contact.getSubject(), contact.getMessage(),
                contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
    }

    public List<Contact> findMsgsWithStatus(String status) {
        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement
                .setString(1, status), new ContactRowMapper());
    }
}
