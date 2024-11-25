package com.npt.anis.ANIS.jwt.service;

import com.npt.anis.ANIS.department.dto.DepartmentDTO;
import com.npt.anis.ANIS.department.service.DepService;
import com.npt.anis.ANIS.jwt.dto.JoinDTO;
import com.npt.anis.ANIS.member.domain.dto.MemberExcelDto;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.jwt.repository.UserRepository;
import com.npt.anis.ANIS.member.repository.BatchMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DepService depService;
    private final BatchMemberRepository batchMemberRepository;

    public void joinProcess(String password, JoinDTO joinDto) {
        String username = joinDto.getUsername();
//        String password = joinDto.getPassword(); // 패스워드 고정 후 학번이 일치했을 때 로그인할 것임 -> QR코드 로그인
        boolean isExist = userRepository.existsByStudentID(username);
        if (isExist) {
            System.out.println("JoinService joinProcess: StudentID already exists");
            return;
        }
        Member data = new Member();
        data.setStudentID(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setGrade(joinDto.getGrade());
        data.setStudentName(joinDto.getStudentName());
        data.setDepartmentID(Long.valueOf(joinDto.getDepartmentId()));
        data.setRole("ROLE_" + joinDto.getRole());
        data.setBirth(joinDto.getBirth());
        data.setLastLogin(LocalDateTime.now());

        userRepository.save(data);
    }

    public void joinProcess(String password, List<JoinDTO> joinDtoList) {
        for (JoinDTO joinDto : joinDtoList) {
            joinProcess(password, joinDto);
        }
    }

    public int joinByExcel(String password, List<MemberExcelDto> memberExcelDtoList) {
        AtomicInteger count = new AtomicInteger();
        List<Member> memberList = new ArrayList<>();

        // 모든 studentID를 미리 조회
        List<String> existingStudentIDs = userRepository.findAllStudentIDs();

        // Set으로 변환하여 빠르게 검사할 수 있도록 함
        HashSet<String> existingStudentIDSet = new HashSet<>(existingStudentIDs);

        // 부서 정보를 캐싱하여 검색 속도 향상
        Map<String, Long> departmentMap = depService.getDepartments().stream()
                .collect(Collectors.toMap(DepartmentDTO::getDepName, DepartmentDTO::getDepIndex));


        // 각 학생의 비밀번호를 bCryptPasswordEncoder로 인코딩하는건, CPU 집약적인 과정이기 때문에, 이 과정에서 시간이 굉장히 오래걸렸음 (10명당 1초)
        // 이를 다중 스레드 처리하니, 215명을 처리하는데 3.8초로, 기존 20초에서 5배 이상의 성능 향상이 있었음
        // 이 때, Apple M1 CPU 8개 코어를 99% 로드
        // JDBC Batch Update로는 0.5초 향상. 200명대에선 미미한 성능 향상
        memberExcelDtoList.parallelStream().forEach(memberExcelDto -> {
            String username = memberExcelDto.getStudentID();

            if (existingStudentIDSet.contains(username)) {
                // studentID가 이미 존재할 경우 정보 업데이트
                Member existingMember = userRepository.findByStudentID(username);
                if (existingMember != null) {
                    // 변경할 정보만 업데이트
                    existingMember.setGrade(memberExcelDto.getGradeNumber());
                    existingMember.setStudentName(memberExcelDto.getStudentName());
                    Long departmentId = departmentMap.getOrDefault(memberExcelDto.getDepName(), -1L);
                    existingMember.setDepartmentID(departmentId);
                    existingMember.setLastLogin(LocalDateTime.now());
                    memberList.add(existingMember);
                    synchronized (this) {
                        count.getAndIncrement();
                    }
                    return;
                }
            }

            Member data = new Member();
            data.setStudentID(username);
            data.setPassword(bCryptPasswordEncoder.encode(password)); // 오래걸리는 작업
            data.setGrade(memberExcelDto.getGradeNumber());
            data.setStudentName(memberExcelDto.getStudentName());
            Long departmentId = departmentMap.getOrDefault(memberExcelDto.getDepName(), -1L);
            data.setDepartmentID(departmentId);
            data.setRole("ROLE_MEMBER");
            data.setBirth(generateRandomDate()); // 엑셀에서 생일 정보가 없어서 랜덤으로 처리
            data.setLastLogin(LocalDateTime.now());

            memberList.add(data);

            synchronized (this) {
                count.getAndIncrement();
            }
        });

        batchMemberRepository.saveAll(memberList);
        return count.get();
    }
    public String generateRandomDate() {
        // 시작 날짜 (2000-01-01)
        LocalDate startDate = LocalDate.of(1950, 1, 1);
        // 현재 날짜
        LocalDate endDate = LocalDate.now();

        // 시작 날짜와 종료 날짜 사이의 랜덤 일수를 계산
        long randomDays = ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay() + 1);

        // 랜덤 날짜 생성
        LocalDate randomDate = LocalDate.ofEpochDay(randomDays);

        // yyyyMMdd 형식으로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return randomDate.format(formatter);
    }
}