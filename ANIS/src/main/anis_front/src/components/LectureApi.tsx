import axios from 'axios';
import {useEffect, useState} from "react";
// 타입스크립트에서 export {} 안에 들어가는 요소들은 이미 모듈로 인정이 되어있고 그 안에 사용하는 타입 또한 모듈로 인정되어있어
// export 안에 타입만 정의한것을 넣으려면 모듈을 두번 export 하는 상황이 발생하여 타입스크립트에서 막는다
// 그래서 타입만을 다른곳에서 사용하고싶으면 타입만 따로 export 해줘야한다
export interface Lecture {
    lecID: number;
    lpIndex: number;
    lecYear: number;
    lecSemester: number;
    lecName: string;
    lecProfessor: string;
    lectureRoom: string;
    lecDay: string;
    lecTimeStart: string;
    lecTimeEnd: string;
    lecCredit: number;
    lecGrade: number;
}
// 받아오는 LectureDto 타입과 매핑시켜주는함수
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
    // TimeTable에서는 9:00 으로 표시하기때문에 길이 3을 뺀다
    lecTimeStart: lecture.lecTimeStart.substring(0, lecture.lecTimeStart.length - 3),
    lecTimeEnd: lecture.lecTimeEnd,
    lecCredit: lecture.lecCredit,
    lecGrade: lecture.lecGrade,
});
// lectureList를 반환하는 url 을 받아서 lectureList 에 대해 반환하는 함수
async function fetchTimetable(url: string): Promise<Lecture[]> {
    const response = await axios.get(url);
    const timetableData = response.data;
    const timetable: Lecture[] = timetableData.map((item: any) => formatLecture(item));
    return timetable;
}
// 현재 로그인되어있는 회원의 id로 값을 받아오기
const fetchSelectedLectures = async () => {
    return fetchTimetable("http://localhost:8080/api/lecture/lectureList");
};
// 현재 수강가능한 강의 모음 받아오기
const fetchAvailableLectures = async () => {
    return fetchTimetable("http://localhost:8080/api/lecture/availableLectureList");
};
// 해당 studentID 가 수강하는 강의 모음 받아오기
const fetchFriendLectureList = async (studentID: string) => {
    return fetchTimetable(`http://localhost:8080/api/lecture/lectureList/${studentID}`);
};

// Main , LectureApplication 에서 사용됨
const useFetchLectures = () => {
    // 현재시간 기준으로 수강 가능한 LectureList
    const [availableLectures, setAvailableLectures] = useState<Lecture[]>([]);
    // 내가 수강하고있는 LectureList 혹은 현재 내가 시간표에서 선택한 LectureList
    const [selectedLectures, setSelectedLectures] = useState<Lecture[]>([]);
    // TODO 에러처리
    useEffect(() => {
        const fetchData = async () => {
            const available = await fetchAvailableLectures();
            const selected = await fetchSelectedLectures();
            setAvailableLectures(available);
            setSelectedLectures(selected);
        };
        fetchData();
    }, []);

    return { availableLectures, selectedLectures, setSelectedLectures};
};

// LectureApplication 에서 사용됨
// onClick 수강신청하기(enrollment)
const registrations = async (selectedLectures: Lecture[]) => {
    try {
        // selectedLectures를 서버로 보내는 HTTP POST 요청
        const response = await axios.post("http://localhost:8080/api/registrations", selectedLectures);
        if (response.data) {
            alert("수강신청이 완료되었습니다.");
        } else {
            alert("수강신청에 실패하였습니다.");
        }
    } catch (error) {
        console.error("수강신청 중 오류가 발생했습니다:", error);
    }
};

export {registrations,useFetchLectures,fetchSelectedLectures,
    fetchAvailableLectures,fetchFriendLectureList,fetchTimetable,formatLecture}