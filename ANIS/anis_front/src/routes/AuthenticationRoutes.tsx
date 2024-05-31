import {DirectInputLogin, QRLogin, SelfLogin, SignUp} from '@components';

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
                // TODO 확인버튼 클릭시 로그인되게 만들기(onConfirm 구현하기)
                //      페이지틀 통일화시키기
                element: <DirectInputLogin/>
            }
        ]
    },
    {
        path: 'signup',
        element: <SignUp/>
    }
];

export default AuthenticationRoutes;