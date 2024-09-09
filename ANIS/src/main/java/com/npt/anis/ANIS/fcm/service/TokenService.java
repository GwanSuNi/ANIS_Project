package com.npt.anis.ANIS.fcm.service;

import com.npt.anis.ANIS.fcm.dto.FCMTokenDto;
import com.npt.anis.ANIS.member.domain.entity.Member;
import com.npt.anis.ANIS.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService {
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    // 토큰 만료 기간 상수 정의
    final int TOKEN_EXPIRATION_MONTHS = 2;

    @Transactional
    public void saveFCMToken(String studentID, String fcmToken) {
        log.info("saveFCMToken 메서드 호출");

        // token이 이미 있는지 체크
        if (redisService.hasToken(studentID, fcmToken)) {
            // 이미 존재하는 토큰의 만료일을 2개월 뒤로 연장
            FCMTokenDto token = redisService.getToken(studentID, fcmToken);
            log.info("이미 존재하는 토큰: " + token.getFcmToken());
            token.setExpirationTime(LocalDateTime.now().plusMonths(2));
            redisService.saveToken(token);
        } else {
            // Only create and save a new token if it does not exist
            FCMTokenDto token = FCMTokenDto.builder()
                    .fcmToken(fcmToken)
                    .studentID(studentID)
                    .expirationTime(LocalDateTime.now().plusMonths(TOKEN_EXPIRATION_MONTHS))
                    .build();
            log.info("DB에 저장하는 token : " + token.getFcmToken());
            redisService.saveToken(token);

//            // 사용자가 구독 중인 모든 토픽을 가져옴
//            List<TopicMember> topicMembers = topicMemberRepository.findByMember(member);
//            List<Topic> subscribedTopics = topicMembers.stream()
//                    .map(TopicMember::getTopic)
//                    .distinct()
//                    .collect(Collectors.toList());

//            // 새 토큰을 기존에 구독된 모든 토픽과 매핑하여 TopicToken 생성 및 저장
//            List<TopicToken> newSubscriptions = subscribedTopics.stream()
//                    .map(topic -> new TopicToken(topic, token))
//                    .collect(Collectors.toList());
//            topicTokenRepository.saveAll(newSubscriptions);
//
//            // 각 토픽에 대해 새 토큰 구독 처리
//            for (Topic topic : subscribedTopics) {
//                fcmService.subscribeToTopic(topic.getTopicName(), Collections.singletonList(token.getTokenValue()));
//                log.info("새 토큰으로 " + topic.getTopicName() + " 토픽을 다시 구독합니다.");
//            }
        }
    }
}
