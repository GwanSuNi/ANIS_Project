package com.npt.anis.ANIS.fcm.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenNotificationRequestDto {
    private List<String> studentIDs; // 수신자 ID 목록

    private String title;

    private String content;

    private String url;

    private String img;
}

