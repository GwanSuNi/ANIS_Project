import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {TimeTable} from './Timetable';
import {fetchLectureOfPreset, Lecture, registrations, useFetchLectures} from '@hooks';
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

export default function LectureApplication() {
    const navigate = useNavigate();
    const enrolmentTogether = () => {
        navigate('/enrolmentTogether');
    };
    const lectureCopy = () => {
        navigate('/lectureCopy');
    }
    // 수강신청 버튼 클릭 이벤트 핸들러
    const enrolment = async (selectedLectures: Lecture[]) => {
        await registrations(selectedLectures);
    };
    // availableLectures, selectedLectures 서버에서 받아옴
    // 자세한 내용은 LectureApi 참고
    const fetchAndSetLectureOfPreset = async (presetName: string) => {
        const lectures = await fetchLectureOfPreset(presetName);
        setSelectedLectures(lectures);
    };
    const {availableLectures, selectedLectures, setSelectedLectures} = useFetchLectures();

    return (
        <Grid container direction="column" alignItems="center" justifyContent="center">
                <Grid container justifyContent="space-around" alignItems="center" sx={{ width: 500 }}>
                        <Button onClick={() => fetchAndSetLectureOfPreset("A")} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black', marginTop: 2}}>A</Button>
                        <Button onClick={() => fetchAndSetLectureOfPreset("B")} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black', marginTop: 2}}>B</Button>
                        <Button onClick={() => fetchAndSetLectureOfPreset("C")} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black', marginTop: 2}}>C</Button>
                </Grid>
                <Box sx={{ width: 650, height: 650, border: '0.5px solid black',
                    justifyContent: 'center', alignItems: 'center', marginBottom: 2, marginTop: 2}}>
                    <TimeTable availableLectures={availableLectures} selectedLectures={selectedLectures}
                               isButtonDisabled={false} onLecturesChange={setSelectedLectures}/>
                </Box>
                <Grid container justifyContent="space-around" alignItems="center" sx={{ width: 500 }}>
                        <Button onClick={enrolmentTogether} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black'  ,marginBottom: 2}}>수강신청 함께하기</Button>
                        <Button onClick={lectureCopy} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black'  ,marginBottom: 2}}>수강신청 따라하기</Button>
                        <Button onClick={() => enrolment(selectedLectures)} variant="contained" sx={{ width: 110, height: 100, fontSize: '20px', backgroundColor: 'yellow', color: 'black', marginBottom: 2 }}>수강신청하기</Button>
                </Grid>
        </Grid>
    );
}
