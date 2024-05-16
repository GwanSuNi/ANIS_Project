import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Avatar from "@mui/material/Avatar";
import LogoutComponent from "../components/LogoutComponent";
import {Outlet} from "react-router-dom";
import {useSelector} from "react-redux";
import {RootState} from "../redux/store";

export default function MainLayout() {
    const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn);
    console.log('isLoggedIn:', isLoggedIn);

    return (
        <>
            <AppBar position='sticky' component='nav' sx={{backgroundColor: '#ebd480'}}>
                <Toolbar sx={{height: '70px'}}>
                    <Avatar alt='' src=''/>
                    <Typography variant='h6' color='#444444' ml={1}>
                        사용자 이름
                    </Typography>
                    <Typography variant='h5' color='#444444' m='auto'>
                        성인학습자 학사정보시스템
                    </Typography>
                    <LogoutComponent/>
                </Toolbar>
            </AppBar>
            <Outlet/>
        </>
    );
}