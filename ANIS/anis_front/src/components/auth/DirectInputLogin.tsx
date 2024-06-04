import {Autocomplete} from '@mui/material';
import TextField from '@mui/material/TextField';
import {StudentItemList, StudentListAndDialog} from '@components';
import {Student, useLogin, useStudentSearch} from '@hooks';
import {useDispatch, useSelector} from "react-redux";
import {RootState, setUsername} from "@redux";
import {secInstance} from "@utils";
import {useEffect, useState} from "react";
import Box from "@mui/material/Box";

/**
 * 직접 선택해서 로그인하는 컴포넌트
 */
export default function DirectInputLogin() {
    const {
        studentID, setStudentID, studentName,
        setStudentName, departmentName, setDepartmentName
        , birth, setBirth
        , studentList,
    } = useStudentSearch();
    const {password, setPassword, handleSubmit} = useLogin();
    const username = useSelector((state: RootState) => state.username.username);
    const dispatch = useDispatch();
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

    useEffect(() => {
        if (selectedStudent) {
            handleSubmit();
        }
    }, [username]);

    return (
        //TODO TextField 컴포넌트화 하기
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
                dialogTitle="정말로 본인이 맞습니까?"
                onConfirm={ async (section: Student | null, handleClose) => {
                    if (section != null) {
                        const friendID = section.studentID;
                        try {
                            dispatch(setUsername(friendID));
                            setSelectedStudent(section);
                        } catch (error) {
                            console.error(error);
                        }
                    } else {
                        console.error('섹션이 없습니다.');
                    }
                }}
            />
        </>
    );
}