INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Jan 1 ', 'New Year''s Day', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Oct 31 ', 'Halloween', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Nov 24 ', 'Thanksgiving Day', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Dec 25 ', 'Christmas', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Jan 17 ', 'Martin Luther King Jr. Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' July 4 ', 'Independence Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Sep 5 ', 'Labor Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Nov 11 ', 'Veterans Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Mar 27 ', 'BirthDay', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('ADMIN', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('TEACHER', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('STUDENT', CURDATE(), 'DBA');

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`)
VALUES ('admin', 'admin', '777666555', '$2a$12$t823mpw6SD8Xn.f3MVeV/Of62q.1XSX6.VVcg.auhwUCbi6OxZAZe', 1, CURDATE(), 'DBA');

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`, `teacher_subject`)
VALUES ('teacher', 'teacher', '444555666', '$2a$12$kh080k5CBAKMlgrJYaFMFupfuJYGuoPWqjT.VlvBSDnywbl4uyrjK', 2, CURDATE(), 'DBA', "WF");

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`, `teacher_subject`)
VALUES ('teacher2', 'teacher@gmail.com', '444555666', '$2a$12$kh080k5CBAKMlgrJYaFMFupfuJYGuoPWqjT.VlvBSDnywbl4uyrjK', 2, CURDATE(), 'DBA', "Math");

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`, `teacher_subject`)
VALUES ('teacher3', 'teacher2@gmail.com', '444555666', '$2a$12$kh080k5CBAKMlgrJYaFMFupfuJYGuoPWqjT.VlvBSDnywbl4uyrjK', 2, CURDATE(), 'DBA', "IT");

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`)
VALUES ('student', 'student', '111222333', '$2a$12$7J4G7W4ppyLy0p3jDmAAnetLHvykPdsKl4dFxWtMAXMxMMNc02LAG', 3, CURDATE(), 'DBA');

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`)
VALUES ('student2', 'student@gmail.com', '111222333', '$2a$12$7J4G7W4ppyLy0p3jDmAAnetLHvykPdsKl4dFxWtMAXMxMMNc02LAG', 3, CURDATE(), 'DBA');

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`)
VALUES ('student3', 'student2@gmail.com', '111222333', '$2a$12$7J4G7W4ppyLy0p3jDmAAnetLHvykPdsKl4dFxWtMAXMxMMNc02LAG', 3, CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Adam','2176436587','zadam@gmail.com','Regarding a job','Wanted to join as teacher','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Zara','3412654387','zarabaig@hotmail.com','Course Admission','Wanted to join a course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Marques','8547643673','kmarques@yahoo.com','Course Review','Review of Development course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Shyam','4365328776','gshyam@gmail.com','Admission Query','Need to talk about admission','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('John','5465765453','doejohn@gmail.com','Holiday Query','Query on upcoming holidays','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Taniya Bell','3987463827','belltaniya@gmail.com','Child Scholarship','Can my child get scholarship?','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Willie Lara','4568764801','476lara@gmail.com','Need Admission','My son need an admission','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Jonathan Parsons','4321768902','jonathan.parsons@gmail.com','Course feedback','Music course is good','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Cloe Rubio','9854438719','rubio987@gmail.com','Correct Date of Birth','My Child DOB needs to be corrected','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Camilla Stein','6545433254','camillas@gmail.com','Transport Query','Is Transport provided?','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Lizeth Gross','4678783434','grossliz@yahoo.com','Progress report','Please send progress report','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Yael Howe','1243563254','howeyael@gmail.com','Certificate Query','Need Certificate hard copy','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Ian Moreno','2312231223','moreno.ian@gmail.com','Food feedback','Food quality can be improved','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Desirae Ibarra','3445235667','ibarrades@gmail.com','Traffic Complaint','Traffic around school can be controlled','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Oswaldo Jarvis','4556121265','jarvissmile@hotmail.com','Study Tour','Study tour details needed','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Miah Perkins','2367784512','perkinsmiah@hotmail.com','Vaccination Support','Vaccination center in the school','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Zion Bolton','8990678900','boltzion@gmail.com','Course fees','Pls share fees of music course','Open',CURDATE(),'DBA');

INSERT INTO `contact_msg` (`name`,`mobile_num`,`email`,`subject`,`message`,`status`,`created_at`, `created_by`)
VALUES ('Dominik Tanner','4556127834','tannerdominik@gmail.com','Games schedule','Provide Summer games schedule','Open',CURDATE(),'DBA');