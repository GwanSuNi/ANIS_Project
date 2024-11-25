import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface SnackbarState {
    open: boolean;
    message: string;
    severity: 'success' | 'error';
}

const initialState: SnackbarState = {
    open: false,
    message: '',
    severity: 'success',
};

const snackbarSlice = createSlice({
    name: 'snackbar',
    initialState,
    reducers: {
        openSnackbar: (state, action: PayloadAction<{ message: string; severity: 'success' | 'error' }>) => {
            state.open = true;
            state.message = action.payload.message;
            state.severity = action.payload.severity;
        },
        closeSnackbar: (state) => {
            state.open = false;
            state.message = '';
            state.severity = 'success';
        },
    },
});

export const { openSnackbar, closeSnackbar } = snackbarSlice.actions;
export default snackbarSlice;