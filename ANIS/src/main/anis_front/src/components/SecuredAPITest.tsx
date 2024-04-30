import React, { useState } from 'react';
import axios from 'axios';

const SecuredAPITest = () => {
    const [res, setRes]= useState("");
    const handleClick = async (event: React.MouseEvent) => {
        event.preventDefault();

        // sessionStorage에서 access 토큰을 가져옵니다.
        const token = sessionStorage.getItem('access');

        const axiosInstance = axios.create({
            baseURL: 'http://localhost:8080',
            withCredentials: true, // 쿠키를 전송받기 위해 필요
            headers: {
                'access': `${token}` // 토큰을 access 헤더에 포함
            }
        });

        try {
            const response = await axiosInstance.get('/admin');
            if (response.status === 200) {
                console.log('Response:', response.data); // 응답 본문 출력
                setRes(response.data);
            }
        } catch (error) {
            setRes("어드민이 아닙니다.")
            console.error('Error:', error); // 오류 메시지 출력
        }
    };

    return ( <>
        <button onClick={handleClick}>어드민 체크</button>
            <p>{res}</p>
        </>
    );
};

export default SecuredAPITest;