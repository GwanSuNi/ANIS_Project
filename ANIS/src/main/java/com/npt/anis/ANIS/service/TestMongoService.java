package com.npt.anis.ANIS.service;

import com.npt.anis.ANIS.domain.TestMongo;
import com.npt.anis.ANIS.repository.TestMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestMongoService {
    @Autowired
    private TestMongoRepository testMongoRepository;

    public void saveUser(TestMongo testMongo) {
        testMongoRepository.save(testMongo);
    }

    public TestMongo getUserById(String id) {
        return testMongoRepository.findById(id).orElse(null);
    }
}
