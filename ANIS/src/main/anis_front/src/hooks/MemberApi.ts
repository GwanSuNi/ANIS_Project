import {secInstance} from '@utils';

export interface Student {
    studentID: string;
    studentName: string;
    departmentName: string;
    birth: string;
}

/**
 * 공통된 API 호출 함수
 * @param url 요청할 컨트롤러 url
 * @param params Student[] 반환
 */
const fetchStudents = async (url: string, params?: object): Promise<Student[]> => {
    try {
        const response = await secInstance.get(url, {params});
        if (response.status === 200) {
            return response.data.map((item: any): Student => ({
                studentID: item.studentID,
                studentName: item.studentName,
                departmentName: item.departmentName,
                birth: item.birth
            }));
        } else {
            console.error('Error fetching data:', response.status, response.statusText);
            return [];
        }
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
};
/**
 * 받아온 ID 로 친구 삭제하는 axios 통신
 * @param friendID
 */
const deleteFriend = async (friendID: string) => {
    try {
        const response = await secInstance.patch(`/api/friend/${friendID}`);
        if (response.status === 200) {
            console.log("친구를 삭제했습니다.");
            // 여기서 추가적인 작업을 수행할 수 있습니다.
        } else {
            console.error("친구를 삭제하지 못했습니다.");
        }
    } catch (error) {
        console.error("친구 삭제 중 오류가 발생했습니다.", error);
    }
};

/**
 * 현재 로그인한 회원의 친구 리스트 갖고오기
 */
const fetchFriendList = async (): Promise<Student[]> => {
    return fetchStudents('/api/friendList');
};

/**
 * 로그인하려고하는 회원을 찾는 axios통신
 */
const fetchMembers = async (studentID: string, studentName: string, departmentName: string, birth: string): Promise<Student[]> => {
    return fetchStudents('/api/members/search', {studentID, studentName, departmentName, birth});
};

/**
 * 현재 로그인한 유저의 친구추가할 친구를찾는 axios 통신
 */
const fetchFriends = async (studentID: string, studentName: string, departmentName: string, birth: string): Promise<Student[]> => {
    return fetchStudents('/api/members/friendSearch', {studentID, studentName, departmentName, birth});
};

export {deleteFriend, fetchFriendList, fetchMembers, fetchFriends}