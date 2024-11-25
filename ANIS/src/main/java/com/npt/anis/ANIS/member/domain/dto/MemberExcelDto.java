package com.npt.anis.ANIS.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberExcelDto {
    private String depName;
    private String studentID;
    private String studentName;
    private String grade;

    public int getGradeNumber() {
        Pattern pattern = Pattern.compile("\\d+");  // 숫자 부분을 추출하기 위한 정규 표현식
        Matcher matcher = pattern.matcher(this.grade);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());  // 숫자 부분을 추출하고 int로 변환
        }

        throw new IllegalArgumentException("Grade does not contain a number");
    }
}
