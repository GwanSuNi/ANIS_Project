import { useState, useEffect } from 'react';
import secInstance from "../utils/secInstance";

interface UserInfo {
    studentID: string;
    studentName: string;
    departmentName: string;
    birth: string;
}

export function useUserInfo() {
    const [userInfo, setUserInfo] = useState<UserInfo>();

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await secInstance.get('/api/member/myInfo');
                setUserInfo(response.data);
            } catch (error) {
                console.error('내 정보를 불러오기에 실패했습니다:', error);
            }
        };
        fetchUserInfo();
    }, []);

    return userInfo;
}