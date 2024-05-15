import * as React from 'react';
import './LectureApplication.css';
import Button from "@mui/material/Button";
import { TimeTable } from "./Timetable";
import {useNavigate} from "react-router-dom";
import {Lecture,registrations} from "./LectureApi";
import {useFetchLectures} from "./LectureApi";
function LectureButton({ selectedLectures }: { selectedLectures: Lecture[] }) {
    const navigate = useNavigate();
    const enrolmentTogether = () => {
        navigate("/enrolmentTogether");
    };
    const lectureCopy = () => {
        navigate("/lectureCopy");
    }
    // 수강신청 버튼 클릭 이벤트 핸들러
    const enrolment = async (selectedLectures: Lecture[]) => {
        await registrations(selectedLectures);
    };

    return (
        <div className="LectureButton">
            <Button onClick={enrolmentTogether} variant="contained" className="lectureButton" style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}} > 수강신청 함께하기</Button>
            <Button onClick={lectureCopy} variant="contained" className="lectureButton" style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}} > 수강신청 따라하기</Button>
            <Button onClick={() => enrolment(selectedLectures)} variant="contained" className="lectureButton" style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}} > 수강신청하기</Button>
        </div>
    );
}

function LecturePresetButton(){
    return(
        <div className="LecturePreset">
            <Button variant="contained" className="presetButton"
                    style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}}>A</Button>
            <Button variant="contained" className="presetButton"
                    style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}}>B</Button>
            <Button variant="contained" className="presetButton"
                    style={{ fontSize: '20px',backgroundColor: 'yellow',color:'black'}}>C</Button>
        </div>
    )
}

const LectureApplication: React.FC = () => {
    // availableLectures, selectedLectures 서버에서 받아옴
    // 자세한 내용은 LectureApi 참고
    const { availableLectures, selectedLectures, setSelectedLectures} = useFetchLectures();
    return (
        <div className="LectureApplication">
            <LecturePresetButton/>
            <div className="main">
                <TimeTable availableLectures={availableLectures} selectedLectures={selectedLectures}
                           isButtonDisabled={false} onLecturesChange={setSelectedLectures}/>
            </div>
            {/*TODO 학점이 모자르거나 넘치는경우 에러처리하기 */}
            <LectureButton selectedLectures={selectedLectures}/>
        </div>
    );
}

export { LectureApplication };