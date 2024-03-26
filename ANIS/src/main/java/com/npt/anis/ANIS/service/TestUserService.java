package com.npt.anis.ANIS.service;

import com.npt.anis.ANIS.domain.TestUser;
import com.npt.anis.ANIS.repository.TestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserService {
    @Autowired
    private TestUserRepository testUserRepository;

    public void saveUser(TestUser user) {
        testUserRepository.save(user);
    }

    public TestUser getUserById(long id) {
        return testUserRepository.findById(id).orElse(null);
    }
}
