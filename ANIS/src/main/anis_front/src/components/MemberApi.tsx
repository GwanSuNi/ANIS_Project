import axios from 'axios';
import {useEffect, useState} from 'react';

export interface Student {
    studentID: string;
    studentName: string;
    departmentName: string;
    birth: string;
}

const fetchMembers = async (studentID: string,studentName:string,departmentName:string,birth:string): Promise<Student[]> =>  {
    try {
        const response = await axios.get('http://localhost:8080/api/members/search', {
            params: {
                studentID,
                studentName,
                departmentName,
                birth
            }
        });

        if (response.status === 200) {
            return response.data.map((item: any): Student => ({
                studentID: item.studentID,
                studentName: item.studentName,
                departmentName: item.departmentName,
                birth: item.birth
            }));
        } else {
            console.error('Error fetching members:', response.status, response.statusText);
            return [];
        }
    } catch (error) {
        console.error('Error fetching members:', error);
        return [];
    }
};

const useStudentSearch = () => {
    const [studentList, setStudentList] = useState<Student[]>([]);
    const [studentID, setStudentID] = useState('');
    const [studentName, setStudentName] = useState('');
    const [departmentName, setDepartmentName] = useState('');
    const [birth, setBirth] = useState('');

    useEffect(() => {
        const fetchAndSetStudents = async () => {
            return await fetchMembers(studentID, studentName, departmentName, birth); // students 반환
        };
        fetchAndSetStudents().then(students => setStudentList(students)); // 반환된 students를 사용하여 studentList 상태를 업데이트
    }, [studentID, studentName, departmentName, birth]);
    return { studentID, setStudentID, studentName, setStudentName, departmentName, setDepartmentName, birth, setBirth, studentList };
};
export {useStudentSearch}