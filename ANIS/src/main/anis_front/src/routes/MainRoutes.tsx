import {Outlet} from "react-router-dom";
import {SurveyListPage} from "../pages";

const MainRoutes = [
    {
        path: '/',
        element: <div>MainLayout <Outlet/></div>,
        children: [
            {
                path: '/',
                element: <div>Main Page</div>
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
                element: <SurveyListPage/>
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