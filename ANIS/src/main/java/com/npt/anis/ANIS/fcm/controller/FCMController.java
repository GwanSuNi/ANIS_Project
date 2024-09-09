package com.npt.anis.ANIS.fcm.controller;

import com.npt.anis.ANIS.fcm.dto.ApiResponseDto;
import com.npt.anis.ANIS.fcm.dto.TokenNotificationRequestDto;
import com.npt.anis.ANIS.fcm.service.FCMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FCMController {
    private final FCMService fcmService;

    // 한 명에게 fcm을 보낸다 (token)
    @PostMapping("/notification/token")
    public ResponseEntity<ApiResponseDto> sendMessageToken(@RequestBody TokenNotificationRequestDto tokenNotificationRequestDto, Principal principal) {
        return fcmService.sendByToken(tokenNotificationRequestDto, principal);
    }

    // 여러명에게 fcm 보내기 (token)
    @PostMapping("/notification/tokens")
    public ResponseEntity<ApiResponseDto> sendMessageTokens(@RequestBody TokenNotificationRequestDto tokenNotificationRequestDto) {
        return fcmService.sendByToken(tokenNotificationRequestDto);
    }

//    // fcm을 보낸다 (topic)
//    @PostMapping("/notification/topic")
//    public ResponseEntity<ApiResponseDto> sendMessageTopic(@RequestBody TopicNotificationRequestDto topicNotificationRequestDto) {
//        return fcmService.sendByTopic(topicNotificationRequestDto);
//    }


}
