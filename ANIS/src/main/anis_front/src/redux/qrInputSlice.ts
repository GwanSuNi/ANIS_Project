import { createSlice } from '@reduxjs/toolkit';

const qrInputSlice = createSlice({
    name: 'qrInputSlice',
    initialState: { qrInput: '' },
    reducers: {
        setQrInput: (state, action) => {
            state.qrInput = action.payload;
        },
    },
});

export const { setQrInput } = qrInputSlice.actions;

export default qrInputSlice;