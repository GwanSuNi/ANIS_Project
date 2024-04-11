package com.npt.anis.ANIS.assessment.service;

import com.npt.anis.ANIS.assessment.domain.dto.AssessmentDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import com.npt.anis.ANIS.assessment.domain.mapper.AssessmentMapper;
import com.npt.anis.ANIS.assessment.repository.AssessmentRepository;
import com.npt.anis.ANIS.global.exception.ResourceNotFoundException;
import com.npt.anis.ANIS.global.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;

    /**
     * Assessment를 DB에 저장하고 저장된 Assessment를 AssessmentDto로 변환해서 반환하는 서비스 메서드
     *
     * @param dto DB 저장할 AssessmentDto. dto의 asIndex(pk)가 null 이어야 함
     * @return DB에 저장된 AssessmentDto. 저장된 AssessmentDto는 새로운 ID를 포함
     */
    @Override
    public AssessmentDto save(AssessmentDto dto) {
        validateDto(dto);

        Assessment assessment = assessmentMapper.toEntity(dto);
        assessment = assessmentRepository.save(assessment);

        AssessmentDto savedDto = assessmentMapper.toDto(assessment);

        log.info("Service: Saved Assessment: {}", savedDto);

        return savedDto;
    }

    /**
     * 파라미터로 받은 ID에 해당하는 Assessment를 찾아서 반환하는 서비스 메서드
     *
     * @param id 찾고자 하는 Assessment의 id
     * @return 찾아진 Assessment의 dto
     * @throws ResourceNotFoundException id에 해당하는 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    @Transactional(readOnly = true)
    public AssessmentDto findById(Long id) {
        validateId(id);

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: " + id));

        AssessmentDto assessmentDto = assessmentMapper.toDto(assessment);

        log.info("Service: Found assessment with id {}: {}", id, assessmentDto);

        return assessmentDto;
    }

    /**
     * 모든 Assessment를 조회하는 서비스 메서드
     *
     * @return 조회된 Assessment의 리스트를 AssessmentDto 리스트로 반환
     */
    @Override
    @Transactional(readOnly = true)
    public List<AssessmentDto> findAll() {
        List<Assessment> assessments = assessmentRepository.findAll();
        List<AssessmentDto> assessmentDtos = assessmentMapper.toDtos(assessments);

        log.info("Service: Found all assessments: {}", assessmentDtos);

        return assessmentDtos;
    }

    /**
     * Assessment를 수정하는 서비스 메서드
     *
     * @param id 수정할 Assessment의 id
     * @param dto 수정할 내용을 포함하는 AssessmentDto. 반드시 dto에 모든 값이 있어야 함
     * @return 수정된 AssessmentDto를 반환
     * @throws ResourceNotFoundException 수정할 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    public AssessmentDto update(Long id, AssessmentDto dto) {
        validateId(id);

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: " + id));

        assessmentMapper.updateFromDto(assessment, dto);
        assessmentRepository.save(assessment);
        AssessmentDto updatedDto = assessmentMapper.toDto(assessment);

        log.info("Service: Updated assessment: {}", updatedDto);

        return updatedDto;
    }


    /**
     * 파라미터로 받은 ID에 해당하는 Assessment를 삭제하는 서비스 메서드
     *
     * @param id 삭제할 Assessment의 id
     * @throws ResourceNotFoundException 삭제할 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    public void delete(Long id) {
        validateId(id);

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: " + id));

        assessmentRepository.delete(assessment);

        log.info("Service: Deleted assessment with id: {}", id);
    }

    // id 유효성 검사하는 메서드
    private void validateId(Long id) {
        if (id == null)
            throw new ValidationException("ID cannot be null");
    }

    // AssessmentDto 유효성 검사하는 메서드
    private void validateDto(AssessmentDto dto) {
        if (dto == null)
            throw new ValidationException("AssessmentDto cannot be null");

        if (dto.getLecId() == null)
            throw new ValidationException("lecId cannot be null");

        if (dto.getDiagnosisArea() == null || dto.getDiagnosisArea().isEmpty())
            throw new ValidationException("diagnosisArea cannot be null or empty");

        if (dto.getDiagnosisQuestion() == null || dto.getDiagnosisQuestion().isEmpty())
            throw new ValidationException("diagnosisQuestion cannot be null or empty");
    }
}
