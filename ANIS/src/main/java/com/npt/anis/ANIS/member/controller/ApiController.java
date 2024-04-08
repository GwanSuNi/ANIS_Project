package com.npt.anis.ANIS.member.controller;

import com.npt.anis.ANIS.nosql.domain.entity.TestMongo;
import com.npt.anis.ANIS.member.domain.entity.TestUser;
import com.npt.anis.ANIS.nosql.service.TestMongoService;
import com.npt.anis.ANIS.member.service.TestUserService;
import com.npt.anis.ANIS.nosql.domain.entity.Student;
//import com.npt.anis.ANIS.nosql.service.StudentService;
import com.npt.anis.ANIS.nosql.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    TestMongoService testMongoService;
    @Autowired
    TestUserService testUserService;
    @Autowired
    StudentService studentService;
    @GetMapping("test")
    public String test() {
        TestMongo testMongo = new TestMongo("sibjagun2","seosang","kyungmin");
        TestUser testUser = new TestUser(1,"seosang");
        testMongoService.saveUser(testMongo);
        testUserService.saveUser(testUser);
        List<String> doneSurveys = new ArrayList<>();
        List<String> participatedSurveys = new ArrayList<>();
        Student student = new Student(2,doneSurveys,participatedSurveys);
        doneSurveys.add("1");
        doneSurveys.add("2");
        doneSurveys.add("5");
        participatedSurveys.add("3");
        participatedSurveys.add("4");
        participatedSurveys.add("6");
        studentService.saveStudent(student);

        return "index";
    }

}
