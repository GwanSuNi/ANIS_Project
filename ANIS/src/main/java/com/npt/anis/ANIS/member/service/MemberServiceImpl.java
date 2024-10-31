package com.npt.anis.ANIS.member.service;

import com.npt.anis.ANIS.department.service.DepService;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberExcelDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchByAdminDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.member.mapper.MemberMapper;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final DepService depService;

    @Override
    public MemberSearchDTO getMemberSearch(String studentID) {
        // 일단 MemberSearchDTO로 만들어서 가려진 값만 리턴하게 했는데, 어드민이 조회하는 메서드를 따로 만들어야하나?
        return memberRepository.findByStudentIDToSearchDTO(studentID);
    }

    @Override
    public MemberDTO getMemberDetail(String studentID) {
        return memberMapper.toDTO(memberRepository.findByStudentID(studentID));
    }

    @Override
    public List<MemberSearchByAdminDto> getMembersByAdmin() {
        return memberRepository.findAllByAdmin();
    }

    @Override
    public List<MemberSearchDTO> getAnyMembers(MemberSearchDTO memberSearchDTO) {
        return memberRepository.findByStudentNameContainingOrBirthContainingOrStudentIDContainingOrDepartmentNameContaining(
                memberSearchDTO.getStudentName(), memberSearchDTO.getBirth(), memberSearchDTO.getStudentID(),
                memberSearchDTO.getDepartmentName());
    }

    @Override
    public MemberSearchByAdminDto updateMember(MemberSearchByAdminDto memberDTO) {
        Member member = memberRepository.findByStudentID(String.valueOf(memberDTO.getStudentID()));
        if (member == null) {
            return null;
        }
        member.setStudentName(memberDTO.getStudentName());
        member.setQuit(memberDTO.getIsQuit().equals("탈퇴"));
        member.setBirth(memberDTO.getBirth().replace("-", ""));
        member.setDepartmentID(depService.getDepartmentByName(memberDTO.getDepName()).getDepIndex());
        switch (memberDTO.getRole()) {
            case "관리자":
                member.setRole("ROLE_ADMIN");
                break;
            case "학생": member.setRole("ROLE_MEMBER");
        }
        Member updatedMember = memberRepository.save(member);
        return memberMapper.toMemberSearchByAdminDto(updatedMember);
    }

    @Override
    public int toggleMemberQuit(String[] memberIDs) {
        int count = 0;
        for (String memberID : memberIDs) {
            Member member = memberRepository.findByStudentID(memberID);
            if (member == null) {
                continue;
            }
            member.toggleQuitUser();
            count++;
        }
        return count;
    }

    @Override
    public List<MemberSearchDTO> getMembersByDepName(String depName) {
        return memberRepository.findMembersByDepartmentName(depName);
    }

    @Override
    public int insertMemberByExcel(MemberExcelDto memberExcelDto) {
        return 0;
    }
}
