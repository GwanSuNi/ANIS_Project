import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {Lecture} from '@types';

// LectureState 인터페이스 정의
interface LectureState {
    selectedLectures: Record<string, Lecture>;
}

// 초기 상태 정의
const initialState: LectureState = {
    selectedLectures: {}
};

const lectureSlice = createSlice({
    name: 'lecture',
    initialState,
    reducers: {
        // lecName을 키로 사용하여 selectedLectures 객체에 새로운 강의를 추가합니다.
        // 즉, selectedLectures 객체의 키가 lecName인 항목에 Lecture 객체를 할당합니다.
        addSelectedLecture: (state, action: PayloadAction<Lecture>) => {
            state.selectedLectures[action.payload.lecName] = action.payload;
        },
        removeSelectedLecture: (state, action: PayloadAction<Lecture>) => {
            delete state.selectedLectures[action.payload.lecName];
        },
        setSelectedLectures: (state, action: PayloadAction<Lecture[]>) => {
            state.selectedLectures = action.payload.reduce((acc, lecture) => {
                acc[lecture.lecName] = lecture;
                return acc;
            }, {} as Record<string, Lecture>);
        }
    }
});

export const {addSelectedLecture, removeSelectedLecture, setSelectedLectures} = lectureSlice.actions;

export default lectureSlice;
