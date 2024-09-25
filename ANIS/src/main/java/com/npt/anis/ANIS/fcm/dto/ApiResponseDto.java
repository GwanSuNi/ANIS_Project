package com.npt.anis.ANIS.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto {
    private HttpStatus successStatus;
    private String successContent;
    private Object Data;
}
