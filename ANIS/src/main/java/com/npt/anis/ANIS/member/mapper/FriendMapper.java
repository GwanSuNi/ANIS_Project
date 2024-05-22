package com.npt.anis.ANIS.member.mapper;

import com.npt.anis.ANIS.lecture.domain.dto.LectureDto;
import com.npt.anis.ANIS.lecture.domain.entity.Lecture;
import com.npt.anis.ANIS.member.domain.dto.FriendDto;
import com.npt.anis.ANIS.member.domain.entity.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    FriendDto toDto(Optional<Friend> friend);
    Friend toEntity(FriendDto friendDto);

    List<Friend> toEntities(List<FriendDto> friendDtoList);
    List<LectureDto> toDtos(List<Friend> friendList);
}
