package com.npt.anis.ANIS.member.repository;

import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByStudentID(String studentID);
    List<Member> findByStudentNameContaining(String studentName);
    List<Member> findByBirthContaining(String birth);
    List<Member> findByRoleContaining(String role);
    List<Member> findByStudentNameContainingOrStudentIDContaining(String birth, String studentID);
    @Query("SELECT m FROM Member m JOIN Department d WHERE m.departmentID = d.depIndex and m.studentName LIKE %:name% OR d.depName LIKE %:departmentName%")
    List<Member> findByStudentNameContainingOrDepartmentNameContaining(String studentName, String departmentName);
    @Query("SELECT new com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO(m.studentID, m.studentName, m.birth, d.depName) FROM Member m JOIN Department d ON m.departmentID = d.depIndex WHERE m.studentName LIKE %:studentName% OR m.birth LIKE %:birth% OR m.studentID LIKE %:studentID% OR d.depName LIKE %:departmentName%")
    List<MemberSearchDTO> findByStudentNameContainingOrBirthContainingOrStudentIDContainingOrDepartmentNameContaining(@Param("studentName") String studentName,
                                                                                                             @Param("birth") String birth,
                                                                                                             @Param("studentID") String studentID,
                                                                                                             @Param("departmentName") String departmentName);

    List<Member> findByIsQuit(boolean isQuit);
    @Query("SELECT new com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO(m.studentID, m.studentName, m.birth, d.depName) FROM Member m JOIN Department d ON m.departmentID = d.depIndex WHERE d.depName like %:depName%")
    List<MemberSearchDTO> findMembersByDepartmentName(@Param("depName") String depName);
}