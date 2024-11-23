package com.npt.anis.ANIS.assessment.controller;

import com.npt.anis.ANIS.assessment.domain.dto.request.CreateAssessmentRequestDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.CreateAssessmentResponseDto;
import com.npt.anis.ANIS.assessment.domain.dto.response.GetAssessmentResponseDto;
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
     * Assessment를 생성하는 API
     *
     * @param request 생성할 Assessment의 정보를 담은 dto
     * @return HTTP 상태 코드가 CREATED(201)이고, 생성된 Assessment의 정보를 담은 dto를 반환
     */
    @PostMapping
    public ResponseEntity<CreateAssessmentResponseDto> createAssessment(@Valid @RequestBody CreateAssessmentRequestDto request) {
        CreateAssessmentResponseDto response = assessmentService.createAssessment(request);

        log.info("Controller: Created assessment: {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ID에 해당하는 Assessment를 가져오는 API
     *
     * @param id 가져오려는 Assessment의 id
     * @return HTTP 상태 코드가 OK(200)이고, ID에 해당하는 Assessment의 정보를 담은 dto를 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetAssessmentResponseDto> getAssessment(@PathVariable Long id) {
        GetAssessmentResponseDto response = assessmentService.findById(id);

        log.info("Controller: Fetched assessment with ID {}: {}", id, response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 모든 Assessment를 가져오는 API
     *
     * @return HTTP 상태 코드가 OK(200)이고, 모든 Assessment의 정보를 담은 dto 리스트를 반환
     */
    @GetMapping
    public ResponseEntity<List<GetAssessmentResponseDto>> getAllAssessments() {
        List<GetAssessmentResponseDto> response = assessmentService.findAll();

        log.info("Controller: Fetched {} assessments: {}", response.size(), response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AssessmentDto> updateAssessment(@PathVariable Long id, @RequestBody UpdateAssessmentRequestDto request) {
//        AssessmentDto dto = assessmentMapper.toDto(request);
//        dto = assessmentService.update(id, dto);
//
//        return ResponseEntity.status(HttpStatus.OK).body(dto);
//    }

    /**
     * ID에 해당하는 Assessment를 삭제하는 API
     *
     * @param id 삭제하려는 Assessment의 id
     * @return HTTP 상태 코드 NO_CONTENT(204)를 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        assessmentService.deleteById(id);
        log.info("Controller: Deleted assessment with ID: {}", id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
