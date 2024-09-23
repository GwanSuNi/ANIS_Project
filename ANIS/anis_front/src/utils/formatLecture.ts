import {Lecture} from '@types';

// 받아오는 LectureDto 타입과 매핑시켜주는 함수
const formatLecture = (lecture: any): Lecture => ({
    lecID: lecture.lecID,
    lpIndex: lecture.lpIndex,
    lecYear: lecture.lecYear,
    lecSemester: lecture.lecSemester,
    lecName: lecture.lecName,
    lecProfessor: lecture.lecProfessor,
    lectureRoom: lecture.lectureRoom,
    lecDay: lecture.lecDay,
    // 받아오는 LectureDto 의 LocalTime 이 9:00:00 이렇게 받아오는데
    // TimeTable에서는 9:00 으로 표시하기 때문에 길이 3을 뺀다
    lecTimeStart: lecture.lecTimeStart.substring(0, lecture.lecTimeStart.length - 3),
    lecTimeEnd: lecture.lecTimeEnd,
    lecCredit: lecture.lecCredit,
    lecGrade: lecture.lecGrade
});

export default formatLecture;
