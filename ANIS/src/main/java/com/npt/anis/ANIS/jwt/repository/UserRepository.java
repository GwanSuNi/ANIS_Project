package com.npt.anis.ANIS.jwt.repository;

import com.npt.anis.ANIS.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, String> {
    Member findByStudentID(String username);
    boolean existsByStudentID(String username);
}