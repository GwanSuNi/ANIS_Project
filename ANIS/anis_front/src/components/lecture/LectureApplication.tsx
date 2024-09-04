import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {useFetchAvailableLecturesQuery} from '@api';
import {fetchLectureOfPreset, registrations} from '../../deprecated/LectureApi';
import Timetable from './Timetable';
import {Lecture} from "@types";
import {addSelectedLecture, RootState, setSelectedLectures} from "@redux";
import {useDispatch, useSelector} from 'react-redux';
function LectureButton() {
    const navigate = useNavigate();
    const enrolmentTogether = () => {
        navigate('/enrolmentTogether');
    };
    const lectureCopy = () => {
        navigate('/lectureCopy');
    }
    const dispatch = useDispatch();
    const selectedLectures = useSelector((state: RootState) => state.lecture.selectedLectures);

    const handleRegisterLectures = async () => {
        const lecturesArray: Lecture[] = Object.values(selectedLectures);
        const result = await registrations(lecturesArray);
        if (result && result.success !== false) {
            dispatch({ type: 'REGISTER_LECTURE_SUCCESS', payload: result });
        } else {
            dispatch({ type: 'REGISTER_LECTURE_FAILURE', error: result.error || 'Registration failed' });
        }
    };

    return (
        <div className="LectureButton">
            <Button onClick={enrolmentTogether} variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청 함께하기</Button>
            <Button onClick={lectureCopy} variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청 따라하기</Button>
            <Button onClick={handleRegisterLectures}variant="contained" className="lectureButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}> 수강신청하기</Button>
        </div>
    );
}

function LecturePresetButton() {
    const dispatch = useDispatch();
    // fetchLectureOfPreset
    const fetchAndSetLectureOfPreset = async (presetName: string) => {
        const lectures = await fetchLectureOfPreset(presetName);
        dispatch(setSelectedLectures(lectures));
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
    // 수강 가능한 애들을 불러오는 코드(강의표전체)
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