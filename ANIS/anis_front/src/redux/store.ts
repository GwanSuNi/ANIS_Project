import {configureStore} from '@reduxjs/toolkit';
import authSlice from './authSlice';
import lectureSlice from './lectureSlice';
import usernameSlice from './usernameSlice';
import qrInputSlice from './qrInputSlice';
import {lectureApi} from '@api';

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

export type RootState = ReturnType<typeof store.getState>;

export default store;