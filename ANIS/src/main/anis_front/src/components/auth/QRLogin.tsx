import {MouseEvent, useEffect, useRef} from 'react';
import {Box, Button, Link, Typography} from '@mui/material';
import {useLogin, useSerialPort} from '@hooks';
import {useNavigate} from 'react-router-dom';
import {useDispatch, useSelector} from 'react-redux';
import {RootState, setUsername} from '@redux';

export default function QRLogin() {
    const navigate = useNavigate();
    const {selectPort} = useSerialPort();
    const {password, setPassword, handleSubmit} = useLogin();
    const formRef = useRef<HTMLFormElement>(null);
    const username = useSelector((state: RootState) => state.username.username);
    const dispatch = useDispatch();
    const qrInput = useSelector((state: RootState) => state.qrInput.qrInput);

    useEffect(() => {
        dispatch(setUsername(qrInput));
        if (username !== '') {
            if (formRef.current) {
                const event = new Event('submit', {bubbles: true, cancelable: true});
                formRef.current.dispatchEvent(event);
                console.log("{username: " + username + ", password: " + password + "}");
            }
        }
    }, [qrInput, username]);

    const handleSelectLogin = (event: MouseEvent) => {
        event.preventDefault();
        navigate('/login/select');
    }

    const handleSelfLogin = (event: MouseEvent) => {
        event.preventDefault();
        navigate('/login/self');
    }

    return (
        <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
            <Box height={400}
                 width={400}
                 my={4}
                 display='flex'
                 alignItems='center'
                 gap={4}
                 p={2}
                 sx={{border: '2px solid grey'}}>
                {/*<QrReader/>*/}
                <Button variant='outlined' onTouchStart={selectPort} onClick={selectPort}>바코드 리더기 연결</Button>
            </Box>
            <form ref={formRef} onSubmit={handleSubmit} encType={'multipart/form-data'}>
                <input hidden type='text' value={username}
                       onChange={e => dispatch({type: 'username/setUsername', payload: e.target.value})}/>
                <input hidden type='password' value={password} onChange={e => setPassword(e.target.value)}/>
                <input hidden type='submit' value='Submit'/>
            </form>
            <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center'}}>
                <Typography variant='h3' component='h2'>
                    모바일 학생증 QR 코드
                </Typography>
                <br/>
                <Typography variant='h3' component='h2'>
                    또는
                </Typography>
                <br/>
                <Typography variant='h3' component='h2'>
                    학생증 바코드를 스캔해주세요.
                </Typography>
            </Box>
            <Button variant='contained' onClick={handleSelectLogin}>학적 검색해서 로그인</Button>

            <Link onClick={handleSelfLogin} variant='body2' sx={{color: 'gray', mt: '5px'}}>
                {'학교 관계자이신가요?'}
            </Link>
        </Box>
    );
}