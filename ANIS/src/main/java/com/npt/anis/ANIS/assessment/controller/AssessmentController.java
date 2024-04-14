package com.npt.anis.ANIS.assessment.controller;

import com.npt.anis.ANIS.assessment.domain.dto.AssessmentDto;
import com.npt.anis.ANIS.assessment.domain.dto.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.UpdateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.mapper.AssessmentMapper;
import com.npt.anis.ANIS.assessment.service.AssessmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;
    private final AssessmentMapper assessmentMapper;

    /**
     * Assessment 생성 API
     *
     * @param request Assessment 생성 요청 정보를 담은 dto
     * @return HTTP 상태 코드가 CREATED(201)이고, 생성된 Assessment의 정보를 담은 dto를 반환
     */
    @PostMapping
    public ResponseEntity<AssessmentDto> createAssessment(@Valid @RequestBody CreateAssessmentRequestDto request) {
        AssessmentDto dto = assessmentMapper.toDto(request);
        dto = assessmentService.save(dto);

        log.info("Controller: Created Assessment: {}", dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Assessment를 가져오는 API
     *
     * @param id 가져오려는 Assessment의 id
     * @return HTTP 상태 코드가 OK(200)이고, 가져온 Assessment의 정보를 담은 dto를 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentDto> getAssessmentById(@PathVariable Long id) {
        AssessmentDto dto = assessmentService.findById(id);

        log.info("Controller: Fetched Assessment: {}", dto);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * 모든 Assessment를 가져오는 API
     *
     * @return HTTP 상태 코드가 OK(200)이고, 모든 Assessment의 정보를 담은 dto 리스트를 반환
     */
    @GetMapping
    public ResponseEntity<List<AssessmentDto>> getAllAssessments() {
        List<AssessmentDto> dtos = assessmentService.findAll();

        log.info("Controller: Fetched {} Assessments", dtos.size());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssessmentDto> updateAssessment(@PathVariable Long id, @RequestBody UpdateAssessmentRequestDto request) {
        AssessmentDto dto = assessmentMapper.toDto(request);
        dto = assessmentService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    /**
     * Assessment를 삭제하는 API
     *
     * @param id 삭제하려는 Assessment의 id
     * @return HTTP 상태 코드 Content(204) 반환. 이는 성공적으로 리소스가 삭제되었음을 나타냄
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        assessmentService.delete(id);

        log.info("Controller: Deleted Assessment with ID: {}", id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
