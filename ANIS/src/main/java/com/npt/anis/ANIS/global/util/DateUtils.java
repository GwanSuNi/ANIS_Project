package com.npt.anis.ANIS.global.util;

import java.time.LocalDate;
// 시간 관련해서 자주 사용하는 메서드들이 너무 많아 재사용을 위한 UtilClass 생성
public class DateUtils {
    /***
     * 현재 몇학기인지 반환하는 메서드
     * @return 1학기면 1 2학기면 2 반환
     */
    public static int getCurrentSemester() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        //
        if (month >= 3 && month <= 7) {
            return 1;
        } else {
            return 2;
        }
    }

    /***
     * 현재년도 갖고오는 메서드
     * @return 현재년도로 정수값 반환
     */
    public static int getCurrentYear() {
        LocalDate now = LocalDate.now();
        return now.getYear();
    }

    /***
     * 현재년도와 학기인지 아닌지 검증하는
     * @param semester 학기
     * @param year 년도
     * @return 현재년도와 학기가 맞으면 true 아니면 false 반환
     */
    public static boolean isCurrentSemester(int semester, int year) {
        int currentSemester = getCurrentSemester();
        int currentYear = getCurrentYear();
        return semester == currentSemester && year == currentYear;
    }
}
