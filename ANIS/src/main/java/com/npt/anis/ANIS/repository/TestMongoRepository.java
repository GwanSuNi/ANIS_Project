package com.npt.anis.ANIS.repository;

import com.npt.anis.ANIS.entity.TestMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMongoRepository extends MongoRepository<TestMongo, String> {
}
