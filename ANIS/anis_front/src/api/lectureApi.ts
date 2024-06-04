import {createApi} from '@reduxjs/toolkit/query/react';
import {Lecture} from '@types';
import {secInstance} from '@utils';

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