package com.npt.anis.ANIS.fcm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npt.anis.ANIS.fcm.dto.FCMTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    // Sorted Set에 FCM 토큰 저장 (점수로 만료 시간 사용)
    public void saveToken(FCMTokenDto tokenDto) {
        String studentID = tokenDto.getStudentID();
        String jsonTokenDto = convertToJson(tokenDto);
        // 저장할 때 String 형태로 저장
        stringRedisTemplate.opsForZSet().add(studentID, jsonTokenDto, tokenDto.getExpirationTime().toEpochSecond(ZoneOffset.UTC));
    }


    // 특정 studentID의 모든 토큰 가져오기 (만료 시간 순서로 정렬된 상태)
    public Set<FCMTokenDto> getTokens(String studentID) {
        Set<FCMTokenDto> tokens = new HashSet<>();
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().rangeWithScores(studentID, 0, -1);

        if (tuples != null) {
            for (ZSetOperations.TypedTuple<String> tuple : tuples) {
                String jsonTokenDto = tuple.getValue();
                FCMTokenDto tokenDto = convertFromJson(jsonTokenDto);
                tokens.add(tokenDto);
            }
        }
        return tokens;
    }

    // 특정 studentID와 특정 token의 토큰 가져오기
    public FCMTokenDto getToken(String studentID, String fcmToken) {
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().rangeWithScores(studentID, 0, -1);

        if (tuples != null) {
            for (ZSetOperations.TypedTuple<String> tuple : tuples) {
                String jsonTokenDto = tuple.getValue();
                FCMTokenDto tokenDto = convertFromJson(jsonTokenDto);
                if (tokenDto.getFcmToken().equals(fcmToken)) {
                    return tokenDto;
                }
            }
        }
        return null;
    }

    // 만료된 토큰 삭제 (현재 시간보다 오래된 토큰들 삭제)
    public void removeExpiredTokens(String studentID) {
        long now = System.currentTimeMillis();

        // 현재 시간보다 작은 점수를 가진 토큰을 삭제
        stringRedisTemplate.opsForZSet().removeRangeByScore(studentID, 0, now);
    }

    // 특정 토큰 삭제
    public boolean deleteToken(String studentID, String token) {
        return stringRedisTemplate.opsForZSet().remove(studentID, token) > 0;
    }

    // 토큰이 존재하는지 확인
    public boolean hasToken(String studentID) {
        List<Double> score = stringRedisTemplate.opsForZSet().score(studentID);
        return score != null;
    }

    // 특정 토큰이 존재하는지 확인
    public boolean hasToken(String studentID, String token) {
        Double score = stringRedisTemplate.opsForZSet().score(studentID, token);
        return score != null;
    }

    private String convertToJson(FCMTokenDto tokenDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(tokenDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert FCMTokenDto to JSON", e);
        }
    }

    private FCMTokenDto convertFromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, FCMTokenDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to FCMTokenDto", e);
        }
    }
}
