package com.npt.anis.ANIS.jwt.repository;

import com.npt.anis.ANIS.member.domain.entity.Member;
import org.h2.command.query.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Member, String> {
    Member findByStudentID(String username);
    boolean existsByStudentID(String username);

    @Query("SELECT m.studentID FROM Member m")
    List<String> findAllStudentIDs();
}