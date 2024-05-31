import '@assets/fonts/Font.css'
import {createTheme} from '@mui/material';
import typography from './typography';
import palette from './palette';

const theme = createTheme({
    typography: typography,
    palette: palette
});

export default theme;