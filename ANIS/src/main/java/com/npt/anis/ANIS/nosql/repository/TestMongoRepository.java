package com.npt.anis.ANIS.nosql.repository;

import com.npt.anis.ANIS.nosql.domain.entity.TestMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMongoRepository extends MongoRepository<TestMongo, String> {
}
