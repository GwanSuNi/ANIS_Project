import {MouseEvent, useState} from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Copyright from "./Copyright";
import DepartmentSelect from "./DepartmentSelect";
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import FormLabel from '@mui/material/FormLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import {Divider} from "@mui/material";
import {useJoin} from "@hooks";

export default function SignUp() {
    const {username, password, studentName, departmentId, birth, role, setUsername, setPassword, setStudentName, setDepartmentId, setBirth, setRole, handleSubmit} = useJoin();
    const [showPassword, setShowPassword] = useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                    <LockOutlinedIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    회원가입
                </Typography>
                <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                    <Divider/>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <FormControl>
                                <FormLabel id="demo-radio-buttons-group-label" sx={{mt:2}}>분류</FormLabel>
                                <RadioGroup
                                    row
                                    aria-labelledby="demo-radio-buttons-group-label"
                                    defaultValue="MEMBER"
                                    name="radio-buttons-group"
                                    value={role}
                                    onChange={e => setRole(e.target.value)}
                                >
                                    <FormControlLabel value="MEMBER" control={<Radio />} label="학생" />
                                    <FormControlLabel value="ADMIN" control={<Radio />} label="관리자" />
                                </RadioGroup>
                            </FormControl>
                        </Grid>

                        <Grid item xs={12} sm={6}>
                            <DepartmentSelect selectedDepIndex={departmentId} setSelectedDepIndex={setDepartmentId}/>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                autoComplete="given-name"
                                name="name"
                                required
                                fullWidth
                                id="name"
                                value={studentName}
                                onChange={e => setStudentName(e.target.value)}
                                label="이름"
                                autoFocus
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                autoComplete="given-studentID"
                                name="username"
                                required
                                fullWidth
                                id="username"
                                value={username}
                                onChange={e => setUsername(e.target.value)}
                                label="학번"
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                autoComplete="given-birth"
                                name="birth"
                                required
                                fullWidth
                                id="birth"
                                value={birth}
                                type={"number"}
                                onChange={e => setBirth(e.target.value)}
                                label="생년월일(8자리)"
                                placeholder={"YYYYMMDD"}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl fullWidth variant="outlined">
                                <InputLabel htmlFor="outlined-adornment-password">비밀번호</InputLabel>
                                <OutlinedInput
                                    id="outlined-adornment-password"
                                    type={showPassword ? 'text' : 'password'}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={handleClickShowPassword}
                                                onMouseDown={handleMouseDownPassword}
                                                edge="end"
                                            >
                                                {showPassword ? <VisibilityOff /> : <Visibility />}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Password"
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                />
                            </FormControl>
                        </Grid>

                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        회원가입
                    </Button>
                    <Grid container justifyContent="flex-end">
                        <Grid item>
                            <Link href="#" variant="body2">
                                계정이 있으신가요?
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            <Copyright sx={{mt: 5}}/>
        </Container>
    );
}