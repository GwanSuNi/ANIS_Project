// React 관련 라이브러리
import {ComponentType, CSSProperties, FC, KeyboardEvent, SyntheticEvent, useEffect, useRef, useState} from 'react';
import {useNavigate} from 'react-router-dom';

// MUI 관련 라이브러리
import AddIcon from '@mui/icons-material/Add';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import ButtonBase from '@mui/material/ButtonBase';
import Checkbox from '@mui/material/Checkbox';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Fab from '@mui/material/Fab';
import Grid from '@mui/material/Grid';
import Grow from '@mui/material/Grow';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import MenuList from '@mui/material/MenuList';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import {createTheme, styled, Theme, ThemeProvider} from '@mui/material/styles';
import Typography from '@mui/material/Typography';

// 기타 라이브러리
import {FixedSizeList} from 'react-window';

// 로컬 컴포넌트
import {deleteFriend, fetchFriendLectureList, fetchFriendList, fetchSelectedLectures, Lecture, Student} from '@hooks';
import {TimeTable} from '@components';

/**
 *
 * @constructor
 */
const FriendListView = () => {
    // TODO nullItem 추가 말고 다른방법을 생각해보기
    const nullItem = {
        // 0번째 index에 AddButton 이 들어가서 nullItem 을 추가함
        studentID: "nullItem",
        studentName: "nullItem",
        departmentName: "nullItem",
        birth: "nullItem"
    }
    const navigate = useNavigate();
    const lightTheme = createTheme({palette: {mode: 'light'}});
    const [open, setOpen] = useState<boolean[]>([]);
    const anchorRef = useRef<(HTMLButtonElement | null)[]>([]);

    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);
    const [showTimeTable, setShowTimeTable] = useState(false);
    const [friendLectures, setFriendLectures] = useState<Lecture[]>([]);
    const [myLectures, setMyLectures] = useState<Lecture[]>([]);
    const [friendList, setFriendList] = useState<Student[]>([]);

    const StyledPaper = styled(Paper)({
        width: '100%',
        height: '100%',
    });
    const handleToggle = (index: number) => {
        setOpen((prevOpen) => {
            const newOpen = [...prevOpen];
            newOpen[index] = !newOpen[index];
            return newOpen;
        });
    };
    const handleClick = () => {
        navigate("/friend/add");
    };
    const handleClose = (event: SyntheticEvent | Event, index: number) => {
        if (
            anchorRef.current[index] &&
            anchorRef.current[index]!.contains(event.target as Node)
        ) {
            return;
        }

        setOpen((prevOpen) => {
            const newOpen = [...prevOpen];
            newOpen[index] = false;
            return newOpen;
        });
    };
    /**
     *
     * @param event
     * @param index
     */
    const handleListKeyDown = (event: KeyboardEvent, index: number) => {
        if (event.key === 'Tab') {
            event.preventDefault();
            setOpen((prevOpen) => {
                const newOpen = [...prevOpen];
                newOpen[index] = false;
                return newOpen;
            });
        } else if (event.key === 'Escape') {
            setOpen((prevOpen) => {
                const newOpen = [...prevOpen];
                newOpen[index] = false;
                return newOpen;
            });
        }
    };
    /**
     * 친구리스트 받아오기
     */
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


    // 나의 시간표를 갖고옴
    const prevOpen = useRef(open);
    useEffect(() => {
        const fetchMyLectureData = async () => {
            const fetchedLectures = await fetchSelectedLectures();
            setMyLectures(fetchedLectures);
        };
        for (let i = 0; i < open.length; i++) {
            if (prevOpen.current[i] && !open[i]) {
                anchorRef.current[i]!.focus();
            }
        }
        prevOpen.current = open;
        fetchMyLectureData();
    }, [open]);
    // Assuming data is an array of objects that you map to create dynamic content
    return (
        <Grid container spacing={1} justifyContent="center" alignItems="center">
            {[lightTheme].map((theme: Theme, themeIndex: number) => (
                <Grid item xs={6} key={themeIndex} sx={{justifyContent: 'center', alignItems: 'center'}}>
                    <ThemeProvider theme={theme}>
                        <Box
                            sx={{
                                p: 0,
                                borderRadius: 2,
                                bgcolor: 'background.default',
                                display: 'grid',
                                gridTemplateColumns: {md: '1fr 1fr'},
                                gap: 2,
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}
                        >
                            {[nullItem, ...friendList].map((item: Student | null, index: number) => {
                                if (!open[index]) {
                                    open[index] = false;
                                }
                                if (!anchorRef.current[index]) {
                                    anchorRef.current[index] = null;
                                }

                                return (
                                    index === 0 ?
                                        <Grid justifyContent="center" alignItems="center" display="flex" key={index}
                                              onClick={handleClick}>
                                            <Fab color="primary" aria-label="add" sx={{width: 100, height: 100,}}>
                                                <AddIcon sx={{
                                                    width: 100,
                                                    height: 100,
                                                    justifyContent: 'center',
                                                    alignItems: 'center'
                                                }}/>
                                            </Fab>
                                        </Grid>
                                        :
                                        <Grid justifyContent="center" alignItems="center" display="flex" key={index}>
                                            <Paper
                                                sx={{
                                                    p: 0,
                                                    margin: 'auto',
                                                    maxWidth: 190,
                                                    flexGrow: 1,
                                                    backgroundColor: (theme) =>
                                                        theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                                                }}
                                            >
                                                <ButtonBase sx={{
                                                    width: '100%',
                                                    height: '100%',
                                                    justifyContent: 'center',
                                                    alignItems: 'center'
                                                }} ref={(el) => anchorRef.current[index] = el}
                                                            onClick={() => handleToggle(index)}>
                                                    <Grid container spacing={0} direction="column" alignItems="center"
                                                          justifyContent="center">
                                                        <Grid sx={{p: 1}}>
                                                            <Avatar
                                                                src="/static/images/avatar/1.jpg"
                                                                sx={{width: 100, height: 100}}
                                                            />
                                                        </Grid>
                                                        <Grid item xs container direction="column" spacing={0}
                                                              alignItems="center" justifyContent="center">
                                                            <Typography gutterBottom variant="body1" component="div"
                                                                        align="center">
                                                                이름 {item ? item.studentName : ''}
                                                            </Typography>
                                                            <Typography variant="body2" align="center">
                                                                학과 {item ? item.departmentName : ''}
                                                            </Typography>
                                                            <Typography variant="body2" align="center">
                                                                학번 {item ? item.studentID : ''}
                                                            </Typography>
                                                            <Typography gutterBottom variant="body2" align="center">
                                                                생년월일 {item ? item.birth : ''}
                                                            </Typography>
                                                        </Grid>
                                                    </Grid>
                                                </ButtonBase>
                                                <Popper
                                                    open={open[index]}
                                                    anchorEl={anchorRef.current[index]}
                                                    role={undefined}
                                                    placement="right-start"
                                                    transition
                                                    disablePortal
                                                >
                                                    {({TransitionProps, placement}) => (
                                                        <Grow
                                                            {...TransitionProps}
                                                            style={{
                                                                transformOrigin:
                                                                    placement === 'right-start' ? 'left top' : 'left bottom',
                                                            }}>
                                                            <Paper>
                                                                <ClickAwayListener
                                                                    onClickAway={(event) => handleClose(event, index)}>

                                                                    <MenuList
                                                                        autoFocusItem={open[index]}
                                                                        id="composition-menu"
                                                                        aria-labelledby="composition-button"
                                                                        onKeyDown={(event) => handleListKeyDown(event, index)}>

                                                                        <MenuItem onClick={async (event) => {
                                                                            handleClose(event, index);
                                                                            // 학생의 ID를 사용하여 시간표 데이터를 가져옵니다.
                                                                            // TODO 시간표가 없다면 현재 시간표가 없습니다!! 띄우기
                                                                            if (item) {
                                                                                setFriendLectures(await fetchFriendLectureList(item.studentID));
                                                                                setShowTimeTable(true);
                                                                            }
                                                                            // else{
                                                                            // }
                                                                        }}>
                                                                            친구 시간표보기
                                                                        </MenuItem>

                                                                        <MenuItem onClick={(event) => {
                                                                            handleClose(event, index);
                                                                            // 대화 상자를 열고 선택된 학생 설정
                                                                            setSelectedStudent(item);
                                                                            setDialogOpen(true);
                                                                        }}>친구 삭제하기</MenuItem>
                                                                    </MenuList>
                                                                </ClickAwayListener>
                                                            </Paper>
                                                        </Grow>
                                                    )}
                                                </Popper>
                                            </Paper>
                                        </Grid>
                                );
                            })}
                        </Box>
                    </ThemeProvider>
                </Grid>
            ))}
            <CustomDialog
                title="친구를 삭제하시겠습니까?"
                open={dialogOpen}
                onClose={() => setDialogOpen(false)}
                onConfirm={async () => {
                    //TODO 친구 삭제 로직 추가하기
                    const studentIDToDelete: string = selectedStudent?.studentID || ''; // 기본값으로 빈 문자열 설정
                    // 친구 삭제 로직
                    await deleteFriend(studentIDToDelete);
                    // 친구 리스트 다시 받아오기
                    const fetchedFriends = await fetchFriendList();
                    setFriendList(fetchedFriends);
                    setDialogOpen(false);
                }}
                section={selectedStudent}
            />
            <Dialog
                open={showTimeTable}
                onClose={() => setShowTimeTable(false)}
                PaperProps={{style: {width: '100%', height: '690px', maxWidth: '690px'}}}>
                <DialogContent>
                    {/* TODO 친구의 시간표 , 나의 시간표 색으로 표시해서 보여주기*/}
                    {/* availableLectures -> 친구의 시간표 , selectedLectures -> 나의 시간표 겹치는 부분 색칠로 표시*/}
                    <TimeTable onLecturesChange={setMyLectures} availableLectures={friendLectures}
                               selectedLectures={myLectures} isButtonDisabled={true}/>
                </DialogContent>
            </Dialog>
        </Grid>
    );
}

interface StudentCheckProps {
    items: Student[];
    onCheckedItemsChange?: (checkedItems: Student[]) => void;
}

/***
 * 모바일 수강신청 함께하기 페이지 컴포넌트
 * @param items
 * @param onCheckedItemsChange
 * @constructor
 */
const StudentCheckList: FC<StudentCheckProps> = ({items, onCheckedItemsChange}) => {
    const [checked, setChecked] = useState<number[]>([]); // 체크된 아이템을 저장할 state
    // 체크박스 토글 핸들러
    const handleToggle = (index: number) => () => {
        const currentIndex = checked.indexOf(index);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(index);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked.sort((a, b) => a - b)); // 체크된 항목들을 오름차순으로 정렬

        if (onCheckedItemsChange) {
            const checkedItems = newChecked.map(checkedIndex => items[checkedIndex]);
            onCheckedItemsChange(checkedItems);
        }

    };

    // 체크된 항목들을 별도의 배열로 분리
    const checkedSections = checked.map(index => items[index]);
    const uncheckedSections = items.filter((_, index) => !checked.includes(index));

    // 체크된 항목 배열을 체크되지 않은 항목 배열 앞에 붙임
    const sortedSections = [...checkedSections, ...uncheckedSections];

    return (
        <List sx={{width: '100%', maxWidth: 360, bgcolor: 'background.paper'}}>
            {sortedSections.map((item, index) => {
                return (
                    <ListItem
                        key={index}
                        alignItems="flex-start"
                        secondaryAction={
                            <Checkbox
                                edge="end"
                                onChange={handleToggle(items.indexOf(item))} // 원래 배열에서의 인덱스를 사용
                                checked={checked.includes(items.indexOf(item))} // 원래 배열에서의 인덱스를 사용
                            />
                        }
                        disablePadding
                        button // ListItem을 클릭 가능한 버튼으로 만듦
                    >
                        <ListItemButton onClick={handleToggle(items.indexOf(item))}>
                            <ListItemAvatar>
                                <Avatar
                                    // src={`/static/images/avatar/${index + 1}.jpg`}
                                />
                            </ListItemAvatar>
                            <ListItemText
                                primary={
                                    <>
                                        <div>이름: {item.studentName}학과: {item.departmentName}</div>
                                        <div>학번: {item.studentID}생년월일: {item.birth}</div>
                                    </>
                                }
                            />
                        </ListItemButton>
                    </ListItem>
                );
            })}
        </List>
    );
};


interface ListRow {
    index: number;
    style: CSSProperties;
}

const StudentItemList: FC<StudentListProps> = ({items, onListItemClick}) => {
    const Row: FC<ListRow> = ({index, style}) => {
        const item = items[index];
        return (
            <ListItem
                key={index}
                style={style}
                alignItems="flex-start"
                disablePadding
                onClick={() => onListItemClick(item)} // 클릭한 섹션을 인자로 전달하여 다이얼로그 열기
            >
                <ListItemButton>
                    <ListItemAvatar>
                        <Avatar
                            // 사진넣는곳
                            // src={`/static/images/avatar/${index + 1}.jpg`}
                        />
                    </ListItemAvatar>
                    <ListItemText
                        primary={
                            <>
                                <div>이름: {item.studentName} 학과: {item.departmentName}</div>
                                <div>학번: {item.studentID} 생년월일: {item.birth}</div>
                            </>
                        }
                    />
                </ListItemButton>
            </ListItem>
        );
    };

    return (
        <FixedSizeList
            height={400}
            width={360}
            itemSize={70}
            itemCount={items.length}
            overscanCount={5}
        >
            {Row}
        </FixedSizeList>
    );
}

interface StudentListProps {
    items: Student[];
    onListItemClick: (section: Student) => void;
}

interface DialogProps {
    title: string;
    onClose: () => void;
    onConfirm: (section: Student | null) => void; // 타입 변경
    open: boolean;
    section: Student | null;
    showTimeTable?: boolean;  // 테이블을 보여줄지 말지를 결정하는 prop
}

const CustomDialog: FC<DialogProps> = ({title, onConfirm, open, onClose, section, showTimeTable = false}) => {
    const [myLectures, setMyLectures] = useState<Lecture[]>([]);  // 강의 목록을 상태로 관리합니다.
    const [friendLectures, setFriendLectures] = useState<Lecture[]>([]);

    useEffect(() => {
        if (showTimeTable) {  // showTimeTable이 true일 때만 강의 목록을 가져옵니다.
            fetchSelectedLectures().then(setMyLectures);  // 강의 목록을 가져와서 상태를 업데이트합니다.
            fetchFriendLectureList(section?.studentID).then(setFriendLectures);
        }
    }, [showTimeTable, section]);
    return (
        <Dialog
            open={open}
            onClose={onClose}
            PaperProps={showTimeTable ? {style: {width: '100%', height: '1000px', maxWidth: '800px'}} : {}}
        >
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {section && (
                        <>
                            <ListItem sx={{flexDirection: 'row'}}>
                                <ListItemText
                                    primary={
                                        <>
                                            <div style={{display: 'flex', flexDirection: 'row'}}>
                                                <div>
                                                    <ListItemAvatar>
                                                        <Avatar
                                                            // 사진넣는곳
                                                            // src={`/static/images/avatar/.jpg`}
                                                        />
                                                    </ListItemAvatar>
                                                </div>
                                                <div style={{flexDirection: 'column'}}>
                                                    <div>이름: {section.studentName} 학과: {section.departmentName}</div>
                                                    <div>학번: {section.studentID} 생년월일: {section.birth}</div>
                                                </div>
                                            </div>
                                            {showTimeTable && <TimeTable onLecturesChange={setMyLectures}
                                                                         availableLectures={friendLectures}
                                                                         selectedLectures={myLectures}
                                                                         isButtonDisabled={true}/>}
                                        </>
                                    }
                                />
                            </ListItem>
                        </>
                    )}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => onConfirm(section)}>{"예"}</Button>
                <Button onClick={onClose}>{"아니요"}</Button>
            </DialogActions>
        </Dialog>
    )
}

interface StudentListAndDialogProps {
    ListComponent: ComponentType<{ onListItemClick: (section: Student) => void }>;
    dialogTitle: string;
    onConfirm: (section: Student | null, handleClose: () => void) => void; // 타입 변경
    showTimeTableCheck?: boolean;  // 테이블을 보여줄지 말지를 결정하는 prop
}

/***
 * 친구추가하기 , 모바일 로그인 직접입력 , 모바일 수강신청 불러오기 페이지에서 사용가능
 * @param ListComponent CustomList컴포넌트를 사용해서 보여줄 list 를 넣기
 * @param title modal 의 제목을 입력
 * @param onConfirm 확인 버튼 클릭시 실행될 메서드 입력
 * @constructor
 */
const StudentListAndDialog: FC<StudentListAndDialogProps> = ({
                                                                 ListComponent,
                                                                 dialogTitle,
                                                                 onConfirm,
                                                                 showTimeTableCheck = false
                                                             }) => {

    const [dialog, setDialog] = useState<{ open: boolean; section: Student | null }>({
        open: false,
        section: null,
    });

    // 다이얼로그 열기 핸들러
    const handleOpenDialog = (section: Student) => {
        setDialog({open: true, section});
    };

    // 다이얼로그 닫기 핸들러
    const handleCloseDialog = () => {
        setDialog({open: false, section: null});
    };

    return (
        <>
            <ListComponent onListItemClick={handleOpenDialog}/>
            <CustomDialog
                open={dialog.open}
                onClose={handleCloseDialog}
                section={dialog.section}
                onConfirm={() => onConfirm(dialog.section, handleCloseDialog)} // handleCloseDialog를 인자로 전달
                title={dialogTitle} // 외부에서 받은 title을 사용합니다.
                showTimeTable={showTimeTableCheck} // TimeTable을 보여줄지 말지를 결정하는 prop
            />
        </>
    );
}

export {StudentListAndDialog, StudentItemList, CustomDialog, StudentCheckList, FriendListView}