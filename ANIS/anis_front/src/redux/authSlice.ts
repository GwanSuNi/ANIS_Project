import {createSlice} from '@reduxjs/toolkit';

// utils/authSlice.ts의 isLoggedIn() 함수로 대처
// 페이지 새로고침하면 리덕스 값이 초기화가 되서 로그인 상태가 유지가 안됨
const authSlice = createSlice({
    name: 'authSlice',
    initialState: {isLoggedIn: false},
    reducers: {
        loginSuccess: (state) => {
            state.isLoggedIn = true;
        },
        logoutSuccess: (state) => {
            state.isLoggedIn = false;
        },
    },
});

export const {loginSuccess, logoutSuccess} = authSlice.actions;

export default authSlice;