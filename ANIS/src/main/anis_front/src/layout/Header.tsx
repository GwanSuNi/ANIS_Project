import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import {LogoutComponent} from '@components';
import {useEffect, useState} from "react";
import secInstance from "../utils/secInstance";
import {useUserInfo} from "../hooks/useUserInfo";

export default function Header() {
    const userInfo = useUserInfo();
    return (
        <AppBar position='sticky' component='nav'>
            <Toolbar sx={{height: '70px'}}>
                <Avatar alt='' src=''/>
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