import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import {LogoutComponent} from '@components';
import {useUserInfo} from "../hooks/useUserInfo";
import useProfileImage from "../hooks/useProfileImage";
import {Skeleton} from "@mui/material";

function AvatarImage({studentID}: { studentID: string }) {
    const {status, data, error, isFetching} = useProfileImage(studentID);

    if (status === 'pending') {
        return (
            <Skeleton variant="circular" width={40} height={40}/>
        )
    }
    return (<Avatar alt='증명사진' src={data}/>);
}

export default function Header() {
    const userInfo = useUserInfo();

    return (
        <AppBar position='sticky' component='nav'>
            <Toolbar sx={{height: '70px'}}>
                <AvatarImage studentID={userInfo?.studentID as string}/>
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