import {
    Alert,
    Box,
    Button,
    Card,
    CardActions,
    FormControl,
    InputLabel,
    Snackbar,
    Select,
    Breadcrumbs
} from "@mui/material";
import Typography from "@mui/material/Typography";
import React, {useEffect, useState} from "react";
import MenuItem from "@mui/material/MenuItem";
import CardContent from "@mui/material/CardContent";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid2";
import secInstance from "../../../utils/secInstance";
import {TimeTable} from "@components";
import {useFetchLectures} from "@hooks"
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

function LectureRegisterCard() {
    const [lecName, setLecName] = useState('');
    const [lecGrade, setLecGrade] = useState('');
    const [lecProfessor, setLecProfessor] = useState('');
    const [lectureRoom, setLectureRoom] = useState('');
    const [lecDay, setLecDay] = useState('');
    const [lecTimeStart, setLecTimeStart] = useState('');
    const [lecTimeEnd, setLecTimeEnd] = useState('');
    const [lecCredit, setLecCredit] = useState('');
    // Snackbar 상태 관리
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success');

    // 강의 등록 핸들러
    const handleLecSubmit = async (event: React.FormEvent<HTMLButtonElement>) => {
        event.preventDefault();

        // 보낼 데이터 객체 생성
        const lectureData = {
            lecName,
            lecGrade: parseInt(lecGrade),
            lecProfessor,
            lectureRoom,
            lecDay,
            lecTimeStart,
            lecTimeEnd,
            lecCredit: parseInt(lecCredit),
        };

        try {
            const response = await secInstance.post('/api/lectureAdmin/lecture', lectureData, {
                headers: {
                    'Content-Type': 'application/json'  // JSON 타입 명시
                }
            });
            if (response.status === 200) {
                setSnackbarMessage('강의 등록 성공 [' + lecName + ']');
                setSnackbarSeverity('success');
                // 입력창 초기화
                setLecName('');
                setLecGrade('');
                setLecProfessor('');
                setLectureRoom('');
                setLecDay('');
                setLecTimeStart('');
                setLecTimeEnd('');
                setLecCredit('');
            }
        } catch (error) {
            setSnackbarMessage('강의 등록 실패');
            setSnackbarSeverity('error');
            console.error('강의 등록 중 에러 발생:', error);
        } finally {
            setSnackbarOpen(true);
        }
    };
    const handleCloseSnackbar = () => {
        setSnackbarOpen(false);
    };

    return (
        <React.Fragment>
            <CardContent>
                <Typography gutterBottom sx={{color: 'text.secondary', fontSize: 14}}>
                    등록할 강의 정보
                </Typography>

                <Grid container spacing={3}>
                    <Grid size={8}>
                        <TextField id="lecName"
                                   label="강의 이름"
                                   variant="standard"
                                   fullWidth
                                   value={lecName}
                                   onChange={(e) => setLecName(e.target.value)}/>
                    </Grid>
                    <Grid size={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 100, margin: 0}}>
                            <InputLabel>대상 학년</InputLabel>
                            <Select
                                value={lecGrade}
                                onChange={(e) => setLecGrade(e.target.value)}
                            >
                                <MenuItem value={"1"}>1</MenuItem>
                                <MenuItem value={"2"}>2</MenuItem>
                                <MenuItem value={"3"}>3</MenuItem>
                                <MenuItem value={"4"}>4</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>

                    <Grid size={4}>
                        <TextField id="lectureProfessor" label="담당 교수" variant="standard"
                                   value={lecProfessor} onChange={(e) => setLecProfessor(e.target.value)}/>
                    </Grid>
                    <Grid size={4}>
                        <TextField id="lectureRoom" label="강의실" variant="standard"
                                   value={lectureRoom} onChange={(e) => setLectureRoom(e.target.value)}/>
                    </Grid>
                    <Grid size={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 100, margin: 0}}>
                            <InputLabel id="demo-simple-select-filled-label">강의 요일</InputLabel>
                            <Select
                                labelId="demo-simple-select-filled-label"
                                id="demo-simple-select-filled"
                                value={lecDay}
                                onChange={(e) => setLecDay(e.target.value)}
                            >
                                <MenuItem value={"월요일"}>월</MenuItem>
                                <MenuItem value={"화요일"}>화</MenuItem>
                                <MenuItem value={"수요일"}>수</MenuItem>
                                <MenuItem value={"목요일"}>목</MenuItem>
                                <MenuItem value={"금요일"}>금</MenuItem>
                                <MenuItem value={"토요일"}>토</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid size={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 100, margin: 0}}>
                            <InputLabel id="demo-simple-select-filled-label">시작 시간</InputLabel>
                            <Select
                                labelId="demo-simple-select-filled-label"
                                id="demo-simple-select-filled"
                                value={lecTimeStart}
                                onChange={(e) => setLecTimeStart(e.target.value)}
                            >
                                <MenuItem value={"09:00:00"}>9시</MenuItem>
                                <MenuItem value={"10:00:00"}>10시</MenuItem>
                                <MenuItem value={"11:00:00"}>11시</MenuItem>
                                <MenuItem value={"12:00:00"}>12시</MenuItem>
                                <MenuItem value={"13:00:00"}>13시</MenuItem>
                                <MenuItem value={"14:00:00"}>14시</MenuItem>
                                <MenuItem value={"15:00:00"}>15시</MenuItem>
                                <MenuItem value={"16:00:00"}>16시</MenuItem>
                                <MenuItem value={"17:00:00"}>17시</MenuItem>
                                <MenuItem value={"18:00:00"}>18시</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid size={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 100, margin: 0}}>
                            <InputLabel id="demo-simple-select-filled-label">종료 시간</InputLabel>
                            <Select
                                labelId="demo-simple-select-filled-label"
                                id="demo-simple-select-filled"
                                value={lecTimeEnd}
                                onChange={(e) => setLecTimeEnd(e.target.value)}
                            >
                                <MenuItem value={"9:00:00"}>9시</MenuItem>
                                <MenuItem value={"10:00:00"}>10시</MenuItem>
                                <MenuItem value={"11:00:00"}>11시</MenuItem>
                                <MenuItem value={"12:00:00"}>12시</MenuItem>
                                <MenuItem value={"13:00:00"}>13시</MenuItem>
                                <MenuItem value={"14:00:00"}>14시</MenuItem>
                                <MenuItem value={"15:00:00"}>15시</MenuItem>
                                <MenuItem value={"16:00:00"}>16시</MenuItem>
                                <MenuItem value={"17:00:00"}>17시</MenuItem>
                                <MenuItem value={"18:00:00"}>18시</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid size={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 100, margin: 0}}>
                            <InputLabel id="demo-simple-select-filled-label">학점</InputLabel>
                            <Select
                                labelId="demo-simple-select-filled-label"
                                id="demo-simple-select-filled"
                                value={lecCredit}
                                onChange={(e) => setLecCredit(e.target.value)}
                            >
                                <MenuItem value={"1"}>1</MenuItem>
                                <MenuItem value={"2"}>2</MenuItem>
                                <MenuItem value={"3"}>3</MenuItem>
                                <MenuItem value={"4"}>4</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                </Grid>
            </CardContent>
            <CardActions>
                <Button onClick={handleLecSubmit} size="large">강의 등록</Button>
            </CardActions>

            <Snackbar
                open={snackbarOpen}
                autoHideDuration={5000}
                onClose={handleCloseSnackbar}
                message={snackbarMessage}
                anchorOrigin={{vertical: 'top', horizontal: 'center'}}
                action={
                    <Button color="inherit" onClick={handleCloseSnackbar}>
                        닫기
                    </Button>
                }
            >
                <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{width: '100%'}}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </React.Fragment>
    );
}


export default function LectureManagement() {
    const {availableLectures, selectedLectures, setSelectedLectures} = useFetchLectures();
    return (
        <>
            <Box pl={2} pr={2} pb={2}>
                <Grid container spacing={2}>
                    <Grid size={7}>
                        {/*TODO: 시간표에서 수업 누르면 강의 정보 카드에 정보 불러와지고 값 변경할 수 있게 해야함*/}
                        <TimeTable availableLectures={availableLectures} selectedLectures={selectedLectures}
                                   isButtonDisabled={true} onLecturesChange={setSelectedLectures}/>
                    </Grid>
                    <Grid size={5}>
                        <Box display={'flex'} flexDirection={"column"} alignItems={"center"} justifyContent={"space-between"}>
                        <Card variant="elevation" sx={{
                            maxWidth: '400px',
                        }}>
                            <LectureRegisterCard/>
                        </Card>
                            <Button startIcon={<CloudUploadIcon />} variant="contained" sx={{width: 400, mt: 2}} onClick={ (e) => {alert('excel파일로 추가하는 기능을 만들어야합니다')}}>Import Excel</Button>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        </>)
}