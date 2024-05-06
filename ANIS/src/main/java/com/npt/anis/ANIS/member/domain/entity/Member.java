package com.npt.anis.ANIS.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    private String studentID;
    private Long departmentID;
    private String studentName;
    private String password;
    private String birth;
    // BLOB
    // 사용자 역할
    private String role;
    private LocalDateTime lastLogin = LocalDateTime.now();
    private boolean isQuit = false;

    @Builder
    public Member(String studentID, String password, String studentName, String birth, String role) { // TODO: DTO로 변경
        this.studentID = studentID;
        this.password = password;
        this.studentName = studentName;
        this.birth = birth;
        this.role = role;
    }

    public void quitUser() {

        isQuit = true;
    }
}
