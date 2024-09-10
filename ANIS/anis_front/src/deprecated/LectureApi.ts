import {secInstance} from '@utils';
import {addSelectedLecture} from "@redux";
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
    const response = await secInstance.get(url);
    const timetableData = response.data;
    const timetable: Lecture[] = timetableData.map((item: any) => formatLecture(item));
    return timetable;
}

// 현재 로그인되어있는 회원의 id로 값을 받아오기
const fetchSelectedLectures = async () => {
    return fetchTimetable("/api/lecture/lectureList");
};
// 현재 수강가능한 강의 모음 받아오기
const fetchAvailableLectures = async () => {
    return fetchTimetable("/api/lecture/availableLectureList");
};
// 해당 studentID 가 수강하는 강의 모음 받아오기
const fetchFriendLectureList = async (studentID: string | undefined) => {
    return fetchTimetable(`/api/lecture/lectureList/${studentID}`);
};
// 프리셋 클릭시에 현재 학기와 현재 년도에 해당하는 프리셋 A,B,C를 갖고옴
const fetchLectureOfPreset = async (lecturePresetName: string) => {
    return fetchTimetable(`/api/lecture/lecturePreset/${lecturePresetName}`);
}
// 친구 수강신청 갖고오기
// const fetchFriendLectureList = async (studentID: string) => {
//     return fetchTimetable(`/api/lecture/lectureList/${studentID}`);
// };


// LectureApplication 에서 사용됨
// onClick 수강신청하기(enrollment)
/**
 * 매개변수로 받아온 강의리스트로 수강신청하는 axios 요청
 * @param selectedLectures 수강신청 할 강의 리스트
 */

const registrations = async (selectedLectures: Lecture[]) => {
    try {
        // selectedLectures를 서버로 보내는 HTTP POST 요청
        const response = await secInstance.post("/api/registrations", selectedLectures);
        if (response.data) {
            // 리덕스 availableLectures 최신화 코드
            // 최신 강의 목록 가져오기
            const registeredLecturesResponse = await fetchSelectedLectures();
            // 현재 수강신청 후 , 리덕스 최신화 하는 코드 짜기
            // registeredLecturesResponse.forEach((lecture) => dispatch(addSelectedLecture(lecture)));
            alert("수강신청이 완료되었습니다.");
            return response.data;
        } else {
            alert("수강신청에 실패하였습니다.");
            return { success: false };
        }
    } catch (error) {
        console.error("수강신청 중 오류가 발생했습니다:", error);
        return { success: false , error };
    }
};
export {
    registrations, fetchSelectedLectures,
    fetchAvailableLectures, fetchFriendLectureList, fetchTimetable, formatLecture, fetchLectureOfPreset
}