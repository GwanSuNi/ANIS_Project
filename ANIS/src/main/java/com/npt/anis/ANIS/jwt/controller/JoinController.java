package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.jwt.dto.JoinDTO;
import com.npt.anis.ANIS.jwt.service.JoinService;
import com.npt.anis.ANIS.member.domain.dto.MemberExcelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    // TODO: 가입 실패시 처리
    @PostMapping("/join")
    public ResponseEntity<String> join(@Value("${spring.password}") String password, @RequestBody JoinDTO joinDto) { // 설정파일에 있는 고정된 비밀번호 사용
        joinService.joinProcess(password, joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: /admin/join이 시큐리티 권한에 있어서 더 관리하기 용이하지 않을까?
    @PostMapping("/join/admin")
    public ResponseEntity<String> joinAdmin(@RequestBody JoinDTO joinDto) { // 관리자 유저는 개인의 비밀번호를 사용
        joinService.joinProcess(joinDto.getPassword(), joinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/joinList")
    public ResponseEntity<String> join(@Value("${spring.password}") String password, @RequestBody List<JoinDTO> joinDTOList) {
        joinService.joinProcess(password, joinDTOList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 엑셀로 가입
    @Secured("ROLE_ADMIN")
    @PostMapping("/join/excel")
    public ResponseEntity<String> joinExcel(@Value("${spring.password}") String password, @RequestParam("file") MultipartFile file) {
        int count;
        long start = System.currentTimeMillis();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet workSheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            List<MemberExcelDto> memberExcelDtoList = new ArrayList<>();

            // 학생 정보가 시작되는 첫 행 번호 찾기
            int rowIndex = 0;
            for (int i = 0; i < workSheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = workSheet.getRow(i);
                if (formatter.formatCellValue(row.getCell(0)).equals("학과")) {
                    rowIndex = i + 1;
                    break;
                }
            }
            // 학생 정보 추출
            for (int i = rowIndex; i < workSheet.getPhysicalNumberOfRows(); i++) {
                MemberExcelDto excel = new MemberExcelDto();
                XSSFRow row = workSheet.getRow(i);

                excel.setDepName(formatter.formatCellValue(row.getCell(0)));
                excel.setStudentID(formatter.formatCellValue(row.getCell(1)));
                excel.setStudentName(formatter.formatCellValue(row.getCell(2)));
                excel.setGrade(formatter.formatCellValue(row.getCell(3)));

                memberExcelDtoList.add(excel);
            }
            long end = System.currentTimeMillis();
            log.info("엑셀 시간: {}", (end - start) /1000.0);

            long start2 = System.currentTimeMillis();
            count = joinService.joinByExcel(password, memberExcelDtoList);
            long end2 = System.currentTimeMillis();
            log.info("DB 시간: {}", (end2 - start2) /1000.0);
        } catch (IOException ioException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.warn("엑셀로 학생 정보 등록 중 오류 발생");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(count + "명 가입", HttpStatus.OK);
    }
}
