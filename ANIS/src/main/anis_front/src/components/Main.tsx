import Button from '@mui/material/Button';
import {useNavigate} from "react-router-dom";
import './Main.css';
import {TimeTable} from "./Timetable";
import {useFetchLectures} from "../hooks/LectureHooks";

export default function Main() {
    // 겹치는 부분 리팩토링 자세한 부분은 LectureApi 참고
    const { availableLectures, selectedLectures, setSelectedLectures} = useFetchLectures();
    return (
        <div className="container">
            <h1>성인학습자 전용 NIS</h1>
            <div className="rectangle">
                <TimeTable onLecturesChange={setSelectedLectures} availableLectures={availableLectures}
                           selectedLectures={selectedLectures} isButtonDisabled={true}/>
            </div>
            <ButtonContainer />
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
                    style={{ fontSize: '30px', backgroundColor: 'yellow', color: 'black' }}>수강신청하기</Button>

            <Button onClick={assessmentClick}variant="contained" className="button"
                    style={{ fontSize: '30px', backgroundColor: 'yellow', color: 'black' }}>설문조사하기</Button>

        </div>
    );
}