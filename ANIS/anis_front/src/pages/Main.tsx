import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {useFetchRegisteredLecturesQuery} from '@api';
import {Timetable} from '@components';

export default function Main() {
    const {data: registeredLectures = [], isLoading} = useFetchRegisteredLecturesQuery();

    return (
        <div className="container">
            <h1>성인학습자 전용 NIS</h1>
            <div className="rectangle">
                {isLoading ? <div>로딩중...</div> : <Timetable lectures={registeredLectures} isEnrolling={false}/>}
            </div>
            <ButtonContainer/>
        </div>
    );
}

function ButtonContainer() {
    const navigate = useNavigate();
    const enrolmentClick = () => {
        navigate("/lecture");
    };
    const assessmentClick = () => {
        navigate("/");
    }
    return (
        <div className="button-container">

            <Button onClick={enrolmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black'}}>수강신청하기</Button>

            <Button onClick={assessmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black'}}>설문조사하기</Button>

        </div>
    );
}