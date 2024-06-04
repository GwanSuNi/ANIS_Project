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
                path: 'select',
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