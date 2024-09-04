import {memo, useEffect, useMemo} from 'react';
import Grid from '@mui/material/Unstable_Grid2';
import Button from '@mui/material/Button';
import {Box, Typography, useTheme} from '@mui/material';
import {useDispatch, useSelector} from 'react-redux';
import {useFetchRegisteredLecturesQuery} from '@api';
import {addSelectedLecture, removeSelectedLecture, RootState} from '@redux';
import {Lecture} from '@types';

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
        [time: number]: Lecture;
    };
}

const COL_CNT = 7;
const ROW_CNT = 10;
const DAYS = ['월', '화', '수', '목', '금', '토', '일'];
const START_HOUR = 9; // 첫 교시 시작 시간

// 시간표 컴포넌트
export default function Timetable({lectures, isEnrolling}: TimetableProps) {
    // Redux 스토어에 어떤 일이 일어났음을 알리는 것을 의미
    const dispatch = useDispatch();
    // 현재 등록되어있는 즉 내가 수강신청 한 강의들을 불러옴 (색칠되어있는애들)
    const {data: registeredLectures = []} = useFetchRegisteredLecturesQuery();
    // 현재 선택되어있는 강의들을 모두 갖고옴
    const selectedLectures = useSelector((state: RootState) => state.lecture.selectedLectures);

    const theme = useTheme();

    useEffect(() => {
        // 수강 가능한 강의들을 모두 받아온 후 , 반복문을 돌려 useEffect(변화가있을때) 강의 변화 감지하기
        registeredLectures.forEach((lecture) => dispatch(addSelectedLecture(lecture)));
    }, [registeredLectures]);

    // 강의 선택/해제 함수
    const toggleLecture = (lecture: Lecture) => {
        dispatch(selectedLectures[lecture.lecName] ? removeSelectedLecture(lecture) : addSelectedLecture(lecture));
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
            map[lecture.lecDay][parseInt(lecture.lecTimeStart)] = lecture;
        });
        return map;
    }, [lectures]); // 의존성 배열: 수강신청 가능한 강의들이 변경될 때에만 메모이제이션을 다시 수행
    console.log('selectedLectures:', selectedLectures); // selectedLectures 상태를 출력
    let hour = 0; // 강의 시간

    return (
        <Grid
            container
            width='100%'
            height='100%'
            textAlign='center'
            borderTop='1px solid'
            borderLeft='1px solid'
        >
            {[...Array(COL_CNT)].map((_, col) => (
                <Grid key={col} container direction='column' xs>
                    {[...Array(ROW_CNT)].map((_, row) => {
                        const key = `${col}-${row}`; // 셀을 식별하기 위한 키

                        if (col === 0 || row === 0) { // 요일과 시간을 표시하는 셀을 렌더링
                            if (col === 0 && row === 0)
                                return <EmptyCell key={key} bgColor={theme.palette.primary.main}/>;
                            else // 헤더 셀을 렌더링
                                return <HeaderCell col={col} row={row} key={key}/>;
                        } else {
                            // 해당 요일과 시간에 강의가 있는지 확인
                            const lecture = lectureMap[DAYS[col - 1]] && lectureMap[DAYS[col - 1]][START_HOUR + row - 1];

                            if (lecture) { // 강의가 있으면 강의 시간을 설정하고 강의 셀 컴포넌트를 렌더링
                                hour = lecture.lecCredit;

                                return (
                                    <LectureCell
                                        lecture={lecture}
                                        isSelected={!!selectedLectures[lecture.lecName]}
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
            color='inherit'
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
                backgroundColor: isSelected ? 'yellow' : 'white',
                ":hover": {backgroundColor: isSelected ? 'yellow' : 'white'}
            }}
        >
            <Box whiteSpace='pre-line' textTransform='none'>
                <Typography variant='body2' display={{xs: 'block', sm: 'none'}}>
                    {`${lecture.lectureRoom}`}
                </Typography>
                <Typography variant='body1' fontFamily='NanumGothicBold'
                            display={{xs: 'none', sm: 'block', md: 'none'}}>
                    {lecture.lectureRoom}
                </Typography>
                <Typography variant='body1' fontFamily='NanumGothicBold' display={{xs: 'none', md: 'block'}}>
                    {`${lecture.lecName}\n${lecture.lectureRoom}`}
                </Typography>
            </Box>
        </Button>
    );
});

// 요일과 시간을 표시하는 헤더 셀 컴포넌트. memo를 사용하여 렌더링 성능 최적화
const HeaderCell = memo(({col, row}: { col: number, row: number }) => {
    const theme = useTheme();

    return (
        <Grid
            xs
            display='flex'
            justifyContent='center'
            alignItems='center'
            borderRight='1px solid'
            borderBottom='1px solid'
            bgcolor= 'primary.main'
            p={.5}
            sx={{height: `calc(100% / ${ROW_CNT})`}}
        >
            <Box whiteSpace='pre-line'>
                <Typography
                    variant='body1'
                    fontFamily='NanumGothicBold'
                    display={{xs: 'block', sm: 'none'}}
                >
                    {col === 0 ? `${START_HOUR + row - 1}시` : DAYS[col - 1]}
                </Typography>
                <Typography
                    variant='body1'
                    fontFamily='NanumGothicBold'
                    display={{xs: 'none', sm: 'block', md: 'none'}}
                >
                    {col === 0 ? `${row}교시\n${START_HOUR + row - 1}:00` : `${DAYS[col - 1]}요일`}
                </Typography>
                <Typography
                    variant='body1'
                    fontFamily='NanumGothicExtraBold'
                    display={{xs: 'none', md: 'block'}}
                >
                    {col === 0 ? `${row}교시\n${START_HOUR + row - 1}:00 ~ ${START_HOUR + row - 1}:50` : `${DAYS[col - 1]}요일`}
                </Typography>
            </Box>
        </Grid>
    );
});

// 빈 셀 컴포넌트
function EmptyCell({bgColor = 'white'}: { bgColor?: string }) {
    return <Grid xs bgcolor={bgColor} p={.5} borderRight='1px solid' borderBottom='1px solid'/>;
}