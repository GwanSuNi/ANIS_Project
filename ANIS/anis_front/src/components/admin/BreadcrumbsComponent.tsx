import React from 'react';
import Breadcrumbs from '@mui/material/Breadcrumbs';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import HomeIcon from '@mui/icons-material/Home';

interface BreadcrumbsProps {
    pathname: string;
    setPathname: (path: string) => void;
}

export default function BreadcrumbsComponent({ pathname, setPathname }: BreadcrumbsProps) {
    // '/'를 기준으로 경로를 분리
    const segments = pathname.split('/').filter(Boolean);

    const handleBreadcrumbClick = (segment: string, index: number) => {
        // 클릭한 경로까지의 경로를 조합하여 새로운 경로 설정
        const newPath = '/' + segments.slice(0, index + 1).join('/');
        setPathname(newPath);
    };

    return (
        <Breadcrumbs aria-label="breadcrumb">
            <Link underline="hover" color="inherit"  sx={{ display: 'flex', alignItems: 'center' }} style={{ cursor: 'pointer' }} onClick={() => setPathname('/대시보드')}>
                <HomeIcon sx={{ mr: 0.5 }} fontSize="inherit" />
                Home
            </Link>
            {segments.map((segment, index) => (
                index < segments.length - 1 ? (
                    // 마지막 경로가 아닌 경우, Link로 표시하려고 했으나, 리스트 주체를 눌렀을 때 보여질 화면은 없어서 그냥 Typography로 표시
                    // <Link
                    //     key={index}
                    //     underline="hover"
                    //     color="inherit"
                    //     style={{ cursor: 'pointer' }}
                    //     onClick={() => handleBreadcrumbClick(segment, index)}
                    // >
                    //     {segment}
                    // </Link>
                    <Typography
                        key={index}
                        color="inherit"
                    >
                        {segment}
                    </Typography>
                ) : (
                    // 현재 경로는 Typography로 표시
                    <Typography key={index} color="text.primary">
                        {segment}
                    </Typography>
                )
            ))}
        </Breadcrumbs>
    );
}