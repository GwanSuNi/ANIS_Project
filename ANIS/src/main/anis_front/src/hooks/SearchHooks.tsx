import {useEffect, useState} from "react";
import {fetchFriends, fetchMembers, Student} from "../components/MemberApi";
import exp from "node:constants";

/**
 * @param fetchFunction 친구추가에서 사용할 훅인지, 로그인시에 사용할 훅인지
 */
const useStudentSearchBase = (fetchFunction: (studentID: string, studentName: string, departmentName: string, birth: string) => Promise<Student[]>) => {
    const [studentList, setStudentList] = useState<Student[]>([]);
    const [studentID, setStudentID] = useState('');
    const [studentName, setStudentName] = useState('');
    const [departmentName, setDepartmentName] = useState('');
    const [birth, setBirth] = useState('');

    const fetchAndSetStudents = async () => {
        const students = await fetchFunction(studentID, studentName, departmentName, birth);
        setStudentList(students);
    };

    useEffect(() => {
        fetchAndSetStudents();
    }, [studentID, studentName, departmentName, birth]);

    return { studentID, setStudentID, studentName, setStudentName, departmentName, setDepartmentName, birth, setBirth, studentList,fetchAndSetStudents };
};

/**
 * DirectInputLogin 에서 사용
 * 직접 로그인할때 사용하는 훅스
 */
const useStudentSearch = () => {
    return useStudentSearchBase(fetchMembers);
};

/**
 * FriendAdd 에서 사용
 * 친구찾을때 사용하는 훅스
 */
const useFriendSearch = () => {
    return useStudentSearchBase(fetchFriends);
};
export{useStudentSearch,useFriendSearch}