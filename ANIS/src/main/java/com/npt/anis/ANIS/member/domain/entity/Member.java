package com.npt.anis.ANIS.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private Long memberID;
    // private Long departmentID;
    private String studentName;
    private int birth;
    // BLOB
    // role SET
    private LocalDateTime lastLogin;
}
