package com.npt.anis.ANIS.member.mapper;

import com.npt.anis.ANIS.member.domain.dto.MemberCreateDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDTO toDTO(Member member);
    Member toEntity(MemberDTO memberDto);
    Member toEntity(MemberSearchDTO memberSearchDTO);
    List<MemberDTO> toDTOs(List<Member> members);
    List<MemberSearchDTO> toMemberSearchDTOs(List<Member> members);
    List<Member> toEntities(List<MemberDTO> memberDTOs);
    }
}
