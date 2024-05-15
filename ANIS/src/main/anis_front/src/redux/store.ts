import { configureStore } from '@reduxjs/toolkit';
import authSlice from "./authSlice";
import usernameSlice from "./usernameSlice";
import qrInputSlice from "./qrInputSlice";

const store = configureStore({
    reducer: {
        auth: authSlice.reducer,
        username: usernameSlice.reducer,
        qrInput: qrInputSlice.reducer,
    },
});
export type RootState = ReturnType<typeof store.getState>;
export default store;