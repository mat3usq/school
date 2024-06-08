package com.mat3.school.utils;

import com.mat3.school.model.*;
import com.mat3.school.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private HolidaysRepository holidaysRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Override
    public void run(String... args) {
//        createRoles();
//        createAddresses();
//        createPersons();
//        createContacts();
//        createClassess();
//        createCourses();
//        createHolidays();
    }

    private void createAddresses() {
        addressRepository.saveAll(Arrays.asList(
                new Address("123 Main St", "Chicago", "Springfield", "62701"),
                new Address("456 Elm St", "New York", "Springfield", "62702")
        ));
    }

    private void createRoles() {
        rolesRepository.saveAll(Arrays.asList(
                new Roles("ADMIN"),
                new Roles("STUDENT")
        ));
    }

    private void createPersons() {
        Address address = addressRepository.findById(1).orElseThrow(() -> new RuntimeException("Address not found"));
        Roles role = rolesRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));
        personRepository.save(new Person("admin", "777666555", "admin@gmail.com", "$2a$12$t823mpw6SD8Xn.f3MVeV/Of62q.1XSX6.VVcg.auhwUCbi6OxZAZe", role, address));
    }


    private void createContacts() {
        contactRepository.saveAll(Arrays.asList(
                new Contact("Adam", "2176436587", "zadam@gmail.com", "Regarding a job", "Wanted to join as teacher", "Open"),
                new Contact("Zara", "3412654387", "zarabaig@hotmail.com", "Course Admission", "Wanted to join a course", "Open"),
                new Contact("Marques", "8547643673", "kmarques@yahoo.com", "Course Review", "Review of Development course", "Open"),
                new Contact("Shyam", "4365328776", "gshyam@gmail.com", "Admission Query", "Need to talk about admission", "Open"),
                new Contact("John", "5465765453", "doejohn@gmail.com", "Holiday Query", "Query on upcoming holidays", "Open"),
                new Contact("Taniya Bell", "3987463827", "belltaniya@gmail.com", "Child Scholarship", "Can my child get scholarship?", "Open"),
                new Contact("Willie Lara", "4568764801", "476lara@gmail.com", "Need Admission", "My son need an admission", "Open"),
                new Contact("Jonathan Parsons", "4321768902", "jonathan.parsons@gmail.com", "Course feedback", "Music course is good", "Open"),
                new Contact("Cloe Rubio", "9854438719", "rubio987@gmail.com", "Correct Date of Birth", "My Child DOB needs to be corrected", "Open"),
                new Contact("Camilla Stein", "6545433254", "camillas@gmail.com", "Transport Query", "Is Transport provided?", "Open"),
                new Contact("Lizeth Gross", "4678783434", "grossliz@yahoo.com", "Progress report", "Please send progress report", "Open"),
                new Contact("Yael Howe", "1243563254", "howeyael@gmail.com", "Certificate Query", "Need Certificate hard copy", "Open"),
                new Contact("Ian Moreno", "2312231223", "moreno.ian@gmail.com", "Food feedback", "Food quality can be improved", "Open"),
                new Contact("Desirae Ibarra", "3445235667", "ibarrades@gmail.com", "Traffic Complaint", "Traffic around school can be controlled", "Open"),
                new Contact("Oswaldo Jarvis", "4556121265", "jarvissmile@hotmail.com", "Study Tour", "Study tour details needed", "Open"),
                new Contact("Miah Perkins", "2367784512", "perkinsmiah@hotmail.com", "Vaccination Support", "Vaccination center in the school", "Open"),
                new Contact("Zion Bolton", "8990678900", "boltzion@gmail.com", "Course fees", "Pls share fees of music course", "Open"),
                new Contact("Dominik Tanner", "4556127834", "tannerdominik@gmail.com", "Games schedule", "Provide Summer games schedule", "Open")
        ));
    }

    private void createClassess() {
        schoolClassRepository.saveAll(Arrays.asList(
                new SchoolClass("Math 101"),
                new SchoolClass("Eng 101")
        ));
    }

    private void createCourses() {
        coursesRepository.saveAll(Arrays.asList(
                new Courses("Introduction to Computer Science", "200"),
                new Courses("Introduction to Biology", "100")
        ));
    }

    private void createHolidays() {
        holidaysRepository.saveAll(Arrays.asList(
                new Holiday("Jan 1", "New Year's Day",  Holiday.Type.FESTIVAL),
                new Holiday("Oct 31", "Halloween",  Holiday.Type.FESTIVAL),
                new Holiday("Nov 24", "Thanksgiving Day",  Holiday.Type.FESTIVAL),
                new Holiday("Dec 25", "Christmas",  Holiday.Type.FESTIVAL),
                new Holiday("Jan 17", "Martin Luther King Jr. Day",  Holiday.Type.FEDERAL),
                new Holiday("July 4", "Independence Day",  Holiday.Type.FEDERAL),
                new Holiday("Sep 5", "Labor Day",  Holiday.Type.FEDERAL),
                new Holiday("Nov 11", "Veterans Day",  Holiday.Type.FEDERAL),
                new Holiday("Mar 27", "BirthDay",  Holiday.Type.FEDERAL)
        ));
    }
}
