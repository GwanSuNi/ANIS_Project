import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
    name: 'authSlice',
    initialState: { isLoggedIn: false },
    reducers: {
        loginSuccess: (state) => {
            state.isLoggedIn = true;
        },
        logoutSuccess: (state) => {
            state.isLoggedIn = false;
        },
    },
});

export const { loginSuccess, logoutSuccess } = authSlice.actions;

export default authSlice;