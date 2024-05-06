import Grid from '@mui/material/Unstable_Grid2';
import Button from '@mui/material/Button';
import {Box} from "@mui/material";
import {memo, useMemo} from "react";

// 강의 데이터 타입 정의
interface Lecture {
    name: string;
    professor: string;
    lectureRoom: string;
    day: string;
    time: string;
    hour: number;
}

// 요일과 시간으로 강의 데이터를 매핑하는 해시맵 타입 정의
interface LectureMap {
    [day: string]: {
        [time: string]: Lecture;
    };
}

const COL_CNT = 6;
const ROW_CNT = 10;
const DAYS = ['월', '화', '수', '목', '금'];
const TIMES = [
    {
        class: '1교시',
        startTime: '09:00',
        endTime: '09:50'
    },
    {
        class: '2교시',
        startTime: '10:00',
        endTime: '10:50'
    },
    {
        class: '3교시',
        startTime: '11:00',
        endTime: '11:50'
    },
    {
        class: '4교시',
        startTime: '12:00',
        endTime: '12:50'
    },
    {
        class: '5교시',
        startTime: '13:00',
        endTime: '13:50'
    },
    {
        class: '6교시',
        startTime: '14:00',
        endTime: '14:50'
    },
    {
        class: '7교시',
        startTime: '15:00',
        endTime: '15:50'
    },
    {
        class: '8교시',
        startTime: '16:00',
        endTime: '16:50'
    },
    {
        class: '9교시',
        startTime: '17:00',
        endTime: '17:50'
    }
];

// 시간표 컴포넌트
export default function Timetable() {
    // TODO: 서버로부터 받은 강의 데이터
    const lectures: Lecture[] = [
        {
            name: '창의융합캡스톤디자인',
            professor: '조성택',
            lectureRoom: '효행관 404호',
            day: '월',
            time: '14:00',
            hour: 3
        },
        {
            name: '빅데이터통계분석',
            professor: '이영희',
            lectureRoom: '효행관 404호',
            day: '화',
            time: '10:00',
            hour: 3
        },
        {
            name: '시스템분석설계실무',
            professor: '김광섭',
            lectureRoom: '효행관 404호',
            day: '수',
            time: '10:00',
            hour: 3
        },
        {
            name: '데이터베이스관리기술',
            professor: '김은욱',
            lectureRoom: '효행관 404호',
            day: '수',
            time: '14:00',
            hour: 3
        },
        {
            name: '애플리케이션프레임워크',
            professor: '박남일',
            lectureRoom: '효행관 404호',
            day: '목',
            time: '09:00',
            hour: 3
        }
    ];

    // 해시맵을 useMemo를 사용하여 메모이제이션 함으로써 성능을 최적화(자바스크립트에서 객체는 해시맵과 유사하게 동작함)
    const lectureMap = useMemo(() => {
        // 빈 해시맵(객체) 생성
        const map: LectureMap = {};

        // 요일과 시간으로 강의 데이터를 매핑: 요일을 key로 하는 객체를 만들고, 그 안에 시간을 key로 하는 객체를 만들어 강의 데이터를 저장
        lectures.forEach(lecture => {
            // 요일 객체가 없으면 생성
            if (!map[lecture.day])
                map[lecture.day] = {};

            // 요일 객체 안에 시간 객체를 만들고 강의 데이터를 저장
            map[lecture.day][lecture.time] = lecture;
        });

        return map;
    }, [lectures]); // 의존성 배열: lectures가 변경될 때에만 메모이제이션을 다시 수행

    let hour = 0; // 강의 시간

    return (
        <Grid
            container
            width='100%'
            height='100%'
            borderTop='1px solid'
            borderLeft='1px solid'
        >
            {[...Array(COL_CNT)].map((_, col) => (
                <Grid
                    key={col}
                    container
                    direction='column'
                    xs={12 / COL_CNT}
                >
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
                                hour = lecture.hour;

                                return <LectureCell lecture={lecture} key={key}/>;
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
                <Grid
                    xs
                    borderRight='1px solid'
                    borderBottom='1px solid'
                />
            </Grid>
        </Grid>
    );
}

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
            <Box sx={{
                wordWrap: 'break-word',
                overflow: 'hidden',
                whiteSpace: 'pre-line',
                padding: '4px'
            }}>
                {col === 0 ? `${TIMES[row - 1].class}\n${TIMES[row - 1].startTime}~${TIMES[row - 1].endTime}` : DAYS[col - 1]}
            </Box>
        </Grid>
    );
});

// 강의 셀 컴포넌트. memo를 사용하여 렌더링 성능 최적화
const LectureCell = memo(({lecture}: { lecture: Lecture }) => {
    return (
        <Button
            sx={{
                gridColumn: '1',
                gridRow: `1 / span ${lecture.hour}`,
                width: '100%',
                height: `calc(100% / ${ROW_CNT} * ${lecture.hour})`,
                minWidth: 0,
                borderRadius: 0,
                borderRight: '1px solid black',
                borderBottom: '1px solid black',
                padding: '4px',
            }}
        >
            <Box sx={{
                wordWrap: 'break-word',
                overflow: 'hidden',
                textTransform: 'none',
                whiteSpace: 'pre-line'
            }}>
                {`${lecture.name}\n${lecture.professor}\n${lecture.lectureRoom}`}
            </Box>
        </Button>
    );
});

// 빈 셀 컴포넌트
function EmptyCell() {
    return (
        <Grid
            xs
            borderRight='1px solid'
            borderBottom='1px solid'
        />
    );
}