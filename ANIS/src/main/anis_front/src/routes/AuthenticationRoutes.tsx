import React from "react";
import {Autocomplete} from "@mui/material";
import TextField from "@mui/material/TextField";
import {DirectInputLogin} from "../components/DirectInputLogin";
const AuthenticationRoutes = {
        path: '/login',
        children: [
            {
                path: 'qr-code',
                element: <div>QR Code</div>
            },
            {
                path: 'direct-input', //모바일 로그인 직접입력
                element:
                // TODO 확인버튼 클릭시 로그인되게 만들기(onConfirm 구현하기)
                //      페이지틀 통일화시키기
                    <>
                        <DirectInputLogin/>
                    </>
            }
        ]
    }
;

export default AuthenticationRoutes;