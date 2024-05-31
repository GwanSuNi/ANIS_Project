import {memo, useEffect, useMemo} from 'react';
import Grid from '@mui/material/Unstable_Grid2';
import Button from '@mui/material/Button';
import {Box} from '@mui/material';
import {useDispatch, useSelector} from 'react-redux';
import {Lecture, useFetchRegisteredLecturesQuery} from '@api';
import {addSelectedLecture, removeSelectedLecture, RootState} from '@redux';

interface TimetableProps {
    lectures: Lecture[],
    isEnrolling: boolean
}

interface LectureCellProps {
    lecture: Lecture,
    isSelected: boolean,
    isEnrolling: boolean,
    toggleLecture: () => void
}

// 요일과 시간으로 강의 데이터를 매핑하는 해시맵 타입 정의
interface LectureMap {
    [day: string]: {
        [time: string]: Lecture;
    };
}

const COL_CNT = 7;
const ROW_CNT = 10;
const DAYS = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'];
const START_HOUR = 9; // 첫 교시 시작 시간
const END_HOUR = 17; // 마지막 교시 시작 시간
// 시간대 배열 생성
const TIMES = Array.from({length: END_HOUR - START_HOUR + 1}, (_, i) => {
    const hour = START_HOUR + i;

    return {
        class: `${i + 1}교시`,
        startTime: `${hour}:00`,
        endTime: `${hour}:50`
    };
});

// 시간표 컴포넌트
export default function Timetable({lectures, isEnrolling}: TimetableProps) {
    const dispatch = useDispatch();
    const {data: registeredLectures = []} = useFetchRegisteredLecturesQuery();
    const selectedLectures = useSelector((state: RootState) => state.lecture.selectedLectures);

    useEffect(() => {
        registeredLectures.forEach((lecture) => dispatch(addSelectedLecture(lecture)));
    }, [registeredLectures]);

    // 강의 선택/해제 함수
    const toggleLecture = (lecture: Lecture) => {
        dispatch(selectedLectures[lecture.lecID] ? removeSelectedLecture(lecture) : addSelectedLecture(lecture));
    }

    // 해시맵을 useMemo를 사용하여 메모이제이션 함으로써 성능을 최적화(자바스크립트에서 객체는 해시맵과 유사하게 동작함)
    const lectureMap = useMemo(() => {
        // 빈 해시맵(객체) 생성
        const map: LectureMap = {};

        // 요일과 시간으로 강의 데이터를 매핑: 요일을 key로 하는 객체를 만들고, 그 안에 시간을 key로 하는 객체를 만들어 강의 데이터를 저장
        lectures.forEach((lecture: Lecture) => {
            // 요일 객체가 없으면 생성
            if (!map[lecture.lecDay])
                map[lecture.lecDay] = {};

            // 요일 객체 안에 시간 객체를 만들고 강의 데이터를 저장
            map[lecture.lecDay][lecture.lecTimeStart] = lecture;
        });

        return map;
    }, [lectures]); // 의존성 배열: 수강신청 가능한 강의들이 변경될 때에만 메모이제이션을 다시 수행

    let hour = 0; // 강의 시간

    return (
        <Grid
            container
            width='100%'
            height='100%'
            minHeight='600px'
            textAlign='center'
            borderTop='1px solid'
            borderLeft='1px solid'
        >
            {[...Array(COL_CNT)].map((_, col) => (
                <Grid key={col} container direction='column' xs={12 / COL_CNT}>
                    {[...Array(ROW_CNT)].map((_, row) => {
                        const key = `${col}-${row}`; // 셀을 식별하기 위한 키

                        if (col === 0 || row === 0) { // 요일과 시간을 표시하는 셀을 렌더링
                            if (col === 0 && row === 0) // 대각선(시간\요일) 셀을 렌더링
                                return <DiagonalCell key={key}/>;
                            else // 헤더 셀을 렌더링
                                return <HeaderCell col={col} row={row} key={key}/>;
                        } else {
                            // 해당 요일과 시간에 강의가 있는지 확인
                            const lecture = lectureMap[DAYS[col - 1]] && lectureMap[DAYS[col - 1]][TIMES[row - 1].startTime];

                            if (lecture) { // 강의가 있으면 강의 시간을 설정하고 강의 셀 컴포넌트를 렌더링
                                hour = lecture.lecCredit;

                                return (
                                    <LectureCell
                                        lecture={lecture}
                                        isSelected={!!selectedLectures[lecture.lecID]}
                                        isEnrolling={isEnrolling}
                                        toggleLecture={() => toggleLecture(lecture)} key={key}
                                    />
                                );
                            } else if (--hour > 0) { // 강의 시간이 남아있으면 아무것도 렌더링 하지 않음
                                return null;
                            } else { // 강의가 없으면 빈 셀을 렌더링
                                return <EmptyCell key={key}/>;
                            }
                        }
                    })}
                </Grid>
            ))}
        </Grid>
    );
}

// 강의 셀 컴포넌트. memo를 사용하여 렌더링 성능 최적화
const LectureCell = memo(({lecture, isSelected, isEnrolling, toggleLecture}: LectureCellProps) => {
    return (
        <Button
            onClick={toggleLecture} // 버튼 클릭 시 selected 상태를 토글
            disabled={!isEnrolling} // 버튼의 활성화 여부를 결정하는 prop
            sx={{
                gridColumn: '1',
                gridRow: `1 / span ${lecture.lecCredit}`,
                width: '100%',
                height: `calc(100% / ${ROW_CNT} * ${lecture.lecCredit})`,
                minWidth: 0,
                borderRadius: 0,
                borderRight: '1px solid black',
                borderBottom: '1px solid black',
                padding: '4px',
                backgroundColor: isSelected ? 'yellow' : 'white', // TODO
                ":hover": {backgroundColor: isSelected ? 'yellow' : 'white'}
            }}
        >
            <Box sx={{
                wordWrap: 'break-word',
                overflow: 'hidden',
                textTransform: 'none',
                whiteSpace: 'pre-line'
            }}>
                {`${lecture.lecName}\n${lecture.lecProfessor}\n${lecture.lectureRoom}`}
            </Box>
        </Button>
    );
});

// 요일과 시간을 표시하는 헤더 셀 컴포넌트. memo를 사용하여 렌더링 성능 최적화
const HeaderCell = memo(({col, row}: { col: number, row: number }) => {
    return (
        <Grid
            xs
            display='flex'
            justifyContent='center'
            alignItems='center'
            borderRight='1px solid'
            borderBottom='1px solid'
            bgcolor='#ebd480'
        >
            <Box sx={{wordWrap: 'break-word', overflow: 'hidden', whiteSpace: 'pre-line', padding: '4px'}}>
                {col === 0 ? `${TIMES[row - 1].class}\n${TIMES[row - 1].startTime}~${TIMES[row - 1].endTime}` : DAYS[col - 1]}
            </Box>
        </Grid>
    );
});

// 빈 셀 컴포넌트
function EmptyCell() {
    return <Grid xs borderRight='1px solid' borderBottom='1px solid'/>
}

// 대각선(시간\요일) 셀 컴포넌트
function DiagonalCell() {
    return (
        <Grid
            container
            sx={{
                height: `calc(100% / ${ROW_CNT})`,
                background: 'linear-gradient(to bottom left, transparent calc(50% - 1px), black calc(50% - 1px), black 50%, transparent 50%)',
                backgroundColor: '#ebd480'
            }}
        >
            <Grid container direction='column' xs>
                <Grid xs/>
                <Grid
                    xs
                    display='flex'
                    justifyContent='center'
                    alignItems='center'
                    borderBottom='1px solid'
                    fontSize={{xs: '.8rem', sm: '1rem'}}
                >
                    시간
                </Grid>
            </Grid>
            <Grid container direction='column' xs>
                <Grid
                    xs
                    display='flex'
                    justifyContent='center'
                    alignItems='center'
                    borderRight='1px solid'
                    fontSize={{xs: '.8rem', sm: '1rem'}}
                >
                    요일
                </Grid>
                <EmptyCell/>
            </Grid>
        </Grid>
    );
}