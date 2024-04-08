package com.mat3.school.rest;

import com.mat3.school.model.Contact;
import com.mat3.school.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/contact")
public class ContactRestController {
    public final ContactRepository contactRepository;

    @Autowired
    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/getMessagesByStatus")
    @ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status) {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    @ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact) {
        if (null != contact && null != contact.getStatus())
            return contactRepository.findByStatus(contact.getStatus());
        else
            return List.of();
    }
}
