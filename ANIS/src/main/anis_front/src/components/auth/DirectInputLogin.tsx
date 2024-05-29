import {Autocomplete} from '@mui/material';
import TextField from '@mui/material/TextField';
import {StudentItemList, StudentListAndDialog} from '@components';
import {Student, useLogin, useStudentSearch} from '@hooks';
import {useDispatch, useSelector} from "react-redux";
import {RootState, setUsername} from "@redux";
import {secInstance} from "@utils";
import {useEffect, useState} from "react";

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
    const dispatch = useDispatch();
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);

    useEffect(() => {
        if (selectedStudent) {
            handleSubmit();
        }
    }, [selectedStudent]);

    return (
        //TODO 컴포넌트끼리 CSS 정리하기
        <>
            <Autocomplete
                options={studentList}
                // 이름기준으로 검색되게
                getOptionLabel={(option) => option.studentName}
                style={{width: 300}}
                onInputChange={(event, newInputValue) => {
                    setStudentID(newInputValue);
                    setStudentName(newInputValue);
                    setDepartmentName(newInputValue);
                    setBirth(newInputValue);
                }}
                renderInput={(params) => <TextField {...params} label="학생 검색"/>}
            />

            <StudentListAndDialog
                ListComponent={(listProps) =>
                    <StudentItemList
                        items={studentList}
                        {...listProps}
                    />
                }
                dialogTitle="정말로 본인이 맞습니까?"
                onConfirm={(section: Student | null, handleClose) => {
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