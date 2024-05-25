import {Outlet} from 'react-router-dom';
import {Box, Stack} from '@mui/material';
import Header from './Header';
import Footer from './Footer';

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