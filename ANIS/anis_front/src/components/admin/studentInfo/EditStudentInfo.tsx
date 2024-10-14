import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid2";
import {Box, Button, MenuItem, TextField} from "@mui/material";
import React, {Dispatch, useEffect, useRef, useState} from "react";
import {StudentDataForAdmin} from "./StudentManagement";
import Typography from "@mui/material/Typography";
import ImageCard from "../../myInfo/ImageCard";
import useUpdateProfileImage from "../../../hooks/useUpdateProfileImage";
import axios from "axios";
import secInstance from "../../../utils/secInstance";
import {useDispatch} from "react-redux";
import {openSnackbar} from "../../../redux/snackbarSlice";

interface EditStudentInfoProps {
    editStudentInfo: StudentDataForAdmin;
    setEditStudentInfo: Dispatch<React.SetStateAction<StudentDataForAdmin | undefined>>;
    fetchMembersData: () => Promise<void>;
}

export default function EditStudentInfo({editStudentInfo, setEditStudentInfo, fetchMembersData}: EditStudentInfoProps) {
    const dispatch = useDispatch();
    const {mutate: updateProfileImage} = useUpdateProfileImage();
    const {studentID, depName, isQuit, studentName, birth, role} = editStudentInfo;
    const inputRef = useRef<HTMLInputElement>(null);

    const [departments, setDepartments] = useState<{ depIndex: number, depName: string }[]>([]);
    const [selectedDepartment, setSelectedDepartment] = useState<number | null>(null); // 선택된 학과의 depIndex를 저장

    // 학과 목록을 불러옴
    useEffect(() => {
        const fetchDepartments = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/departments');
                setDepartments(response.data);

                // depName에 해당하는 depIndex 찾기
                const department = response.data.find((dep: { depName: string }) => dep.depName === depName);
                if (department) {
                    setSelectedDepartment(department.depIndex);
                }
            } catch (error) {
                dispatch(openSnackbar({message:"학과 정보 불러오기 실패", severity:"error"}));
            }
        };
        fetchDepartments();
    }, [depName]);

    const handleDepartmentChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        const depIndex = event.target.value as number;
        setSelectedDepartment(depIndex);

        // 선택된 depIndex에 해당하는 depName을 찾아서 업데이트
        const selectedDep = departments.find((dep) => dep.depIndex === depIndex);
        if (selectedDep) {
            editStudentInfo.depName = selectedDep.depName;  // 학과 이름 업데이트
        }
    };

    const roles = [
        {
            value: "관리자",
            label: "관리자",
        },
        {
            value: "학생",
            label: "학생",
        },
    ]

    const handleRoleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEditStudentInfo({...editStudentInfo, role: event.target.value});
    }

    const handleBirthChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        let input = event.target.value;

        // 숫자 외의 모든 문자 제거 (하지만 setBirth 후 -는 다시 추가되므로 문제가 발생하지 않음)
        input = input.replace(/[^0-9]/g, "");

        // 최대 8자리까지만 허용 (YYYYMMDD)
        if (input.length > 8) {
            input = input.slice(0, 8);
        }

        let formattedInput = input;

        // 입력된 값이 5자리 이상일 경우 YYYY-MM-DD 포맷 적용
        if (input.length >= 6) {
            formattedInput = `${input.slice(0, 4)}-${input.slice(4, 6)}-${input.slice(6, 8)}`;
        } else if (input.length >= 4) {
            formattedInput = `${input.slice(0, 4)}-${input.slice(4, 6)}`;
        }

        setEditStudentInfo({...editStudentInfo, birth: formattedInput}); // 상태 업데이트
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (!inputRef.current) return;

        const cursorPosition = inputRef.current.selectionStart; // selectionStart 가져오기

        if (cursorPosition === null) return;

        // 백스페이스 처리 로직 추가
        if (event.key === "Backspace") {
            const currentValue = inputRef.current.value;
            // 예: 2000-04-23에서 "-" 뒤의 숫자와 "-"를 함께 삭제하는 로직 추가
            if (currentValue[cursorPosition - 1] === "-") {
                event.preventDefault(); // 기본 동작 방지
                const newValue = currentValue.slice(0, cursorPosition - 2) + currentValue.slice(cursorPosition);
                inputRef.current.value = newValue;
                inputRef.current.setSelectionRange(cursorPosition - 1, cursorPosition - 1); // 커서 위치 조정
            }
        }
    };

    // TODO: MyInfo와 코드 중복이 발생하므로 ReactQuery를 사용하는 훅에다 넣어야할 것 같음
    const handleUploadImageClick = async (studentID: string) => {
        // 파일 선택 요소를 동적으로 생성합니다.
        const fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept = 'image/*'; // 이미지만 선택할 수 있도록 합니다.

        // 파일 선택 요청을 처리합니다.
        fileInput.addEventListener('change', (event) => {
            /// event.target이 null인지 확인합니다.
            if (fileInput.files === null) {
                return;
            }
            const file = fileInput.files[0];
            if (file) {
                updateProfileImage({studentID, file});
            }
        });

        // 파일 선택 요소를 클릭하여 파일 선택 대화상자를 엽니다.
        fileInput.click();
    };

    const handleEditStudentInfo = async () => {
        try {
            const response = await secInstance.put("/api/member", {
                studentID: studentID,
                studentName: studentName,
                depName: depName,
                birth: birth,
                role: role,
                isQuit: isQuit,
            });

            const updatedInfo = response.data;
            setEditStudentInfo({...editStudentInfo,
                studentName: updatedInfo.studentName,
                role: updatedInfo.role,
                birth: updatedInfo.birth
            });
            dispatch(openSnackbar({message: "[" + studentID + "] 에 대한 정보 변경 성공", severity:"success"}))

            await fetchMembersData();
        } catch (error) {
            dispatch(openSnackbar({message: "[" + studentID + "] 에 대한 정보 변경 실패", severity:"error"}))
        }
    }

    return (
        <>
            <Paper elevation={3} sx={{
                padding: '10px',
                textAlign: 'center',
            }}>
                <Typography variant={'h6'} mb={2}>회원 정보 수정</Typography>
                <Grid container spacing={2}>
                    <Grid size={12}>
                        <Box
                            sx={{
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}
                        >
                            <ImageCard userInfo={{
                                studentID: String(studentID),
                                studentName: editStudentInfo.studentName,
                                departmentName: editStudentInfo.depName,
                                birth: String(editStudentInfo.birth),
                            }} handleUploadImageClick={handleUploadImageClick}/>
                        </Box>
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            id="standard-read-only-input"
                            label="학번"
                            value={studentID}
                            variant="outlined"
                            sx={{
                                minWidth: '100px',
                                textAlign: 'center'
                            }}
                            slotProps={{
                                input: {
                                    readOnly: true,
                                },
                            }}
                        />
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            select
                            label="학과"
                            value={selectedDepartment || ""}
                            onChange={handleDepartmentChange}
                            fullWidth
                        >
                            {departments.map((department) => (
                                <MenuItem key={department.depIndex} value={department.depIndex}>
                                    {department.depName}
                                </MenuItem>
                            ))}
                        </TextField>
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            label="이름"
                            value={studentName}
                            variant="outlined"
                            onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                                setEditStudentInfo({...editStudentInfo, studentName: event.target.value});
                            }}
                            sx={{
                                minWidth: '100px',
                                textAlign: 'center'
                            }}
                        />
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            label="생년월일"
                            inputRef={inputRef}
                            value={birth}
                            variant="outlined"
                            onChange={handleBirthChange}
                            onKeyDown={handleKeyDown}
                            sx={{
                                minWidth: '130px',
                                textAlign: 'center'
                            }}
                            slotProps={{
                                htmlInput: {maxLength:10},
                            }} // 최대 길이 10자 ('YYYY-MM-DD')
                        />
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            select
                            label="권한"
                            defaultValue={role}
                            value={role}
                            onChange={handleRoleChange}
                            variant="outlined"
                            color={(role === "관리자" ? "warning" : "success")}
                            sx={{
                                minWidth: '100px',
                                textAlign: 'center'
                            }}
                            fullWidth
                            focused
                        >
                            {roles.map((option) => (
                                <MenuItem key={option.value} value={option.value}>
                                    {option.label}
                                </MenuItem>
                            ))}
                        </TextField>
                    </Grid>
                    <Grid size={6}>
                        <TextField
                            label="탈퇴"
                            value={isQuit}
                            variant="outlined"
                            color={(isQuit === "정상" ? "success" : "warning")}
                            sx={{
                                minWidth: '100px',
                                textAlign: 'center'
                            }}
                            slotProps={{
                                input: {
                                    readOnly: true,
                                },
                            }}
                            focused
                        />
                    </Grid>
                    <Grid size={12}>
                        <Button variant={"contained"} sx={{mb: '2px'}} onClick={handleEditStudentInfo}>수정하기</Button>
                    </Grid>
                </Grid>
            </Paper>
        </>
    );
}