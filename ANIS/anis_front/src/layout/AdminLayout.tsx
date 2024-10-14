import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import {createTheme} from '@mui/material/styles';
import DashboardIcon from '@mui/icons-material/Dashboard';
import AssignmentIcon from '@mui/icons-material/Assignment';
import NoteAltIcon from '@mui/icons-material/NoteAlt';
import ClassIcon from '@mui/icons-material/Class';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import SchoolIcon from '@mui/icons-material/School';
import VideoSettingsIcon from '@mui/icons-material/VideoSettings';
import RoomPreferencesIcon from '@mui/icons-material/RoomPreferences';
import AssignmentTurnedInIcon from '@mui/icons-material/AssignmentTurnedIn';
import {AppProvider, Session} from '@toolpad/core/AppProvider';
import {DashboardLayout} from '@toolpad/core/DashboardLayout';
import type {Router, Navigation} from '@toolpad/core';
import {useEffect, useState} from "react";
import { Fab} from "@mui/material";
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import {useNavigate} from "react-router-dom";
import {jwtDecode} from "jwt-decode";
import {logoutInstance} from "@utils";
import BreadcrumbsComponent from "../components/admin/BreadcrumbsComponent";
import StudentManagement from "../components/admin/studentInfo/StudentManagement";
import LectureManagement from "../components/admin/lecture/LecureManagement";

const NAVIGATION: Navigation = [
    {
        segment: '대시보드',
        title: '대시보드',
        icon: <DashboardIcon/>,
    },
    {
        kind: 'header',
        title: '학적 관리',
    },
    {
        segment: '개인신상조회',
        title: '개인신상조회',
        icon: <ManageAccountsIcon/>,
    },
    {
        kind: 'divider',
    },
    {
        kind: 'header',
        title: '수업 관리',
    },
    {
        segment: '수강 관리',
        title: '수강 관리',
        icon: <SchoolIcon/>,
        children: [
            {
                // 년도 학기 설정해서 수업 설정하는 페이지
                segment: '강의 등록',
                title: '강의 등록',
                icon: <ClassIcon/>,
            },
            {
                // 등록된 강의를 프리셋으로 만드는 페이지
                segment: '강의 프리셋 설정',
                title: '강의 프리셋 설정',
                icon: <VideoSettingsIcon/>,
            },
            {
                // 수강 신청 현황, 수강 신청 가능 일자 설정, 미완료 학생 선택해서 푸시 알림
                segment: '수강 신청 관리',
                title: '수강 신청 관리',
                icon: <RoomPreferencesIcon/>,
            }
        ]
    },
    {
        kind:'divider',
    },
    {
        kind: 'header',
        title: '설문조사 관리',
    },
    {
        segment: '설문조사',
        title: '설문조사',
        icon: <AssignmentIcon/>,
        children: [
            {
                segment: '설문 관리',
                title: '설문 관리',
                icon: <NoteAltIcon/>,
            },
            {
                // 설문별 해당되는 학생 조회, 미완료 학생 선택해서 푸시 알림
                segment: '설문 현황',
                title: '설문 현황',
                icon: <AssignmentTurnedInIcon/>,
            },
        ],
    },
];

function DemoPageContent({pathname}: { pathname: string }) {
    if (pathname === '/대시보드') {
        return <Typography>대시보드 내용</Typography>;
    }

    if (pathname === '/개인신상조회') {
        return <StudentManagement/>;
    }

    if (pathname === '/수강 관리/강의 등록') {
        return <LectureManagement/>;
    }

    if (pathname === '/수강 관리/강의 프리셋 설정') {
        return <Typography>강의 프리셋 설정 페이지 내용</Typography>;
    }

    if (pathname === '/수강 관리/수강 신청 관리') {
        return <Typography>수강 신청 관리 페이지 내용</Typography>;
    }

    if (pathname === '/설문조사/설문 관리') {
        return <Typography>설문 관리 페이지 내용</Typography>;
    }

    if (pathname === '/설문조사/설문 현황') {
        return <Typography>설문 현황 페이지 내용</Typography>;
    }
    return (
        <Box
            sx={{
                py: 4,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                textAlign: 'center',
            }}
        >
            <Typography>Dashboard content for {pathname}</Typography>
        </Box>
    );
}

interface User {
    name: string;
}


export default function AdminLayout() {
    // 모드 상태 관리
    const [mode, setMode] = useState<'light' | 'dark'>('light');
    const [session, setSession] = React.useState<Session | null>();
    const navigate = useNavigate();

    useEffect(() => {
        const token = sessionStorage.getItem('access');
        if (token) {
            try {
                const decoded: any = jwtDecode(token); // Decode JWT token

                const user: User = {
                    name: decoded.username || 'Unknown User',
                };
                setSession({ user });
            } catch (error) {
                console.error('Token decoding failed:', error);
                setSession(null); // Handle token decoding error
            }
        } else {
            setSession(null); // No token found, handle accordingly
        }
    }, []);


    // 테마 설정
    const theme = createTheme({
        palette: {
            mode: mode,  // light 또는 dark
        },
    });

// 모드 변경 핸들러
    const toggleDarkMode = () => {
        setMode(prevMode => (prevMode === 'light' ? 'dark' : 'light'));
    };

    const [pathname, setPathname] = React.useState(decodeURIComponent('/대시보드'));

    const router = React.useMemo<Router>(() => {
        return {
            pathname,
            searchParams: new URLSearchParams(),
            navigate: (path) => setPathname(decodeURIComponent(String(path))), // BreadCrumbsComponent에서 한글로 보여줘야하는데, 한글로 보여주면서 path를 인식 못해서 decodeURIComponent로 한글을 인식하게 함
        };
    }, [pathname]);



    const authentication = {
        signIn: () => {
            // Handle sign-in logic
        },
        signOut: async () => {
            try {
                const response = await logoutInstance.post('/api/logout');
                if (response.status === 200) {
                    sessionStorage.removeItem('access');
                    setTimeout(() => {
                        navigate('/login');
                    });
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }
    };

    return (
        // preview-start
        <AppProvider
            navigation={NAVIGATION}
            branding={{
                logo: <img src="https://imgur.com/rkFuhNq.png" alt="ANIS logo"/>,
                title: 'ANIS Admin',
            }}
            router={router}
            theme={theme}
            session={session}
            authentication={authentication}
        >
            <DashboardLayout>
                <Box sx={{ p: 2 }}>
                    <BreadcrumbsComponent pathname={pathname} setPathname={setPathname} />
                </Box>
                <DemoPageContent pathname={pathname}/>
                <Fab color="primary" aria-label="add" onClick={toggleDarkMode} size={"small"}
                     style={{
                         position: 'fixed',
                         bottom: 12,
                         left: 12,
                         zIndex: 100000 // 가장 위에 보이게 함
                     }}>
                    {mode === 'dark' ? <Brightness7Icon/> : <Brightness4Icon/>}
                </Fab>
            </DashboardLayout>
        </AppProvider>
        // preview-end
    );
}
