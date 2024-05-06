import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import loginInstance from '../utils/loginInstance';
import { loginSuccess } from '../redux/authSlice';

export function useLogin() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        try {
            await loginInstance.post('/login', formData);
            dispatch(loginSuccess());
            // TODO: Redirect to the main page
        } catch (error) {
            console.log(username);
            console.error(error);
        }
    };

    return { username, password, setUsername, setPassword, handleSubmit };
}