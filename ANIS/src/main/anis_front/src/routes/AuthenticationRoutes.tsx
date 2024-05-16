import {StudentListAndDialog,StudentItemList} from "../components/FriendList";
import React from "react";

const studentList = [
    {
        studentID: '20244013',
        studentName: '유관돌',
        department: '라이프케어과',
        birth: '1960-04-23'
    },
    {
        studentID: '20244009',
        studentName: '서상철',
        department: '자율전공학부',
        birth: '1958-06-23'
    },
];

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
            // TODO 통합검색필드로 검색한데이터 items 안에넣기
            //      확인버튼 클릭시 로그인되게 만들기(onConfirm 구현하기)
            //      페이지틀 통일화시키기
            <StudentListAndDialog
                ListComponent={(listProps) =>
                    <StudentItemList
                        // axios로 내가 받아온 데이터를 보여주기
                        items={studentList}
                        {...listProps}
                    />
                }
                // 모달안에 들어가는 텍스트를 바꾸기
                dialogTitle="정말로 본인이 맞습니까?"
                // "예" 를 클릭했을때 작동하는 메서드 넣기
                onConfirm={() => {
                    // 여기에 onConfirm 이벤트 핸들러를 작성하기
                    console.log("다이얼로그의 '예' 버튼이 클릭되었습니다.");
                }}
            />
        }
    ]
};

export default AuthenticationRoutes;