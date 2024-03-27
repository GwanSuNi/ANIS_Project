package com.npt.anis.ANIS.member.controller;

import com.npt.anis.ANIS.member.domain.entity.TestMongo;
import com.npt.anis.ANIS.member.domain.entity.TestUser;
import com.npt.anis.ANIS.member.service.TestMongoService;
import com.npt.anis.ANIS.member.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    TestMongoService testMongoService;
    @Autowired
    TestUserService testUserService;
    @GetMapping("test")
    public String test() {
        TestMongo testMongo = new TestMongo("sibjagun2","seosang","kyungmin");
        TestUser testUser = new TestUser(1,"seosang");
        testMongoService.saveUser(testMongo);
        testUserService.saveUser(testUser);
        return "index";
    }

}
