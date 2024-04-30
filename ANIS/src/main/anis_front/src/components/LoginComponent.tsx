import React, { useState } from 'react';
import axios from 'axios';

const LoginComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        const axiosInstance = axios.create({
            baseURL: 'http://localhost:8080',
            withCredentials: true // 쿠키를 전송받기 위해 필요
        });

        try {
            const response = await axiosInstance.post('/login', formData);
            if (response.status === 200) {
                let accessToken = response.headers["access"].trim(); // 헤더에서 access 토큰 가져오기
                console.log('Access Token:', accessToken);
                // Access 토큰을 메모리에 저장
                sessionStorage.setItem('access', accessToken);
                // 메모리에 있는 ACCESS 토큰 출력
                console.log('sessionStorage:', sessionStorage.getItem('access'));
                // Refresh 토큰은 서버에서 HTTP Only 쿠키로 설정되어 클라이언트에서 직접 접근할 수 없습니다.
                alert('로그인 성공');
            }
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