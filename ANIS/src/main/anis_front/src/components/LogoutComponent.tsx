import React from "react";
import logoutInstance from "../utils/logoutInstance";
import {useDispatch, useSelector} from "react-redux";
import {logoutSuccess} from "../redux/authSlice";
import {RootState} from "../redux/store";

export default function LogoutComponent() {
    const dispatch = useDispatch();
    const isLoggedIn = useSelector((state:RootState) => state.auth.isLoggedIn);
    const handleLogout = async (event: React.MouseEvent) => {
        event.preventDefault();

        try {
            const response = await logoutInstance.post('/logout');
            if (response.status === 200) {
                sessionStorage.removeItem('access');
                dispatch(logoutSuccess());
                alert('로그아웃 되었습니다.');
                window.location.href = '/';
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };
    return (
        <div>
            { isLoggedIn && <button onClick={handleLogout}>로그아웃</button>}
        </div>
    );
}