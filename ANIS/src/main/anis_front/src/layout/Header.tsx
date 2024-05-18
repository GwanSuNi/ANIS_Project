import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import LogoutComponent from '../components/LogoutComponent';

export default function Header() {
    return (
        <AppBar position='sticky' component='nav'>
            <Toolbar sx={{height: '70px'}}>
                <Avatar alt='' src=''/>
                <Typography variant='h6' color='primary.contrastText' ml={1}>
                    사용자 이름
                </Typography>
                <Typography variant='h5' color='primary.contrastText' m='auto'>
                    성인학습자 학사정보시스템
                </Typography>
                <LogoutComponent/>
            </Toolbar>
        </AppBar>
    );
}