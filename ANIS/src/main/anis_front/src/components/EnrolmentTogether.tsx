import {StudentCheckList} from "./CustomFriendList";
import React, {useEffect, useState} from "react";
import {fetchFriendList, Student} from "./MemberApi";
import Button from "@mui/material/Button";
import secInstance from "../utils/secInstance";

/**
 * 수강신청 함께하기 컴포넌트
 */
const EnrolmentTogether = () => {
    const [friendList, setFriendList] = useState<Student[]>([]);
    const [checkedStudents, setCheckedStudents] = useState<Student[]>([]);
    useEffect(() => {
        const fetchMyFriendList = async () => {
            try {
                const fetchedFriends = await fetchFriendList();
                setFriendList(fetchedFriends);
            } catch (error) {
                console.error('Error fetching friend list:', error);
            }
        };
        fetchMyFriendList();
    }, []);
    return (
        <>
            <StudentCheckList items={friendList} onCheckedItemsChange={(checkedStudents) => {
                setCheckedStudents(checkedStudents); // 체크된 학생들의 목록을 상태로 저장
            }}/>
            // TODO 에러처리하기
            <Button onClick={async () => {
                try {
                    const response = await secInstance.post('/api/registrations/friends', checkedStudents);
                    if (response.data) {
                        alert("수강신청이 되었습니다");
                    } else {
                        alert("수강신청함께하기 실패");
                    }
                } catch (error) {
                    console.error('Error during enrolment with friends:', error);
                }
            }
            } variant="contained" className="presetButton"
                    style={{fontSize: '20px', backgroundColor: 'yellow', color: 'black'}}>수강신청함께하기</Button>
        </>
    );
}

export {EnrolmentTogether}