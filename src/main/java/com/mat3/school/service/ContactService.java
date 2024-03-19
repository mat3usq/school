package com.mat3.school.service;

import com.mat3.school.model.Contact;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Setter
@Getter
@Slf4j
@Service
@ApplicationScope
public class ContactService {
    private int counter = 0;

    public ContactService() {
        System.out.println("ContactService was initialized.");
    }

    /**
     * Save Contact Details into DB
     *
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = true;
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }
}
