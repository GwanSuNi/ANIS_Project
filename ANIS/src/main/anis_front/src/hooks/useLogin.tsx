import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {loginInstance} from '@utils';
import {loginSuccess} from '@redux/authSlice';
import {useNavigate} from "react-router-dom";
import {RootState} from "@redux/store";

export function useLogin() {
    const username = useSelector((state:RootState) => state.username.username);
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        // console.log('useLogin의 ', username);
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        try {
            await loginInstance.post('/login', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            dispatch(loginSuccess());
            navigate('/');
        } catch (error) {
            console.log(username);
            console.error(error);
        }
    };

    return { password, setPassword, handleSubmit };
}