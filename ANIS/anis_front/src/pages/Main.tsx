import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {useFetchRegisteredLecturesQuery} from '@api';
import {Timetable} from '@components';
import {Box} from '@mui/material';
import Grid from "@mui/material/Grid2";

export default function Main() {
    const {data: registeredLectures = [], isLoading} = useFetchRegisteredLecturesQuery(undefined, {
        refetchOnMountOrArgChange: false, // 컴포넌트가 마운트되거나 쿼리 인자가 변경될 때 데이터를 다시 가져오지 않음
        refetchOnReconnect: false, // 네트워크 연결이 다시 복구될 때 데이터를 다시 가져오지 않음
        refetchOnFocus: false // 브라우저 탭이나 창이 다시 포커스를 받을 때 데이터를 다시 가져오지 않음
    });

    return (
        <Grid container spacing={2} sx={{display: "flex", flexDirection: "column", alignItems: "center", width:"100%"}}>
            <Grid size={12} px={2} pt={2}>
                <Box className="rectangle">
                    {isLoading ? <div>로딩중...</div> : <Timetable lectures={registeredLectures} isEnrolling={false}/>}
                </Box>
            </Grid>
            <ButtonContainer/>
        </Grid>
    );
}

function ButtonContainer() {
    const navigate = useNavigate();
    const enrolmentClick = () => {
        navigate("/lecture");
    };
    const assessmentClick = () => {
        navigate("/");
    };
    return (
        <Box sx={{display: "flex", flexDirection: "row", alignItems: "center"}}>
            <Button onClick={enrolmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black'}}>수강신청하기</Button>
            <Button onClick={assessmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black', marginLeft:5}}>설문조사하기</Button>
        </Box>
    );
}
