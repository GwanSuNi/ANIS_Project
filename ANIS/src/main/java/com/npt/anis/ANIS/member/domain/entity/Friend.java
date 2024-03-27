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

    // private Long myStuID;

    // private Long frStuID;
    // erd에 대한 얘기가 끝나지 않아서 비워둠
}
