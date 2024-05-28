import CssBaseline from "@mui/material/CssBaseline";
import * as React from "react";
import Container from "@mui/material/Container";
import {Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import Grid from '@mui/material/Unstable_Grid2'; // Grid version 2
import Typography from '@mui/material/Typography';
import {useEffect, useState} from "react";
import secInstance from "../utils/secInstance";
import Paper from "@mui/material/Paper";
import Card from "@mui/material/Card";
import CardMedia from '@mui/material/CardMedia';
import {useUserInfo} from "../hooks/useUserInfo";
import useProfileImage from "../hooks/useProfileImage";
import useQrCode from "../hooks/useQrCode";

interface UserInfo {
    studentID: string;
    studentName: string;
    departmentName: string;
    birth: string;
}

function MyInfoTable(userInfo: UserInfo) {
    const {studentID, studentName, departmentName, birth} = userInfo;
    const data = [
        {label: '학번', value: studentID},
        {label: '이름', value: studentName},
        {label: '학과', value: departmentName},
        {label: '생년월일', value: birth},
    ];
    return (
        <TableContainer sx={{minWidth: '250px', minHeight:'250px', maxWidth:'300px', maxHeight:'300px'}} component={Paper}>
            <Table  size="medium" aria-label="a dense table">
                <TableBody>
                    {data.map((row) => (
                        <TableRow key={row.label}>
                            <TableCell component="th" scope="row">
                                <Typography variant='h6'>
                                {row.label}
                                </Typography>
                            </TableCell>
                            <TableCell align="right"><Typography variant='h6'>{row.value}</Typography></TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

export default function MyInfo() {
    const userInfo = useUserInfo();
    const qrCode = useQrCode();
    const profileImage = useProfileImage(userInfo?.studentID);

    if (!userInfo) {
        return <div>로딩중 입니다...</div>;
    }

    return (
        <Container component="main" maxWidth={"lg"}>
            <CssBaseline/>
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    width: '100%',
                }}
            >
                <Grid container spacing={2} maxWidth={"xl"} direction={"column"} justifyContent="center" alignItems="center" >
                    <Grid container spacing={4} xs={12} justifyContent="center" alignItems="center"  direction={{ xs: 'column', sm: 'row' }}
                          width={'900px'}
                          columnSpacing={{ xs: 2, sm: 4, md: 4 }}
                          rowSpacing={{ xs: 2, sm: 4, md: 4 }}
                          sx={{p:0, mt:3}}>
                        <Grid xs={'auto'} display={'flex'} justifyContent="center" alignItems="center">
                            {/*    이미지 박스*/}
                            {/*    TODO: DB에서 본인 증명사진 받아오기 */}
                            <Card
                                style={{width:'203px', height:'260px'}}>
                                <CardMedia
                                    component="img"
                                    image={profileImage}
                                    alt="profile"
                                />
                            </Card>
                        </Grid>
                        <Grid container xs={6} justifyContent="center" alignItems="center">
                            <MyInfoTable {...userInfo}/>
                        </Grid>
                    </Grid>
                    <Grid display={'flex'} xs={12} justifyContent="center" alignItems="center">
                        <Card style={{minWidth: '100px', minHeight: '100px', maxWidth:'300px', maxHeight:'300px', marginTop:10}}>
                            <CardMedia
                                component="img"
                                src={qrCode}
                                alt="qrCode"
                            />
                        </Card>
                    </Grid>
                </Grid>
            </Box>
        </Container>


    );
};
