import {useDispatch, useSelector} from "react-redux";
import {closeSnackbar} from "../../redux/snackbarSlice";
import React from "react";
import {Alert, Snackbar} from "@mui/material";
import {RootState} from "@redux";

export default function SnackbarComponent() {
    const dispatch = useDispatch();
    const {open, message, severity} = useSelector((state: RootState) => state.snackbar);

    const handleClose = () => {
        dispatch(closeSnackbar());
    };

    return (
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}
                  anchorOrigin={{vertical: 'top', horizontal: 'center'}}>
            <Alert onClose={handleClose} severity={severity} sx={{width: '100%'}}>
                {message}
            </Alert>
        </Snackbar>
    );
}