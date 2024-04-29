package com.npt.anis.ANIS.service;

import com.npt.anis.ANIS.assessment.domain.dto.AssessmentDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import com.npt.anis.ANIS.assessment.domain.mapper.AssessmentMapper;
import com.npt.anis.ANIS.assessment.repository.AssessmentRepository;
import com.npt.anis.ANIS.assessment.service.AssessmentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssessmentService 테스트")
public class AssessmentServiceTest {
    @InjectMocks
    private AssessmentServiceImpl assessmentService;
    @Mock
    private AssessmentRepository assessmentRepository;
    @Mock
    private AssessmentMapper assessmentMapper;

    @Test
    @DisplayName("Assessment 저장 테스트")
    public void testSave() {
        // given
        Assessment assessment = new Assessment(1L, 1L, "Area", "Question");
        AssessmentDto dto = new AssessmentDto(1L, 1L, "Area", "Question");

        when(assessmentMapper.toEntity(dto)).thenReturn(assessment);
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        when(assessmentMapper.toDto(assessment)).thenReturn(dto);

        // when
        AssessmentDto savedDto = assessmentService.save(dto);

        // then
        assertThat(savedDto).isEqualTo(dto);
        verify(assessmentRepository, times(1)).save(assessment);
    }

    @Test
    @DisplayName("Assessment ID로 조회 테스트")
    public void testFindById() {
        // given
        Long id = 1L;
        Assessment assessment = new Assessment(1L, 1L, "Area", "Question");
        AssessmentDto expectedDto = new AssessmentDto(1L, 1L, "Area", "Question");

        when(assessmentRepository.findById(id)).thenReturn(Optional.of(assessment));
        when(assessmentMapper.toDto(assessment)).thenReturn(expectedDto);

        // when
        AssessmentDto foundDto = assessmentService.findById(id);

        // then
        assertThat(foundDto).isEqualTo(expectedDto);
        verify(assessmentRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("모든 Assessment 조회 테스트")
    public void testFindAll() {
        // given
        List<Assessment> assessments = new ArrayList<>();
        assessments.add(new Assessment(1L, 1L, "Area1", "Question1"));
        assessments.add(new Assessment(2L, 2L, "Area2", "Question2"));
        List<AssessmentDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new AssessmentDto(1L, 1L, "Area1", "Question1"));
        expectedDtos.add(new AssessmentDto(2L, 2L, "Area2", "Question2"));

        when(assessmentRepository.findAll()).thenReturn(assessments);
        when(assessmentMapper.toDtos(assessments)).thenReturn(expectedDtos);

        // when
        List<AssessmentDto> foundDtos = assessmentService.findAll();

        // then
        assertThat(foundDtos).isEqualTo(expectedDtos);
        verify(assessmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Assessment 업데이트 테스트")
    public void testUpdate() {
        // given
        Long id = 1L;
        Assessment existingAssessment = new Assessment(1L, 1L, "Area", "Question");
        AssessmentDto updateDto = AssessmentDto.builder().diagnosisArea("updated Area").build();
        Assessment updatedAssessment = new Assessment(1L, 1L, "Updated Area", "Question");
        AssessmentDto expectedDto = new AssessmentDto(1L, 1L, "Updated Area", "Question");

        when(assessmentRepository.findById(id)).thenReturn(Optional.of(existingAssessment));
        when(assessmentRepository.save(existingAssessment)).thenReturn(updatedAssessment);
        when(assessmentMapper.toDto(updatedAssessment)).thenReturn(expectedDto);

        // When
        AssessmentDto updatedDto = assessmentService.update(id, updateDto);

        // Then
        assertThat(updatedDto).isEqualTo(expectedDto);
        verify(assessmentRepository, times(1)).findById(id);
        Mockito.verify(assessmentMapper, Mockito.times(1)).updateFromDto(existingAssessment, updateDto);
        verify(assessmentRepository, times(1)).save(existingAssessment);
    }

    @Test
    @DisplayName("Assessment 삭제 테스트")
    public void testDelete() {
        // given
        Long id = 1L;
        Assessment assessment = new Assessment(1L, 1L, "Area", "Question");

        when(assessmentRepository.findById(id)).thenReturn(Optional.of(assessment));

        // When
        assessmentService.delete(id);

        // Then
        verify(assessmentRepository, times(1)).findById(id);
        verify(assessmentRepository, times(1)).delete(assessment);
    }
}
