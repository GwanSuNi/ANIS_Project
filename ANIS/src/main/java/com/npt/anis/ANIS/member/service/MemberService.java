package com.npt.anis.ANIS.member.service;

import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;

import java.util.List;

public interface MemberService {
    MemberSearchDTO getMember(String studentID);
    /**
     * 자세한 사용자 정보를 조회하는 메서드
     * @return MemberDto
     */
    List<MemberDTO> getMembersByAdmin();
    /**
     * DTO 정보 중 하나라도 일치하는 사용자 정보를 조회하는 메서드
     * @param memberSearchDTO
     * @return List<MemberSearchDTO>
     */
    List<MemberSearchDTO> getAnyMembers(MemberSearchDTO memberSearchDTO);
    MemberDTO updateMember(MemberDTO memberDTO);
    List<MemberSearchDTO> getMembersByDepName(String departmentName);
}
