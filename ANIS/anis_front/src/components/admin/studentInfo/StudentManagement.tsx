import {
    Alert,
    alpha,
    Box,
    Button,
    IconButton,
    Snackbar,
    TableCell,
    TableHead,
    TableRow,
    TableSortLabel,
    Toolbar,
    Tooltip
} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import Checkbox from "@mui/material/Checkbox";
import Typography from "@mui/material/Typography";
import {visuallyHidden} from '@mui/utils';
import FilterListIcon from '@mui/icons-material/FilterList';
import BlockIcon from '@mui/icons-material/Block';
import Paper from "@mui/material/Paper";
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TablePagination from "@mui/material/TablePagination";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";
import {secInstance} from "@utils";
import Grid from "@mui/material/Grid2";
import EditStudentInfo from "./EditStudentInfo";
import {useDispatch} from "react-redux";
import {openSnackbar} from "../../../redux/snackbarSlice";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import ExcelUpload from "./ExcelUpload";

export interface StudentDataForAdmin {
    studentID: number;
    depName: string;
    grade: string;
    studentName: string;
    birth: string;
    lastLogin: string;
    isQuit: string;
    role: string;
    edit: string;
}

function descendingComparator<T>(a: T, b: T, orderBy: keyof T) {
    if (b[orderBy] < a[orderBy]) {
        return -1;
    }
    if (b[orderBy] > a[orderBy]) {
        return 1;
    }
    return 0;
}

type Order = 'asc' | 'desc';

function getComparator<Key extends keyof any>(
    order: Order,
    orderBy: Key,
): (
    a: { [key in Key]: number | string },
    b: { [key in Key]: number | string },
) => number {
    return order === 'desc'
        ? (a, b) => descendingComparator(a, b, orderBy)
        : (a, b) => -descendingComparator(a, b, orderBy);
}

interface HeadCell {
    disablePadding: boolean;
    id: keyof StudentDataForAdmin;
    label: string;
    numeric: boolean;
}

const headCells: readonly HeadCell[] = [
    {
        id: 'studentID',
        numeric: true,
        disablePadding: false,
        label: '학번',
    },
    {
        id: 'depName',
        numeric: false,
        disablePadding: false,
        label: '학과',
    },
    {
        id: 'grade',
        numeric: false,
        disablePadding: false,
        label: '학년',
    },
    {
        id: 'studentName',
        numeric: false,
        disablePadding: false,
        label: '이름',
    },
    {
        id: 'birth',
        numeric: false,
        disablePadding: false,
        label: '생년월일',
    },
    {
        id: 'lastLogin',
        numeric: false,
        disablePadding: false,
        label: '마지막 접속',
    },
    {
        id: 'isQuit',
        numeric: false,
        disablePadding: false,
        label: '탈퇴 여부',
    },
    {
        id: 'role',
        numeric: false,
        disablePadding: false,
        label: '권한',
    },
    {
        id: 'edit',
        numeric: false,
        disablePadding: true,
        label: '정보 수정',
    }
];

interface EnhancedTableProps {
    numSelected: number;
    onRequestSort: (event: React.MouseEvent<unknown>, property: keyof StudentDataForAdmin) => void;
    onSelectAllClick: (event: React.ChangeEvent<HTMLInputElement>) => void;
    order: Order;
    orderBy: string;
    rowCount: number;
}

interface EnhancedTableToolbarProps {
    numSelected: number;
    selectedIDs: readonly number[];
    fetchMembersData: () => Promise<void>;
}

function EnhancedTableToolbar(props: EnhancedTableToolbarProps) {
    const dispatch = useDispatch();

    const {
        numSelected,
        selectedIDs,
        fetchMembersData
    } = props;

    const handleToggleQuitUser = async () => {
        try {
            const response = await secInstance.put("/api/admin/member/quit", selectedIDs); // Controller에선 String[]로 받으나 자동 형변환?
            fetchMembersData();
            dispatch(openSnackbar({message: response.data + " 명의 탈퇴 정보가 변경되었습니다.", severity: 'success'}));
        } catch (error) {
            dispatch(openSnackbar({message: "요청을 처리하는 중에 오류가 발생했습니다.", severity: 'error'}));
            console.log(error);
        }
    }
    return (
        <Toolbar
            sx={[
                {
                    pl: {sm: 2},
                    pr: {xs: 1, sm: 1},
                },
                numSelected > 0 && {
                    bgcolor: (theme) =>
                        alpha(theme.palette.primary.main, theme.palette.action.activatedOpacity),
                },
            ]}
        >
            {numSelected > 0 ? (
                <Typography
                    sx={{flex: '1 1 100%'}}
                    color="inherit"
                    variant="subtitle1"
                    component="div"
                >
                    {numSelected} 명 선택됨
                </Typography>
            ) : (
                <Typography
                    sx={{flex: '1 1 100%'}}
                    variant="h6"
                    id="tableTitle"
                    component="div"
                >
                    사용자 목록
                </Typography>
            )}
            {numSelected > 0 ? (
                <Tooltip title="탈퇴여부 변경">
                    <IconButton>
                        <BlockIcon onClick={handleToggleQuitUser}/>
                    </IconButton>
                </Tooltip>
            ) : (
                <Tooltip title="Filter list">
                    <IconButton>
                        {/*TODO: 필터 아이콘을 선택하면 Member, Admin 필터링되게 handler 추가*/}
                        <FilterListIcon/>
                    </IconButton>
                </Tooltip>
            )}
        </Toolbar>
    );
}

function EnhancedTableHead(props: EnhancedTableProps) {
    const {onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort} =
        props;
    const createSortHandler =
        (property: keyof StudentDataForAdmin) => (event: React.MouseEvent<unknown>) => {
            onRequestSort(event, property);
        };

    return (
        <TableHead>
            <TableRow>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        indeterminate={numSelected > 0 && numSelected < rowCount}
                        checked={rowCount > 0 && numSelected === rowCount}
                        onChange={onSelectAllClick}
                        inputProps={{
                            'aria-label': 'select all students',
                        }}
                    />
                </TableCell>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={headCell.numeric ? 'right' : 'left'}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

interface StudentTableParams {
    setEditStudentInfo: Dispatch<React.SetStateAction<StudentDataForAdmin | undefined>>;
    fetchMembersData: () => Promise<void>;
    students: StudentDataForAdmin[];
}

function StudentTable({setEditStudentInfo, fetchMembersData, students}: StudentTableParams) {
    const [order, setOrder] = React.useState<Order>('asc');
    const [orderBy, setOrderBy] = React.useState<keyof StudentDataForAdmin>('studentID');
    const [selected, setSelected] = React.useState<readonly number[]>([]); // 선택된 학생의 ID를 저장
    const [page, setPage] = React.useState(0);
    const [dense, setDense] = React.useState(true);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);

    const handleRequestSort = (
        event: React.MouseEvent<unknown>,
        property: keyof StudentDataForAdmin,
    ) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };

    const handleSelectAllClick = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.checked) {
            const newSelected = students.map((n) => n.studentID);
            setSelected(newSelected);
            return;
        }
        setSelected([]);
    };

    const handleClick = (event: React.MouseEvent<unknown>, id: number) => {
        const selectedIndex = selected.indexOf(id);
        let newSelected: readonly number[] = [];

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, id);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1));
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selected.slice(0, selectedIndex),
                selected.slice(selectedIndex + 1),
            );
        }
        setSelected(newSelected);
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleChangeDense = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDense(event.target.checked);
    };

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows =
        page > 0 ? Math.max(0, (1 + page) * rowsPerPage - students.length) : 0;

    const visibleRows = React.useMemo(
        () =>
            [...students]
                .sort(getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage),
        [order, orderBy, page, rowsPerPage, students],
    );

    const handleJoinStudentByExcel = () => {

    }

    return (
        <Box sx={{width: '100%'}}>
            <Paper sx={{width: '100%', mb: 2}}>
                <EnhancedTableToolbar numSelected={selected.length} selectedIDs={selected}
                                      fetchMembersData={fetchMembersData}/>
                <TableContainer>
                    <Table
                        sx={{minWidth: 620}}
                        aria-labelledby="tableTitle"
                        size={dense ? 'small' : 'medium'}
                    >
                        <EnhancedTableHead
                            numSelected={selected.length}
                            order={order}
                            orderBy={orderBy}
                            onSelectAllClick={handleSelectAllClick}
                            onRequestSort={handleRequestSort}
                            rowCount={students.length}
                        />
                        <TableBody>
                            {visibleRows.map((row, index) => {
                                const isItemSelected = selected.includes(row.studentID);
                                const labelId = `enhanced-table-checkbox-${index}`;

                                return (
                                    <TableRow
                                        hover
                                        onClick={(event) => handleClick(event, row.studentID)}
                                        role="checkbox"
                                        aria-checked={isItemSelected}
                                        tabIndex={-1}
                                        key={row.studentID}
                                        selected={isItemSelected}
                                        sx={{cursor: 'pointer'}}
                                    >
                                        <TableCell padding="checkbox">
                                            <Checkbox
                                                color="primary"
                                                checked={isItemSelected}
                                                inputProps={{
                                                    'aria-labelledby': labelId,
                                                }}
                                            />
                                        </TableCell>
                                        <TableCell align="right">{row.studentID}</TableCell>
                                        <TableCell align="left">{row.depName}</TableCell>
                                        <TableCell align="left">{row.grade}</TableCell>
                                        <TableCell
                                            component="th"
                                            id={labelId}
                                            scope="row"
                                            align="left"
                                        >
                                            {row.studentName}
                                        </TableCell>
                                        <TableCell align="left">{row.birth}</TableCell>
                                        <TableCell align="left">{row.lastLogin}</TableCell>
                                        <TableCell align="left">{row.isQuit}</TableCell>
                                        <TableCell align="left">{row.role}</TableCell>
                                        <TableCell align="left">
                                            <IconButton onClick={(event) => {
                                                event.stopPropagation(); // 행 클릭 방지
                                                setEditStudentInfo(row);
                                            }}><EditIcon/></IconButton>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                            {emptyRows > 0 && (
                                <TableRow
                                    style={{
                                        height: (dense ? 33 : 53) * emptyRows,
                                    }}
                                >
                                    <TableCell colSpan={6}/>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={students.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                />
            </Paper>
            <Box sx={{display: "flex", justifyContent: "space-between"}}>
                <FormControlLabel
                    control={<Switch checked={dense} onChange={handleChangeDense}/>}
                    label="촘촘히 보기"
                />
                <FormControlLabel sx={{m: 0}} control={<ExcelUpload fetchMembersData={fetchMembersData}/>} label={""}/>
            </Box>
        </Box>
    );
}

// 학생 정보 Redux로 관리하는게 더 깔끔할 듯
export default function StudentManagement() {
    const [students, setStudents] = useState<StudentDataForAdmin[]>([]); // 학생 데이터를 저장할 상태 변수
    const [editStudentInfo, setEditStudentInfo] = useState<StudentDataForAdmin>();

    useEffect(() => {
        fetchMembersData();
    }, []);

    const fetchMembersData = async () => {
        try {
            const response = await secInstance.get("api/members");
            setStudents(response.data);
        } catch (error) {
            console.log("데이터를 가져오는 중 오류가 발생했습니다.", error)
        }
    };

    return (
        <Box pl={2} pr={2} pb={2}>
            <Grid container spacing={2}>
                <Grid size={editStudentInfo ? 9 : 12}>
                    <StudentTable students={students} fetchMembersData={fetchMembersData}
                                  setEditStudentInfo={setEditStudentInfo}/>
                </Grid>
                {editStudentInfo &&
                    (<Grid size={3}>
                            <EditStudentInfo fetchMembersData={fetchMembersData} editStudentInfo={editStudentInfo}
                                             setEditStudentInfo={setEditStudentInfo}/>
                        </Grid>
                    )}
            </Grid>
        </Box>
    );
}