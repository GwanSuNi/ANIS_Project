import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {TimeTable} from '@components';
import {useFetchLectures} from '@hooks';
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

export default function Main() {
    const {availableLectures, selectedLectures, setSelectedLectures} = useFetchLectures();
    const navigate = useNavigate();
    const enrolmentClick = () => {
        navigate("/lecture");
    };
    const assessmentClick = () => {
        navigate("/");
    }
    return (
        <Grid container direction="column" alignItems="center" justifyContent="center">
            <Box sx={{ width: 650, height: 650, border: '0.5px solid black',
                justifyContent: 'center', alignItems: 'center', marginBottom: 2, marginTop: 2 }}>
                <TimeTable onLecturesChange={setSelectedLectures} availableLectures={availableLectures}
                           selectedLectures={selectedLectures} isButtonDisabled={true}/>
            </Box>
            <Grid container justifyContent="space-around" alignItems="center" sx={{ width: 500 }}>
                    <Button onClick={enrolmentClick} variant="contained" sx={{ width: 150, height: 100, fontSize: '30px', backgroundColor: 'yellow', color: 'black' , marginBottom: 2 }}>수강신청하기</Button>
                    <Button onClick={assessmentClick} variant="contained" sx={{ width: 150, height: 100, fontSize: '30px', backgroundColor: 'yellow', color: 'black' , marginBottom: 2 }}>설문조사하기</Button>
            </Grid>
        </Grid>
    );
}
