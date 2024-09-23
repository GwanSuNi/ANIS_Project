import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import React, {FC, ReactNode, useEffect, useState} from "react";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import {Student, useLogin, useStudentSearch} from "@hooks";
import Checkbox from "@mui/material/Checkbox";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import DialogTitle from "@mui/material/DialogTitle";
import TextField from "@mui/material/TextField";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "@redux";
import {Table, TableBody, TableCell, TableContainer, TableRow} from "@mui/material";
import Paper from "@mui/material/Paper";
import {Timetable} from "@components";
import {useFetchFriendList} from "../../hooks/MemberApi";
import {Lecture} from "@types";
import {fetchFriendLectureList} from "../../deprecated/LectureApi";

// TODO 리팩토링을 위한 코드정리
interface StudentItemProps {
    item: Student;
    index: number;
    onClick: (item: Student) => void;
    checked?: boolean;
    onToggle?: (index: number) => void;
    buttonDisabled?: boolean;  // 추가된 prop
}

/**
 * @param item studentType의
 * @param index
 * @param onClick
 * @param checked
 * @param onToggle
 * @param buttonDisabled
 * @constructor
 */
const StudentItem: FC<StudentItemProps> = ({
                                               item,
                                               index,
                                               onClick,
                                               checked = false,
                                               onToggle,
                                               buttonDisabled = false
                                           }) => (
    <ListItem
        key={index}
        sx={{display: 'flex', justifyContent: 'center'}}
        disablePadding
        secondaryAction={onToggle && (
            <Checkbox
                edge="end"
                onChange={(event) => onToggle(index)}
                checked={checked}
            />
        )}
    >
        <ListItemButton disabled={buttonDisabled} sx={{width: '650px', maxWidth: '650px'}}
                        onClick={() => onClick(item)}>
            <StudentInfo item={item}/>
        </ListItemButton>
    </ListItem>
);


interface CustomDialogProps {
    height?: number;
    maxWidth?: number;
    open: boolean;
    onClose: () => void;
    onConfirm: () => void; // 확인 버튼 클릭 이벤트 핸들러
    title: string;
    content: ReactNode; // 대화상자 내용
}

const CustomDialog: FC<CustomDialogProps> = ({
                                                 height = 375,
                                                 maxWidth = 680,
                                                 open,
                                                 onClose,
                                                 onConfirm,
                                                 title,
                                                 content
                                             }) => (
    <Dialog open={open}
            onClose={onClose}
            PaperProps={{
                style: {
                    width: '100%',
                    height: height,
                    maxWidth: maxWidth,
                    border: '1px solid black',
                    marginBottom: 2, marginTop: 2,
                    borderRadius: '5px'
                }
            }}>
        <DialogTitle>
            <Typography variant='h4' fontFamily='NanumGothicBold' color='primary.contrastText' m='auto'>
                {title}
            </Typography>
        </DialogTitle>
        <DialogContent>
            {content}
        </DialogContent>
        <ConfirmDialog onConfirm={onConfirm} onClose={onClose}/>
    </Dialog>
);

interface StudentItemListProps {
    items: Student[]; // 학생 목록
    onListItemClick: (item: Student) => void; // 항목 클릭 이벤트 핸들러
}

const StudentItemList: FC<StudentItemListProps> = ({items, onListItemClick}) => (
    <Grid container alignItems="center" justifyContent="center" overflow='auto'>
        <Box sx={{
            width: 650, height: 650, border: '1px solid black',
            marginBottom: 2, marginTop: 2, borderRadius: '5px'
        }}>
            <List sx={{overflow: 'auto', height: '650px'}}>
                {items.map((item, index) => (
                    <StudentItem
                        item={item}
                        index={index}
                        onClick={() => onListItemClick(item)}
                        // onToggle prop을 제공하지 않습니다.
                    />
                ))}
            </List>
        </Box>
    </Grid>);

const TestComponent: FC = () => {
    const [open, setOpen] = useState(false);
    const [dialogContent, setDialogContent] = useState<ReactNode>(null);
    const handleItemClick = (item: Student) => {
        setDialogContent(
            <StudentInfo item={item}/>
        );
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    };

    const handleConfirm = () => {
        setOpen(false);
        console.log('Confirmed!');
    };
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
            <StudentItemList items={studentList} onListItemClick={handleItemClick}/>
            <CustomDialog open={open} onClose={handleClose} onConfirm={handleConfirm} title="정말로 본인이 맞습니까?"
                          content={dialogContent}/>
        </>
    );
}
const TestTimeTableComponent: FC = () => {
    const friendList = useFetchFriendList();
    const [open, setOpen] = useState(false);
    const [dialogContent, setDialogContent] = useState<ReactNode>(null);
    const [friendLectures, setFriendLectures] = useState<Lecture[]>([]);

    useEffect(() => {

    }, [friendLectures]);
    const handleItemClick = async (item: Student) => {
        const lectures = await fetchFriendLectureList(item.studentID);
        setFriendLectures(lectures);
        console.log(lectures);
        setDialogContent(
            <>
                <Timetable lectures={lectures} isEnrolling={false}/>
            </>
        );
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    };

    const handleConfirm = () => {
        setOpen(false);
        console.log('Confirmed!');
    };

    const {password, setPassword, handleSubmit} = useLogin();
    const username = useSelector((state: RootState) => state.username.username);
    const dispatch = useDispatch();

    return (
        <>
            <StudentItemList items={friendList} onListItemClick={handleItemClick}/>
            <CustomDialog height={1000} maxWidth={1000} open={open} onClose={handleClose} onConfirm={handleConfirm}
                          title="정말로 본인이 맞습니까?"
                          content={dialogContent}/>
        </>
    );
};


// const handleItemClickTimeTable = (item: Student) => {
//     setDialogContent(
//
//         <TimeTable availableLectures={} selectedLectures={} isButtonDisabled={} onLecturesChange={}/>
//     )
// }

interface StudentInfoProps {
    item: Student
}

const StudentInfo: React.FC<StudentInfoProps> = ({item}) => {
    return (
        <Box display="flex" flexDirection="row" alignItems="center">
            <ListItemAvatar>
                <Avatar sx={{height: '100px', width: '100px'}}/>
            </ListItemAvatar>
            <Box display="flex" justifyContent="center" flex={1}>
                <TableContainer
                    sx={{
                        maxWidth: '500px',
                        maxHeight: '135px',
                        marginLeft: 2
                    }}
                    component={Paper}
                >
                    <Table size="medium" aria-label="a dense table">
                        <TableBody>
                            <TableRow>
                                <TableCell component="th" scope="row" sx={{}}>
                                    <Typography fontSize='23px'>
                                        이름: {item.studentName}
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row" sx={{}}>
                                    <Typography fontSize='23px'>
                                        학번: {item.studentID}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row" sx={{}}>
                                    <Typography fontSize='23px'>
                                        학과: {item.departmentName}
                                    </Typography>
                                </TableCell>
                                <TableCell component="th" scope="row" sx={{}}>
                                    <Typography fontSize='23px'>
                                        생년월일: {item.birth}
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
        </Box>
    );
};

type ConfirmDialogProps = {
    onConfirm: () => void;
    onClose: () => void;
};

const ConfirmDialog: React.FC<ConfirmDialogProps> = ({onConfirm, onClose}) => {
    return (
        <DialogActions sx={{display: 'flex', justifyContent: 'space-evenly', alignItems: 'center'}}>
            <Grid sx={{
                height: '100px',
                width: '150px',
                border: '2px solid #black',
                borderRadius: '5px',
                backgroundColor: '#ffff00',
                marginBottom: 3.5
            }}>
                <Button variant='contained' onClick={onConfirm} sx={{
                    display: 'flex',
                    justifyContent: 'flex',
                    alignItems: 'flex',
                    height: '100px',
                    width: '150px'
                }}>
                    <Typography fontSize='30px' fontFamily='NanumGothicBold' color='primary.contrastText'
                                m='auto'>
                        예
                    </Typography>
                </Button>
            </Grid>
            <Grid sx={{
                height: '100px',
                width: '150px',
                border: '2px solid #black',
                borderRadius: '5px',
                marginBottom: 3.5
            }}>
                <Button variant='contained' onClick={onClose} sx={{
                    display: 'flex',
                    justifyContent: 'flex',
                    alignItems: 'flex',
                    height: '100px',
                    width: '150px'
                }}>
                    <Typography fontSize='30px' fontFamily='NanumGothicBold' color='primary.contrastText'
                                m='auto'>
                        아니요
                    </Typography>
                </Button>
            </Grid>
        </DialogActions>
    );
};

export {
    TestComponent, TestTimeTableComponent
}