import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {useFetchAvailableLecturesQuery} from '@api';
import {fetchLectureOfPreset} from '../../deprecated/LectureApi';
import Timetable from './Timetable';

function LectureButton() {
    const navigate = useNavigate();
    const enrolmentTogether = () => {
        navigate('/enrolmentTogether');
    };
    const lectureCopy = () => {
        navigate('/lectureCopy');
    }

    return (
        <div className="LectureButton">
            <Button onClick={enrolmentTogether} variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청 함께하기</Button>
            <Button onClick={lectureCopy} variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청 따라하기</Button>
            <Button variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청하기</Button>
        </div>
    );
}

function LecturePresetButton() {
    // fetchLectureOfPreset
    const fetchAndSetLectureOfPreset = async (presetName: string) => {
        const lectures = await fetchLectureOfPreset(presetName);
    };
    return (
        <div className="LecturePreset">
            <Button onClick={() => fetchAndSetLectureOfPreset("A")} variant="contained" className="presetButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}>A</Button>
            <Button onClick={() => fetchAndSetLectureOfPreset("B")} variant="contained" className="presetButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}>B</Button>
            <Button onClick={() => fetchAndSetLectureOfPreset("C")} variant="contained" className="presetButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}>C</Button>
        </div>
    )
}

export default function LectureApplication() {
    const {data: availableLectures = [], isLoading, error} = useFetchAvailableLecturesQuery();

    if (isLoading)
        return <div>로딩중...</div>
    if (error)
        return <div>에러발생</div>

    return (
        <div className="LectureApplication">
            <LecturePresetButton/>
            <div className="main">
                <Timetable lectures={availableLectures} isEnrolling={true}/>
            </div>
            {/*TODO 학점이 모자르거나 넘치는경우 에러처리하기 */}
            <LectureButton/>
        </div>
    );
}