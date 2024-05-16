import React from "react";
import logoutInstance from "../utils/logoutInstance";
import {useDispatch} from "react-redux";
import {logoutSuccess} from "../redux/authSlice";
import {useNavigate} from "react-router-dom";
import {Button} from "@mui/material";
import LogoutIcon from '@mui/icons-material/Logout';
import {setUsername} from "../redux/usernameSlice";
import {setQrInput} from "../redux/qrInputSlice";
import Typography from "@mui/material/Typography";
import {isLoggedIn} from "../utils";

export default function LogoutComponent() {
    const dispatch = useDispatch();
    // const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn);
    const navigate = useNavigate();

    const handleLogout = async (event: React.MouseEvent) => {
        event.preventDefault();

        try {
            const response = await logoutInstance.post('/logout');
            if (response.status === 200) {
                sessionStorage.removeItem('access');
                dispatch(setUsername(''));
                dispatch(setQrInput(''));
                dispatch(logoutSuccess());
                alert('로그아웃 되었습니다.');
                navigate('/login');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };
    return (
        <Button
            variant='text'
            onClick={handleLogout}
            endIcon={<LogoutIcon sx={{color: '#444444'}}/>}
            sx={{visibility: isLoggedIn() ? 'visible' : 'hidden'}}
        >
            <Typography variant='h6' color='#444444'>
                로그아웃
            </Typography>
        </Button>
    );
}