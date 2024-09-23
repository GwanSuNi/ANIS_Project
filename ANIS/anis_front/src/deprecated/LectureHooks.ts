import {useEffect, useState} from 'react';
import {fetchAvailableLectures, fetchSelectedLectures, Lecture} from './LectureApi';


/**
 * 현재 내가 수강신청한 과목과 , 수강신청 가능한 강의리스트에대한 훅스
 */
// Main , LectureApplication 에서 사용됨
const useFetchLectures = () => {
    // 현재시간 기준으로 수강 가능한 LectureList
    const [availableLectures, setAvailableLectures] = useState<Lecture[]>([]);
    // 내가 수강하고있는 LectureList 혹은 현재 내가 시간표에서 선택한 LectureList
    const [selectedLectures, setSelectedLectures] = useState<Lecture[]>([]);
    // TODO 에러처리
    useEffect(() => {
        const fetchData = async () => {
            const available = await fetchAvailableLectures();
            const selected = await fetchSelectedLectures();
            setAvailableLectures(available);
            setSelectedLectures(selected);
        };
        fetchData();
    }, []);

    // 강의 데이터 변동 시 즉각 변동되도록 함
    useEffect(() => {
        const updateLectures = async () => {
            const updatedAvailableLectures = await fetchAvailableLectures();
            setAvailableLectures(updatedAvailableLectures);
        };

        updateLectures();
    }, [availableLectures]);

    return {availableLectures, selectedLectures, setSelectedLectures};
};

export {useFetchLectures}