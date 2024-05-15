import {createTheme} from '@mui/material';
import typography from './typography';

const theme = () => {
    return createTheme({
        typography: typography()
    });
};

export default theme;