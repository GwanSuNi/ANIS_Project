import Button from '@mui/material/Button';
import {useNavigate} from 'react-router-dom';
import {useFetchRegisteredLecturesQuery} from '@api';
import {Timetable} from '@components';

export default function Main() {
    const {data: registeredLectures = [], isLoading} = useFetchRegisteredLecturesQuery(undefined, {
        refetchOnMountOrArgChange: false, // 컴포넌트가 마운트되거나 쿼리 인자가 변경될 때 데이터를 다시 가져오지 않음
        refetchOnReconnect: false, // 네트워크 연결이 다시 복구될 때 데이터를 다시 가져오지 않음
        refetchOnFocus: false // 브라우저 탭이나 창이 다시 포커스를 받을 때 데이터를 다시 가져오지 않음
    });

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
    };
    return (
        <div className="button-container">
            <Button onClick={enrolmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black'}}>수강신청하기</Button>
            <Button onClick={assessmentClick} variant="contained" className="button"
                    style={{fontSize: '30px', backgroundColor: 'yellow', color: 'black'}}>설문조사하기</Button>
        </div>
    );
}
