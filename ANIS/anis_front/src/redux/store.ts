import {configureStore} from '@reduxjs/toolkit';
import authSlice from './authSlice';
import lectureSlice from './lectureSlice';
import usernameSlice from './usernameSlice';
import qrInputSlice from './qrInputSlice';
import {lectureApi} from '@api';

// Redux 스토어 설정
const store = configureStore({
    reducer: {
        auth: authSlice.reducer,
        lecture: lectureSlice.reducer,
        username: usernameSlice.reducer,
        qrInput: qrInputSlice.reducer,
        [lectureApi.reducerPath]: lectureApi.reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(lectureApi.middleware),
    devTools: process.env.NODE_ENV !== 'production'
});

// RootState 타입 정의
export type RootState = ReturnType<typeof store.getState>;
// typeof store.dispatch를 사용하여 디스패치 함수의 타입을 가져와 AppDispatch 타입으로 정의
// 컴포넌트에서 useDispatch 훅을 사용할 때 디스패치 함수의 타입을 명확하게 지정할 수 있음
export type AppDispatch = typeof store.dispatch;

export default store;
