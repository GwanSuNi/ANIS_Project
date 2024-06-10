import React, {useEffect, useState} from 'react';
import Button from '@mui/material/Button';
import {StudentCheckList} from '@components';
import {fetchFriendList, Student} from '@hooks';
import {secInstance} from '@utils';
import {useNavigate} from "react-router-dom";
import {useFetchFriendList} from "../../hooks/MemberApi";
import Dialog from "@mui/material/Dialog";
import {Snackbar} from "@mui/material";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogActions from "@mui/material/DialogActions";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import ListItemText from "@mui/material/ListItemText";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";

/**
 * 수강신청 함께하기 컴포넌트
 */
export default function EnrolmentTogether() {
    const friendList = useFetchFriendList();
    const [checkedStudents, setCheckedStudents] = useState<Student[]>([]);
    const navigate = useNavigate();
    const [openDialog, setOpenDialog] = useState(false); // Dialog의 열림 상태를 제어하는 state
    const [openSnackbar, setOpenSnackbar] = useState(false); // Snackbar의 열림 상태를 제어하는 state

    const handleClickOpen = () => {
        if (checkedStudents.length > 0) {
            setOpenDialog(true);
        } else {
            setOpenSnackbar(true);
        }
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
    };

    const handleCloseSnackbar = () => {
        setOpenSnackbar(false);
    };

    const enrolmentTogetherEnd = () => {
        navigate('/lecture');
    };
    return (
        <>
            <StudentCheckList items={friendList} onCheckedItemsChange={(checkedStudents) => {
                setCheckedStudents(checkedStudents); // 체크된 학생들의 목록을 상태로 저장
            }}/>
            {/* TODO 에러처리하기*/}
            <Button onClick={handleClickOpen} variant="contained"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}>수강신청함께하기</Button>
            <Dialog
                open={openDialog}
                onClose={handleCloseDialog}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{"수강신청 확인"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        현재 체크된 학생들과 함께 수강신청을 진행하시겠습니까?
                    </DialogContentText>
                        <List sx={{overflow: 'auto', height: '450px'}}>
                            {checkedStudents.map((item, index) => (
                                <ListItem
                                    key={index}
                                    sx={{display: 'flex', justifyContent: 'center'}}
                                    disablePadding
                                >
                                        <ListItemAvatar>
                                            <Avatar
                                                // 사진넣는곳
                                                // src={`/static/images/avatar/${index + 1}.jpg`}
                                            />
                                        </ListItemAvatar>
                                        <Box display="flex" justifyContent="center" alignItems="center" flex={1}>  {/* 이 부분을 추가하세요. */}
                                            <ListItemText
                                                primary={
                                                    <>
                                                        <div>이름: {item.studentName} 학과: {item.departmentName}</div>
                                                        <div>학번: {item.studentID} 생년월일: {item.birth}</div>
                                                    </>
                                                }
                                            />
                                        </Box>
                                </ListItem>
                            ))}
                        </List>

                    <DialogActions sx={{display: 'flex', justifyContent: 'space-evenly', alignItems: 'center'}}>
                        <Grid sx={{
                            height: '100px',
                            width: '150px',
                            border: '2px solid #black',
                            borderRadius: '5px',
                            backgroundColor: '#ffff00',
                            marginBottom: 3.5
                        }}>
                            <Button variant='contained' onClick={async () => {
                                        try {
                                            const response = await secInstance.post('/api/registrations/friends', checkedStudents);
                                            alert("수강신청이 함께 되었습니다");
                                            enrolmentTogetherEnd();
                                        } catch (error) {
                                            console.error('Error during enrolment with friends:', error);
                                        }
                                    }} sx={{
                                display: 'flex',
                                justifyContent: 'flex',
                                alignItems: 'flex',
                                height: '100px',
                                width: '150px'
                            }}>
                                <Typography fontSize='30px' fontFamily='NanumGothicBold' color='primary.contrastText'
                                            m='auto'>
                                    예
                                </Typography>
                            </Button>
                        </Grid>
                        <Grid sx={{
                            height: '100px',
                            width: '150px',
                            border: '2px solid #black',
                            borderRadius: '5px',
                            marginBottom: 3.5
                        }}>
                            <Button variant='contained' onClick={handleCloseDialog} sx={{
                                display: 'flex',
                                justifyContent: 'flex',
                                alignItems: 'flex',
                                height: '100px',
                                width: '150px'
                            }}>
                                <Typography fontSize='30px' fontFamily='NanumGothicBold' color='primary.contrastText'
                                            m='auto'>
                                    아니요
                                </Typography>
                            </Button>
                        </Grid>
                    </DialogActions>
                </DialogContent>
            </Dialog>

            <Snackbar
                open={openSnackbar}
                autoHideDuration={6000}
                onClose={handleCloseSnackbar}
                message="체크된 학생이 없습니다."
                action={
                    <React.Fragment>
                        <Button color="secondary" size="small" onClick={handleCloseSnackbar}>
                            닫기
                        </Button>
                    </React.Fragment>
                }
            />
        </>
    );
}