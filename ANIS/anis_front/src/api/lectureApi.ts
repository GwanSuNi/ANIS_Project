import {createApi} from '@reduxjs/toolkit/query/react';
import {Lecture} from '@types';
import {formatLecture, secInstance} from '@utils';

export const lectureApi = createApi({
    reducerPath: 'lectureApi',
    baseQuery: async ({url, method = 'GET', body}) => {
        try {
            let response;

            if (method === 'GET')
                response = await secInstance.get(url);
            else if (method === 'POST')
                response = await secInstance.post(url, body);
            else
                return {error: `Unsupported method: ${method}`};

            return {data: response.data};
        } catch (error: unknown) {
            if (error instanceof Error)
                return {error: error.message};
            else
                return {error: 'An unknown error occurred'};
        }
    },
    endpoints: (builder) => ({
        // 현재 수강 가능한 모든 강의들 갖고오기
        fetchAvailableLectures: builder.query<Lecture[], void>({
            query: () => ({url: '/api/lecture/availableLectureList'}),
            transformResponse: (response: Lecture[]): Lecture[] => response.map(formatLecture),
            keepUnusedDataFor: 3600 // 1시간 동안 캐시에 유지
        }),
        // 현재 회원이 수강한 강의들 모두 갖고오기
        fetchRegisteredLectures: builder.query<Lecture[], void>({
            query: () => ({url: '/api/lecture/lectureList'}),
            transformResponse: (response: Lecture[]): Lecture[] => response.map(formatLecture),
            keepUnusedDataFor: 3600
        }),
        // 수강신청하기
        registerLectures: builder.mutation({
            query: (selectedLectures) => ({
                url: '/api/registrations',
                method: 'POST',
                body: selectedLectures,
            }),
            // 변이 요청이 시작될 때 호출되는 함수
            onQueryStarted: async (selectedLectures, {dispatch, queryFulfilled}) => {
                try {
                    // 변이 요청이 완료될 때까지 기다림
                    const {data} = await queryFulfilled;

                    // 변이 요청이 성공했으면 캐시 데이터를 업데이트
                    if (data)
                        dispatch(lectureApi.util.updateQueryData('fetchRegisteredLectures', undefined, () => [...selectedLectures]));
                } catch (error) {
                    console.error('Failed to update cache:', error);
                }
            }
        })
    })
});

export const {
    useFetchAvailableLecturesQuery,
    useFetchRegisteredLecturesQuery,
    useRegisterLecturesMutation
} = lectureApi;
