package com.npt.anis.ANIS.member.mapper;

import com.npt.anis.ANIS.department.service.DepService;
import com.npt.anis.ANIS.member.domain.dto.MemberCreateDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchByAdminDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {DepService.class})
public interface MemberMapper {
    MemberDTO toDTO(Member member);
    @Mapping(source = "departmentID", target = "depName")
    @Mapping(source = "quit", target = "isQuit", qualifiedByName = "quitStatusToString")
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToString")
    @Mapping(source = "birth", target = "birth", qualifiedByName = "birthToFormattedString")
    MemberSearchByAdminDto toMemberSearchByAdminDto(Member member);
    Member toEntity(MemberDTO memberDto);
    Member toEntity(MemberSearchDTO memberSearchDTO);
    List<MemberDTO> toDTOs(List<Member> members);
    List<MemberSearchDTO> toMemberSearchDTOs(List<Member> members);
    List<Member> toEntities(List<MemberDTO> memberDTOs);

    @Named("quitStatusToString")
    default String quitStatusToString(boolean isQuit) {
        return isQuit ? "탈퇴" : "정상";
    }

    @Named("stringToQuitStatus")
    default boolean stringToQuitStatus(String isQuit) {
        return "탈퇴".equals(isQuit);
    }

    @Named("roleToString")
    default String roleToString(String role) {
        return switch (role) {
            case "ROLE_ADMIN" -> "관리자";
            case "ROLE_MEMBER" -> "학생";
            default -> null;
        };
    }

    @Named("birthToFormattedString")
    default String birthToFormattedString(String birth) {
        // 문자열에서 연도, 월, 일을 추출
        String year = birth.substring(0, 4);
        String month = birth.substring(4, 6);
        String day = birth.substring(6, 8);

        // 형식화된 문자열 반환
        return String.format("%s-%s-%s", year, month, day);
    }
}