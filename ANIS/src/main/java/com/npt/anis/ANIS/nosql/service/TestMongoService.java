package com.npt.anis.ANIS.nosql.service;

import com.npt.anis.ANIS.nosql.domain.entity.TestMongo;
import com.npt.anis.ANIS.nosql.repository.TestMongoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
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
