import {MouseEvent, useState} from 'react';
import {secInstance} from "utils";

const SecuredAPITest = () => {
    const [res, setRes]= useState("");
    const handleClick = async (event: MouseEvent) => {
        event.preventDefault();

        try {
            const response = await secInstance.get('/admin');
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