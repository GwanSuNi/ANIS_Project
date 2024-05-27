import {useEffect, useState} from 'react';
import {StudentItemList, StudentListAndDialog} from '@components';
import {fetchFriendList, Student} from '@hooks';
import {secInstance} from '@utils';

/**
 * 수강신청 따라하기 컴포넌트
 */
export default function LectureCopy() {
    const [friendList, setFriendList] = useState<Student[]>([]);
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
        <StudentListAndDialog
            ListComponent={(listProps) =>
                <StudentItemList
                    // axios로 내가 받아온 데이터를 보여주기
                    items={friendList}
                    {...listProps}
                />
            }
            // 모달안에 들어가는 텍스트를 바꾸기
            dialogTitle="해당 친구의 시간표대로 수강신청 할까요?"
            // "예" 를 클릭했을때 작동하는 메서드 넣기
            onConfirm={(section: Student | null, handleClose) => {
                if (section != null) {
                    const friendID = section.studentID; // 친구의 ID를 여기에 입력하세요.
                    secInstance.post(`/api/registrations/${friendID}`)
                        .then(response => {
                            handleClose();
                            alert("수강신청이 완료되었습니다.")
                            // 친구가 성공적으로 추가되었음을 사용자에게 알리는 코드를 여기에 작성하세요.
                        })
                        .catch(error => {
                            console.error(error);
                        });
                } else {
                    console.error('유저를 찾을수없습니다.');
                }
            }}
        />
    );
}