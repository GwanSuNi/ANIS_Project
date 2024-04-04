package com.npt.anis.ANIS.member.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    private Long frIndex;
    // Member FK
     private Long myStuID;
    // 친구 신청한 아이디
     private Long frStuID;
}
