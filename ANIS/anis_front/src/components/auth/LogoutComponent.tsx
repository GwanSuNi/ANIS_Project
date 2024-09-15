import React, {MouseEvent, useState} from 'react';
import {isLoggedIn, logoutInstance} from '@utils';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';
import {Alert, Button, Snackbar} from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import Typography from '@mui/material/Typography';
import CheckIcon from '@mui/icons-material/Check';

export default function LogoutComponent() {
    const dispatch = useDispatch();
    // const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn);
    const navigate = useNavigate();
    const [logoutSuccess, setLogoutSuccess] = useState(false);

    const handleLogout = async (event: MouseEvent) => {
        event.preventDefault();

        try {
            const response = await logoutInstance.post('/api/logout');
            if (response.status === 200) {
                sessionStorage.removeItem('access');
                setLogoutSuccess(true);
                setTimeout(() => {
                    dispatch({type: 'RESET_STATE'}); // 모든 리덕스 상태 초기화(RTK Query 캐시도 포함)
                    navigate('/login');
                }, 3000);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleClose = (event: React.SyntheticEvent | Event, reason?: 'timeout' | 'clickaway' | 'escapeKeyDown') => {
        if (reason === 'clickaway') {
            return;
        }
        setLogoutSuccess(false);
    };

    return (
        <>
            <Snackbar
                anchorOrigin={{vertical: 'top', horizontal: 'right'}}
                open={logoutSuccess}
                autoHideDuration={5000}
                onClose={handleClose}
                message="로그아웃 되었습니다."
            >
                <Alert icon={<CheckIcon fontSize="inherit"/>} severity="success">
                    <Typography variant='h5'>
                        로그아웃 되었습니다.
                    </Typography>
                </Alert>
            </Snackbar>
            <Button
                variant='contained'
                disableElevation
                onClick={handleLogout}
                endIcon={<LogoutIcon sx={{color: '#444444'}}/>}
                sx={{visibility: isLoggedIn() ? 'visible' : 'hidden'}}
            >
                <Typography variant='h6' color='primary.contrastText'>
                    로그아웃
                </Typography>
            </Button>
        </>
    );
}