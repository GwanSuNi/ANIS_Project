import React from "react";
import MyInfo from "../components/MyInfo";
import {EnrolmentTogether, FriendAdd, FriendListView, LectureApplication, LectureCopy} from '@components';
import {SurveyListPage} from '@pages';
import {MainLayout} from '@layout';
import {ProtectedRoute} from "@utils";

const MainRoutes = [
    {
        path: '/',
        element: <MainLayout/>,
        children: [
            {
                path: '/',
                element: <ProtectedRoute element={<div>Main Page</div>}/>
                // element: <div>Main Page <MyInfoBtn/></div>
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
                        // TODO items 에 현재 로그인한 학생의 친구 목록 모두 받아오기
                        //      Dialog 안에 예 버튼 클릭시 친구 취소하는 메서드 만들기
                        //      친구 시간표 보기 메서드 만들기
                        <FriendListView/>
                    },
                    {
                        path: 'add', // 친구 추가하기 페이지
                        element:
                        // TODO items에 모든 studentList 받아오기
                        //      확인 버튼 클릭시에 친구 추가되는 메서드 만들기
                        <FriendAdd/>
                    }
                ]
            },
            {
                path: 'lecture',
                element:
                    <LectureApplication/>,
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
                    // <StudentListAndDialog
                    //     ListComponent={(listProps) =>
                    //         <StudentItemList
                    //             // axios로 내가 받아온 데이터를 보여주기
                    //             items={studentList}
                    //             {...listProps}
                    //         />
                    //     }
                    //     // 모달안에 들어가는 텍스트를 바꾸기
                    //     dialogTitle="헤딩 친구의 시간표대로 수강신청 할까요?"
                    //     // "예" 를 클릭했을때 작동하는 메서드 넣기
                    //     onConfirm={() => {
                    //         // 여기에 onConfirm 이벤트 핸들러를 작성하기
                    //         console.log("친구 추가하기 메서드만들기");
                    //     }}
                    // />
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