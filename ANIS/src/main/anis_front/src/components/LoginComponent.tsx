import React, { useState } from 'react';
import loginInstance from "../utils/loginInstance";
import {useDispatch} from "react-redux";
import {loginSuccess} from "../redux/authSlice";
import {useLogin} from "../hooks/useLogin";

const LoginComponent = () => {
    // const { username, password, setUsername, setPassword, handleSubmit } = useLogin();
    // 아래 코드가 중복이 발생하기 때문에 위와 같이 커스텀 훅을 사용해서 중복을 제거
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
        } catch (error) {
            console.log(username);
            console.error(error);
        }
    };
    return (
        <form onSubmit={handleSubmit} encType={"multipart/form-data"}>
            <label>
                Username:
                <input type="text" value={username} onChange={e => setUsername(e.target.value)} />
            </label>
            <br/>
            <label>
                Password:
                <input type="password" value={password} onChange={e => setPassword(e.target.value)} />
            </label>
            <br/>
            <input type="submit" value="Submit" />
        </form>
    );
};

export default LoginComponent;