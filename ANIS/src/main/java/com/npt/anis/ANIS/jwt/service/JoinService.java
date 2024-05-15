package com.npt.anis.ANIS.jwt.service;

import com.npt.anis.ANIS.jwt.dto.JoinDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        data.setStudentName(joinDto.getStudentName());
        data.setDepartmentID(Long.valueOf(joinDto.getDepartmentId()));
        data.setRole("ROLE_" + joinDto.getRole());
        data.setBirth(joinDto.getBirth());
        data.setLastLogin(LocalDateTime.now());

        userRepository.save(data);
    }
}