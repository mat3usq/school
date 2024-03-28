package com.mat3.school.service;

import com.mat3.school.constants.SchoolConstants;
import com.mat3.school.model.Person;
import com.mat3.school.repository.PersonRepository;
import com.mat3.school.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, RolesRepository rolesRepository) {
        this.personRepository = personRepository;
        this.rolesRepository = rolesRepository;
    }

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        person.setRoles(rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
            isSaved = true;
        return isSaved;
    }
}