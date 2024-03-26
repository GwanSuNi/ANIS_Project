package com.npt.anis.ANIS.repository;

import com.npt.anis.ANIS.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser,Long> {
}
