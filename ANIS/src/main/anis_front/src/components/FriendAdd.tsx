import {Autocomplete} from "@mui/material";
import TextField from "@mui/material/TextField";
import {StudentItemList, StudentListAndDialog} from "./CustomFriendList";
import React from "react";
import {Student} from "./MemberApi";
import secInstance from "../utils/secInstance";
import {useFriendSearch} from "../hooks/SearchHooks";

/**
 * 친구 추가하기
 */
export const FriendAdd = () => {
    // 친구 찾는 훅스 갖고오기
    const { studentID,setStudentID,studentName,
        setStudentName,departmentName,setDepartmentName
        ,birth,setBirth
        ,studentList,fetchAndSetStudents} = useFriendSearch();

    return (
        //TODO 컴포넌트끼리 CSS 정리하기
        <>
            <Autocomplete
                options={studentList}
                // 이름기준으로 검색되게
                getOptionLabel={(option) => option.studentName}
                style={{ width: 300 }}
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
                dialogTitle="친구로 추가하시겠습니까?"
                onConfirm={(section: Student |  null, handleClose) => {
                    if (section != null) {
                        const friendID = section.studentID; // 친구의 ID를 여기에 입력하세요.
                        secInstance.post(`/api/friend/${friendID}`)
                            .then(response => {
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
};