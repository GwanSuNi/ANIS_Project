package com.npt.anis.ANIS.member.service;

import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
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

    @Override
    public MemberSearchDTO getMember(String studentID) {
        // 일단 MemberSearchDTO로 만들어서 가려진 값만 리턴하게 했는데, 어드민이 조회하는 메서드를 따로 만들어야하나?
        Member member = memberRepository.findByStudentID(studentID);
        return memberMapper.toMemberSearchDTO(member);
    }

    @Override
    public List<MemberDTO> getMembersByAdmin() {
        List<Member> members = memberRepository.findAll();
        return memberMapper.toDTOs(members);
    }

    @Override
    public List<MemberSearchDTO> getAnyMembers(MemberSearchDTO memberSearchDTO) {
        return memberRepository.findByStudentNameContainingOrBirthContainingOrStudentIDContainingOrDepartmentNameContaining(memberSearchDTO.getStudentName(), memberSearchDTO.getBirth(), memberSearchDTO.getStudentID(), memberSearchDTO.getDepartmentName());
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) {
        Member member = memberRepository.findByStudentID(memberDTO.getStudentID());
        if (member == null) {
            return null;
        }
        member.setStudentName(memberDTO.getStudentName());
        member.setBirth(memberDTO.getBirth());
        member.setStudentID(memberDTO.getStudentID());
        member.setDepartmentID(Long.valueOf(memberDTO.getDepartmentID()));
        Member updatedMember = memberRepository.save(member);
        return memberMapper.toDTO(updatedMember);
    }

    @Override
    public List<MemberSearchDTO> getMembersByDepName(String depName) {
        return memberRepository.findMembersByDepartmentName(depName);
    }
}
