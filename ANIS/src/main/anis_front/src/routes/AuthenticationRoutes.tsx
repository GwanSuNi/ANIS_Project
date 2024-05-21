import QRLogin from "../components/QRLogin";
import SelfLogin from "../components/SelfLogin";
import SignUp from "../components/SignUp";
import {DirectInputLogin} from "../components/DirectInputLogin";

const AuthenticationRoutes = [
    {
        path: '/login',
        children: [
            {
                path: '',
                element: <QRLogin/>
            },
            {
                path: 'self',
                element: <SelfLogin/>
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
    },
    {
        path: 'signup',
        element: <SignUp/>
    }
];

export default AuthenticationRoutes;