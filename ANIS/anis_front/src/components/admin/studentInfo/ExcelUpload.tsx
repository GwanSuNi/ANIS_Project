import React, {useState} from "react";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import {Button, Dialog, DialogActions, DialogTitle, DialogContent, CircularProgress} from "@mui/material";
import {secInstance} from "@utils";
import {openSnackbar} from "../../../redux/snackbarSlice";
import {useDispatch} from "react-redux";

interface ExcelUploadProps {
    fetchMembersData: () => Promise<void>;
}

export default function ExcelUpload({fetchMembersData} : ExcelUploadProps) {
    const [open, setOpen] = useState(false);
    const [file, setFile] = useState<File | null>();
    const [loading, setLoading] = useState(false);
    const dispatch = useDispatch();

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const selectedFile = event.target.files?.[0];
        // 선택된 파일이 엑셀 파일인지 확인
        if (selectedFile && (selectedFile.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || selectedFile.type === 'application/vnd.ms-excel')) {
            setFile(selectedFile);
        } else {
            dispatch(openSnackbar({message: "엑셀 파일만 선택할 수 있습니다. (.xlsx, .xls 형식)", severity: "error"}))
            setFile(null); // 잘못된 파일 선택 시 상태 초기화
        }
    };

    const handleUpload = async () => {
        if (!file) {
            dispatch(openSnackbar({message: "엑셀 업로드 실패. 다시 시도해주세요.", severity: "error"}))
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        setLoading(true);
        try {
            const response = await secInstance.post('/api/join/excel', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            dispatch(openSnackbar({message: response.data, severity: "success"}))
            handleClose(); // 다이얼로그 닫기
            await fetchMembersData();
        } catch (error) {
            console.error('업로드 실패:', error);
            dispatch(openSnackbar({message: "엑셀 업로드 실패. 다시 시도해주세요.", severity: "error"}))
        } finally {
            setLoading(false);
        }
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setFile(null); // 다이얼로그 닫을 때 파일 상태 초기화
    };

    return (
        <>
            <Button startIcon={<CloudUploadIcon/>} variant="contained" sx={{width: 200}}
                    onClick={handleClickOpen}>Excel로 학생 추가</Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>엑셀 파일 업로드</DialogTitle>
                <DialogContent>
                    <input
                        type="file"
                        accept=".xlsx, .xls"
                        onChange={handleFileChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>취소</Button>
                    {
                        loading ?
                            <CircularProgress/>
                            :
                            <Button onClick={handleUpload} variant="contained" disabled={!file}>업로드</Button>
                    }
                </DialogActions>
            </Dialog>
        </>
    )
}