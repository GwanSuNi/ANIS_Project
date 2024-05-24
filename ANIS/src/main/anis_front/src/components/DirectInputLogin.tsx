import {Autocomplete} from "@mui/material";
import TextField from "@mui/material/TextField";
import {StudentItemList, StudentListAndDialog} from "./CustomFriendList";
import React from "react";
import {useStudentSearch} from "../hooks/SearchHooks";

/**
 * 직접 선택해서 로그인하는 컴포넌트
 */
const DirectInputLogin = () => {
    const { studentID,setStudentID,studentName,
        setStudentName,departmentName,setDepartmentName
        ,birth,setBirth
        ,studentList} = useStudentSearch();

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
                dialogTitle="정말로 본인이 맞습니까?"
                onConfirm={() => {
                    //TODO 예를 눌렀을때 로그인하는 기능을 만들어주세요 @Gwansuni
                }}
            />
        </>
    );
};
export {DirectInputLogin}