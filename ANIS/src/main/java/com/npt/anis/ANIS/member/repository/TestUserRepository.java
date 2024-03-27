package com.npt.anis.ANIS.member.repository;

import com.npt.anis.ANIS.member.domain.entity.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<TestUser,Long> {
}
