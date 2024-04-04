package com.npt.anis.ANIS.nosql.service;

import com.npt.anis.ANIS.nosql.domain.entity.Student;
import com.npt.anis.ANIS.nosql.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(Student student){
        studentRepository.save(student);
    }

}
