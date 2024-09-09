import {Suspense} from 'react';
import {EnrolmentTogether, FriendAdd, FriendListView, LectureApplication, LectureCopy} from '@components';
import {SurveyListPage} from '@pages';
import {MainLayout} from '@layout';
import Main from "../pages/Main";
import {ProtectedRoute} from "@utils";
import MyInfo from "../components/myInfo/MyInfo";
import {Provider} from "react-redux";
import store from "../redux/store";
const MainRoutes = [
    {
        path: '/',
        element: <MainLayout/>,
        children: [
            {
                path: '/',
                element: <ProtectedRoute element={<Main/>}/>
            },
            {
                path: 'myInfo',
                element: <MyInfo/>
            },
            {
                path: 'friend',
                children: [
                    {
                        path: '',
                        element:
                        <FriendListView/>
                    },
                    {
                        path: 'add', // 친구 추가하기 페이지
                        element:
                        <FriendAdd/>
                    }
                ]
            },
            {
                path: 'lecture',
                element:
                    <Provider store={store}>
                        <LectureApplication />
                    </Provider>,
            },
            {
                //TODO 설명 넣어주기 , 예 아니오 버튼 만들기
                path: 'enrolmentTogether', // 친구 수강신청 함께하기
                element:
                <EnrolmentTogether/>
            },
            {
                //TODO 시간표 보여주기
                path: 'lectureCopy', // 친구 수강신청 따라하기
                element:
                <LectureCopy/>
            },
            {
                path: 'survey',
                element: (
                    <Suspense fallback={<div>Loading...</div>}>
                        <SurveyListPage/>
                    </Suspense>
                )
            },
            {
                path: 'mypage',
                element: <div>MyPage</div>
            },
        ]
    },
    {
        path: '*',
        element: <div>Not Found</div>
    }
];

export default MainRoutes;