import {Outlet} from 'react-router-dom';
import {Box, Stack} from '@mui/material';
import Header from './Header';
import Footer from './Footer';
// TODO 각각의 컴포넌트들 화면에 알맞게 적용시키기
export default function MainLayout() {
    return (
        <Stack height='100vh'>
            <Header/>
            <Box component="main" flex="1">
                <Outlet/>
            </Box>
            <Footer/>
        </Stack>
    );
}