package com.npt.anis.ANIS.nosql.repository;

import com.npt.anis.ANIS.nosql.domain.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
public interface StudentRepository extends MongoRepository <Student,String> {
}
