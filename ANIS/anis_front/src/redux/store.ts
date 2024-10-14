import {configureStore} from '@reduxjs/toolkit';
import authSlice from './authSlice';
import lectureSlice from './lectureSlice';
import usernameSlice from './usernameSlice';
import qrInputSlice from './qrInputSlice';
import {lectureApi} from '@api';
import snackbarSlice from "./snackbarSlice";

// rootReducer 정의
const rootReducer = (state: any, action: any) => {
    // RESET_STATE 액션을 처리하여 모든 상태를 초기화
    if (action.type === 'RESET_STATE')
        state = undefined;

    // 각 슬라이스 리듀서에 현재 상태와 액션을 전달하여 상태를 업데이트
    return {
        auth: authSlice.reducer(state?.auth, action),
        lecture: lectureSlice.reducer(state?.lecture, action),
        username: usernameSlice.reducer(state?.username, action),
        qrInput: qrInputSlice.reducer(state?.qrInput, action),
        [lectureApi.reducerPath]: lectureApi.reducer(state?.[lectureApi.reducerPath], action),
        snackbar: snackbarSlice.reducer(state?.snackbar, action),
    };
};

// Redux 스토어 설정
const store = configureStore({
    reducer: rootReducer,
    // 기본 미들웨어에 RTK Query 미들웨어 추가
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(lectureApi.middleware),
    devTools: process.env.NODE_ENV !== 'production' // 개발 환경에서 Redux DevTools 사용
});

// RootState 타입 정의
export type RootState = ReturnType<typeof store.getState>;
// typeof store.dispatch를 사용하여 디스패치 함수의 타입을 가져와 AppDispatch 타입으로 정의
// 컴포넌트에서 useDispatch 훅을 사용할 때 디스패치 함수의 타입을 명확하게 지정할 수 있음
export type AppDispatch = typeof store.dispatch;

export default store;
