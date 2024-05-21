import {LectureApplication} from "../components/LectureApplication";
import {FriendListView, StudentCheckList, StudentItemList, StudentListAndDialog} from "../components/FriendList";
import {SurveyListPage} from "@pages";
import {MainLayout} from "@layout";

// TODO 학생 리스트 Repository, Service 만들어서 서버에있는 값 반환하게 하기
const studentList = [
    {
        studentID: "19831033",
        studentName: "김막순",
        departmentName: "컴퓨터공학",
        birth: "1967-05-15",
    },
    {
        studentID: "19831034",
        studentName: "최덕자",
        departmentName: "정보공학시스템",
        birth: "1959-10-25",
    },
    {
        studentID: "19831029",
        studentName: "김덕광",
        departmentName: "컴퓨터소프트웨어",
        birth: "1964-03-07",
    },
    {
        studentID: "19831022",
        studentName: "노춘섭",
        departmentName: "시스템케어",
        birth: "1960-12-12",
    }
];
const MainRoutes = [
    {
        path: '/',
        element: <MainLayout/>,
        children: [
            {
                path: '/',
                // element: <ProtectedRoute element={<div>Main Page</div>}/>
                element: <div>Main Page</div>
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
                            <FriendListView items={studentList}/>
                    },
                    {
                        path: 'add', // 친구 추가하기 페이지
                        element:
                        // TODO items에 모든 studentList 받아오기
                        //      확인 버튼 클릭시에 친구 추가되는 메서드 만들기
                            <StudentListAndDialog
                                ListComponent={(listProps) =>
                                    <StudentItemList
                                        // axios로 내가 받아온 데이터를 보여주기
                                        items={studentList}
                                        {...listProps}
                                    />
                                }
                                // 모달안에 들어가는 텍스트를 바꾸기
                                dialogTitle="친구로 추가하시겠습니까?"
                                // "예" 를 클릭했을때 작동하는 메서드 넣기
                                onConfirm={() => {
                                    // 여기에 onConfirm 이벤트 핸들러를 작성하기
                                    console.log("친구 추가하기 메서드만들기");
                                }}
                            />
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
                    <StudentCheckList items={studentList}></StudentCheckList>
            },
            {
                //TODO 시간표 보여주기
                path: 'lectureCopy', // 친구 수강신청 따라하기
                element:
                    <StudentListAndDialog
                        ListComponent={(listProps) =>
                            <StudentItemList
                                // axios로 내가 받아온 데이터를 보여주기
                                items={studentList}
                                {...listProps}
                            />
                        }
                        // 모달안에 들어가는 텍스트를 바꾸기
                        dialogTitle="헤딩 친구의 시간표대로 수강신청 할까요?"
                        // "예" 를 클릭했을때 작동하는 메서드 넣기
                        onConfirm={() => {
                            // 여기에 onConfirm 이벤트 핸들러를 작성하기
                            console.log("친구 추가하기 메서드만들기");
                        }}
                    />
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