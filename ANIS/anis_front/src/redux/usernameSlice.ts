import {createSlice} from '@reduxjs/toolkit';

const userNameSlice = createSlice({
    name: 'userNameSlice',
    initialState: {username: ''},
    reducers: {
        setUsername: (state, action) => {
            state.username = action.payload;
        },
    },
});

export const {setUsername} = userNameSlice.actions;

export default userNameSlice;