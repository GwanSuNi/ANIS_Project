import { useState, useEffect } from 'react';
import secInstance from '../utils/secInstance';

const useProfileImage = (studentID: string | undefined) => {
    const [profileImage, setProfileImage] = useState<string>();

    useEffect(() => {
        const fetchProfileImage = async () => {
            try {
                const imageResponse = await secInstance.get(`/api/image/download/${studentID}`, {responseType: 'arraybuffer'});
                const base64 = btoa(
                    new Uint8Array(imageResponse.data).reduce(
                        (data, byte) => data + String.fromCharCode(byte),
                        '',
                    ),
                );
                setProfileImage(`data:image/png;base64,${base64}`);
            } catch (error) {
                console.error('프로필 이미지를 불러오기에 실패했습니다:', error);
            }
        };
        fetchProfileImage();
    }, [studentID]); // 학번이 변경될 때만 프로필 이미지를 다시 요청합니다.

    return profileImage;
};

export default useProfileImage;