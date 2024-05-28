import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import {LogoutComponent} from '@components';
import {useUserInfo} from "../hooks/useUserInfo";
import {useEffect, useState} from "react";
import secInstance from "../utils/secInstance";

export default function Header() {
    const userInfo = useUserInfo();
    const [profileImage, setProfileImage] = useState<string>();

    useEffect(() => {
        const fetchProfileImage = async () => {
            try {
                const imageResponse = await secInstance.get(`/api/image/download/${userInfo?.studentID}`, {responseType: 'arraybuffer'});
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
        if (userInfo) {
            fetchProfileImage();
        }
    }, [userInfo]); // 사용자 정보가 변경될 때만 프로필 이미지를 다시 요청합니다.

    return (
        <AppBar position='sticky' component='nav'>
            <Toolbar sx={{height: '70px'}}>
                <Avatar alt='증명사진' src={profileImage}/>
                <Typography variant='h6' color='primary.contrastText' ml={1}>
                    {userInfo?.studentName}({userInfo?.studentID})
                </Typography>
                <Typography variant='h5' fontFamily='NanumGothicBold' color='primary.contrastText' m='auto'>
                    성인학습자 학사정보시스템
                </Typography>
                <LogoutComponent/>
            </Toolbar>
        </AppBar>
    );
}