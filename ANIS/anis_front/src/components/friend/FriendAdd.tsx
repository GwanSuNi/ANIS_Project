import {Autocomplete, Grid} from '@mui/material';
import TextField from '@mui/material/TextField';
import {StudentItemList, StudentListAndDialog} from './CustomFriendList';
import {Student, useFriendSearch} from '@hooks';
import {secInstance} from '@utils';
import Box from "@mui/material/Box";
import {TimeTable} from "../lecture";

/**
 * 친구 추가하기
 */
export default function FriendAdd() {
    // 친구 찾는 훅스 갖고오기
    const {
        studentID, setStudentID, studentName,
        setStudentName, departmentName, setDepartmentName
        , birth, setBirth
        , studentList, fetchAndSetStudents
    } = useFriendSearch();

    return (
        //TODO 컴포넌트끼리 CSS 정리하기
        <>
            <Box sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <TextField
                    id="outlined-basic"
                    label="학생 검색"
                    variant="outlined"
                    sx={{width: 400, mt: '20px'}}
                    onChange={(event) => {
                        const newInputValue = event.target.value;
                        setStudentID(newInputValue);
                        setStudentName(newInputValue);
                        setDepartmentName(newInputValue);
                        setBirth(newInputValue);
                    }}
                />
            </Box>

            <StudentListAndDialog
                ListComponent={(listProps) =>
                    <StudentItemList
                        items={studentList}
                        {...listProps}
                    />
                }
                dialogTitle="친구로 추가하시겠습니까?"
                onConfirm={(section: Student | null, handleClose) => {
                    if (section != null) {
                        const friendID = section.studentID; // 친구의 ID를 여기에 입력하세요.
                        secInstance.post(`/api/friend/${friendID}`)
                            .then(response => {
                                alert("친구추가되었습니다.")
                                handleClose();
                                fetchAndSetStudents();
                                // 친구가 성공적으로 추가되었음을 사용자에게 알리는 코드를 여기에 작성하세요.
                            })
                            .catch(error => {
                                console.error(error);
                                // 에러 처리 코드를 여기에 작성하세요.
                            });
                    } else {
                        console.error('섹션이 없습니다.');
                    }
                }}
            />
        </>
    );
}