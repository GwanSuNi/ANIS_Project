package com.npt.anis.ANIS.fcm.service;

import com.google.firebase.messaging.*;
import com.npt.anis.ANIS.fcm.dto.ApiResponseDto;
import com.npt.anis.ANIS.fcm.dto.FCMTokenDto;
import com.npt.anis.ANIS.fcm.dto.TokenNotificationRequestDto;
import com.npt.anis.ANIS.member.domain.dto.MemberSearchDTO;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import com.npt.anis.ANIS.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.FixedKeySet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FCMService {
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    // FireBase 토큰을 redis에 저장
    @Transactional
    public ResponseEntity<?> saveToken(FCMTokenDto fcmTokenDto, String studentID) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        if (memberRepository.findByStudentID(studentID) == null) {
            resultMap.put("message", "존재하지 않는 회원입니다.");
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(resultMap, status);
        }

        try {
            redisService.saveToken(fcmTokenDto);
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("exception", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> sendByToken(TokenNotificationRequestDto tokenNotificationRequestDto) {
        List<String> studentIDs = tokenNotificationRequestDto.getStudentIDs(); // 수신자 ID 목록

        // 실패한 토큰 리스트 초기화
        List<FCMTokenDto> failedTokens = new ArrayList<>();

        for (String studentID : studentIDs) {
            // 사용자 조회
            Member member = memberRepository.findByStudentID(studentID);

            // 사용자의 토큰들 조회
            Set<FCMTokenDto> tokenStrings = redisService.getTokens(member.getStudentID());

            for (FCMTokenDto token : tokenStrings) {
                log.info("메시지 보내는 토큰 " + token.getFcmToken());
                Message message = Message.builder()
                        .setToken(token.getFcmToken())
                        .setNotification(Notification.builder()
                                .setTitle(tokenNotificationRequestDto.getTitle())
                                .setBody(tokenNotificationRequestDto.getContent())
                                .setImage(tokenNotificationRequestDto.getImg())
                                .build())
                        .putData("click_action", tokenNotificationRequestDto.getUrl())
                        .build();
                try {
                    FirebaseMessaging.getInstance().send(message);
                } catch (FirebaseMessagingException e) {
                    failedTokens.add(token);
                    log.error("알림 전송에 실패한 token: " + token.getFcmToken(), e);
                }
            }
        }

        if (!failedTokens.isEmpty()) {
            log.warn("유효하지 않은 토큰 목록 : " + failedTokens.stream()
                    .map(FCMTokenDto::getFcmToken)
                    .toList());
            for (FCMTokenDto failedToken : failedTokens) {
                redisService.deleteToken(failedToken.getStudentID(), failedToken.getFcmToken());
            }
        }

        return ResponseEntity.ok().body(ApiResponseDto.builder()
                .successStatus(HttpStatus.OK)
                .successContent("푸시 알림 성공")
                .build()
        );
    }

    // 현재 로그인 한 사용자 한 명한테만 알림
    @Transactional
    public ResponseEntity<ApiResponseDto> sendByToken(TokenNotificationRequestDto tokenNotificationRequestDto, Principal principal) {
        String studentID = principal.getName();
        // 사용자 조회
        Member member = memberRepository.findByStudentID(studentID);

        // 사용자의 토큰들 조회
        Set<FCMTokenDto> tokenStrings = redisService.getTokens(member.getStudentID());

        // 실패한 토큰 리스트 초기화
        List<FCMTokenDto> failedTokens = new ArrayList<>();

        for (FCMTokenDto token : tokenStrings) {
            log.info("메시지 보내는 토큰 " + token.getFcmToken());
            Message message = Message.builder()
                    .setToken(token.getFcmToken())
                    .setNotification(Notification.builder()
                            .setTitle(tokenNotificationRequestDto.getTitle())
                            .setBody(tokenNotificationRequestDto.getContent())
                            .setImage(tokenNotificationRequestDto.getImg())
                            .build())
                    .putData("click_action", tokenNotificationRequestDto.getUrl())
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException e) {
                failedTokens.add(token);
                log.error("알림 전송에 실패한 token: " + token.getFcmToken(), e);
            }
        }

        if (!failedTokens.isEmpty()) {
            log.warn("유효하지 않은 토큰 목록 : " + failedTokens.stream()
                    .map(FCMTokenDto::getFcmToken)
                    .toList());
            for (FCMTokenDto failedToken : failedTokens) {
                redisService.deleteToken(member.getStudentID(), failedToken.getFcmToken());
            }
        }

        return ResponseEntity.ok().body(ApiResponseDto.builder()
                .successStatus(HttpStatus.OK)
                .successContent("푸시 알림 성공")
                .build()
        );
    }
}
