import {Outlet} from "react-router-dom";
import QRLogin from "../components/QRLogin";
import SelfLogin from "../components/SelfLogin";
import SignUp from "../components/SignUp";
import React from "react";
import LogoutComponent from "../components/LogoutComponent";
import ProtectedRoute from "../utils/ProtectedRoute";

const MainRoutes = [
    {
        path: '/',
        element: <div>MainLayout <LogoutComponent/><Outlet/></div>,
        children: [
            {
                path: '/',
                element: <ProtectedRoute element={<div>Main Page</div>}/>
            },
            {
                path: 'login',
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
                        element: <div>select login</div>
                    }
                ]
            },
            {
                path: 'signup',
                element: <SignUp/>
            },
            {
                path: 'friend',
                children: [
                    {
                        path: '',
                        element: <div>Friend</div>
                    },
                    {
                        path: 'add',
                        element: <div>Add Friend</div>
                    }
                ]
            },
            {
                path: 'lecture/application',
                element: <div>Lecture Application</div>
            },
            {
                path: 'survey',
                element: <div>Survey</div>
            },
            {
                path: 'mypage',
                element: <div>MyPage</div>
            }
        ]
    },
    {
        path: '*',
        element: <div>Not Found</div>
    }
];

export default MainRoutes;