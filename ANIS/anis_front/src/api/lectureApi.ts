import {createApi} from '@reduxjs/toolkit/query/react';
import {secInstance} from '@utils';

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
    lecGrade: lecture.lecGrade
});

export const lectureApi = createApi({
    reducerPath: 'lectureApi',
    baseQuery: async ({url}) => {
        const response = await secInstance.get(url);

        return {data: response.data}
    },
    endpoints: (builder) => ({
        fetchAvailableLectures: builder.query<Lecture[], void>({
            query: () => ({url: '/api/lecture/availableLectureList'}),
            transformResponse: (response: Lecture[]): Lecture[] => response.map(formatLecture)
        }),
        fetchRegisteredLectures: builder.query<Lecture[], void>({
            query: () => ({url: '/api/lecture/lectureList'}),
            transformResponse: (response: Lecture[]): Lecture[] => response.map(formatLecture)
        })
    })
});

export const {useFetchAvailableLecturesQuery, useFetchRegisteredLecturesQuery} = lectureApi;