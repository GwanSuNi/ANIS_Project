import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import {Lecture} from '@types';

interface LectureState {
    selectedLectures: Record<number, Lecture>;
}

const initialState: LectureState = {
    selectedLectures: {}
};

const lectureSlice = createSlice({
    name: 'lecture',
    initialState,
    reducers: {
        addSelectedLecture: (state, action: PayloadAction<Lecture>) => {
            state.selectedLectures[action.payload.lecID] = action.payload;
        },
        removeSelectedLecture: (state, action: PayloadAction<Lecture>) => {
            delete state.selectedLectures[action.payload.lecID];
        }
    }
});

export const {addSelectedLecture, removeSelectedLecture} = lectureSlice.actions;

export default lectureSlice;