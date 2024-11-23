package com.npt.anis.ANIS.assessment.service;

import com.npt.anis.ANIS.assessment.domain.dto.request.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentItemRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.request.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.CreateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.GetAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.UpdateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.entity.Assessment;
import com.npt.anis.ANIS.assessment.domain.entity.AssessmentItem;
import com.npt.anis.ANIS.assessment.domain.mapper.AssessmentItemMapper;
import com.npt.anis.ANIS.assessment.domain.mapper.AssessmentMapper;
import com.npt.anis.ANIS.assessment.repository.AssessmentItemRepository;
import com.npt.anis.ANIS.assessment.repository.AssessmentRepository;
import com.npt.anis.ANIS.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentServiceImpl implements AssessmentService {
    private final AssessmentRepository assessmentRepository;
    private final AssessmentItemRepository assessmentItemRepository;
    private final AssessmentMapper assessmentMapper;
    private final AssessmentItemMapper assessmentItemMapper;

    /**
     * Assessment를 생성하는 서비스 메서드
     *
     * @param requestDto 생성할 Assessment의 정보를 담은 DTO
     * @return 생성된 Assessment의 정보를 담은 DTO
     */
    @Override
    public CreateAssessmentResponseDto createAssessment(CreateAssessmentRequestDto requestDto) {
        Assessment assmt = assessmentMapper.toCreateAssessmentEntity(requestDto);
        List<AssessmentItem> assmtItems = assessmentItemMapper.toAssessmentItemEntities(assmt.getAssmtId(), requestDto.getAssmtItems());

        assmt = assessmentRepository.save(assmt);
        assmtItems = assessmentItemRepository.saveAll(assmtItems);

        CreateAssessmentResponseDto responseDto = assessmentMapper.toCreateAssessmentResponseDto(assmt, assmtItems);

        log.info("Service: Created assessment: {}", responseDto);

        return responseDto;
    }

    /**
     * ID에 해당하는 Assessment를 조회하는 서비스 메서드
     *
     * @param id 조회할 Assessment의 ID
     * @return 조회된 Assessment의 정보를 담은 DTO
     * @throws ResourceNotFoundException ID에 해당하는 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    @Transactional(readOnly = true)
    public GetAssessmentResponseDto findById(Long id) {
        Assessment assmt = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));
        List<AssessmentItem> assmtItems = assessmentItemRepository.findByAssmtId(assmt.getAssmtId());
        GetAssessmentResponseDto responseDto = assessmentMapper.toGetAssessmentResponseDto(assmt, assmtItems);

        log.info("Service: Found assessment with ID {}: {}", id, responseDto);

        return responseDto;
    }

    /**
     * 모든 Assessment를 조회하는 서비스 메서드
     *
     * @return 모든 Assessment의 정보를 담은 DTO 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<GetAssessmentResponseDto> findAll() {
        List<Assessment> assmts = assessmentRepository.findAll();
        List<Long> assmtIds = assmts.stream().map(Assessment::getAssmtId).toList();
        List<AssessmentItem> assmtItems = assessmentItemRepository.findAllByAssmtIdIn(assmtIds);
        Map<Long, List<AssessmentItem>> assessmentItemMap = assmtItems.stream()
                .collect(Collectors.groupingBy(AssessmentItem::getAssmtId));
        List<GetAssessmentResponseDto> responseDtos = assmts.stream()
                .map(assmt -> assessmentMapper.toGetAssessmentResponseDto(assmt, assessmentItemMap.get(assmt.getAssmtId()))).toList();

        log.info("Service: Found {} assessments: {}", responseDtos.size(), responseDtos);

        return responseDtos;
    }

    /**
     * Assessment를 수정하는 서비스 메서드
     *
     * @param id         수정할 Assessment의 id
     * @param requestDto 수정할 내용을 포함하는 AssessmentDto. 반드시 dto에 모든 값이 있어야 함
     * @return 수정된 AssessmentDto를 반환
     * @throws ResourceNotFoundException 수정할 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    public UpdateAssessmentResponseDto update(Long id, UpdateAssessmentRequestDto requestDto) {
        Assessment assmt = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));

        assessmentMapper.updateFromDto(assmt, requestDto);
        assmt = assessmentRepository.save(assmt);

        for (UpdateAssessmentItemRequestDto dto : requestDto.getAssmtItems()) {
            if (dto.isDeleted()) {
                assessmentItemRepository.deleteById(dto.getItemId());
            } else {
                AssessmentItem assmtItem = assessmentItemRepository.findById(dto.getItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("AssessmentItem not found with ID: " + dto.getItemId()));

                assessmentItemMapper.updateFromDto(assmtItem, dto);
                assessmentItemRepository.save(assmtItem);
            }
        }

        List<AssessmentItem> assmtItems = assessmentItemRepository.findByAssmtId(assmt.getAssmtId());
        UpdateAssessmentResponseDto responseDto = assessmentMapper.toUpdateAssessmentResponseDto(assmt, assmtItems);

        log.info("Service: Updated assessment: {}", requestDto);

        return responseDto;
    }

    /**
     * ID에 해당하는 Assessment를 삭제하는 서비스 메서드
     *
     * @param id 삭제할 Assessment의 ID
     * @throws ResourceNotFoundException ID에 해당하는 Assessment를 찾을 수 없는 경우 발생
     */
    @Override
    public void deleteById(Long id) {
        Assessment assmt = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));
        List<AssessmentItem> assmtItems = assessmentItemRepository.findByAssmtId(assmt.getAssmtId());

        assessmentRepository.delete(assmt);
        assessmentItemRepository.deleteAll(assmtItems);

        log.info("Service: Deleted assessment with ID: {}", id);
    }
}
