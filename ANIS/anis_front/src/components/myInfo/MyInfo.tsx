import CssBaseline from "@mui/material/CssBaseline";
import * as React from "react";
import Container from "@mui/material/Container";
import {Box, Skeleton, Table, TableBody, TableCell, TableContainer, TableRow} from "@mui/material";
import Grid from '@mui/material/Grid2'; // Grid version 2
import Typography from '@mui/material/Typography';
import Paper from "@mui/material/Paper";
import Card from "@mui/material/Card";
import CardMedia from '@mui/material/CardMedia';
import {useUserInfo} from "../../hooks/useUserInfo";
import useQrCode from "../../hooks/useQrCode";
import useUpdateProfileImage from "../../hooks/useUpdateProfileImage";
import ImageCard from "./ImageCard";
import QrCard from "./QrCard";

export interface UserInfo {
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
        <TableContainer sx={{minWidth: '250px', minHeight: '250px', maxWidth: '300px', maxHeight: '300px'}}
                        component={Paper}>
            <Table size="medium" aria-label="a dense table">
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
    const {mutate: updateProfileImage} = useUpdateProfileImage();

    const handleUploadImageClick = async (studentID:string) => {
        // 파일 선택 요소를 동적으로 생성합니다.
        const fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept = 'image/*'; // 이미지만 선택할 수 있도록 합니다.

        // 파일 선택 요청을 처리합니다.
        fileInput.addEventListener('change', (event) => {
            /// event.target이 null인지 확인합니다.
            if (fileInput.files === null) {
                return;
            }
            const file = fileInput.files[0];
            if (file) {
                updateProfileImage({ studentID, file });
            }
        });

        // 파일 선택 요소를 클릭하여 파일 선택 대화상자를 엽니다.
        fileInput.click();
    };

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
                <Grid container spacing={2} maxWidth={"xl"} direction={"column"} justifyContent="center"
                      alignItems="center">
                    <Grid container spacing={4} size={{xs: 12}} justifyContent="center" alignItems="center"
                          direction={{xs: 'column', sm: 'row'}}
                          width={'900px'}
                          columnSpacing={{xs: 2, sm: 4, md: 4}}
                          rowSpacing={{xs: 2, sm: 4, md: 4}}
                          sx={{p: 0, mt: 3}}>
                        <Grid size={{xs: 'auto'}} display={'flex'} justifyContent="center" alignItems="center">
                            <ImageCard userInfo={userInfo} handleUploadImageClick={handleUploadImageClick}/>
                        </Grid>
                        <Grid container size={{xs:6}} justifyContent="center" alignItems="center">
                            <MyInfoTable {...userInfo}/>
                        </Grid>
                    </Grid>
                    <Grid display={'flex'} size={{xs:12}} justifyContent="center" alignItems="center">
                      <QrCard studentID={userInfo.studentID}/>
                    </Grid>
                </Grid>
            </Box>
        </Container>


    )
        ;
};
