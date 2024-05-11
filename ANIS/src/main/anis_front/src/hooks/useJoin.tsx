import React, { useState } from 'react';
import axios from 'axios';

export function useJoin() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [studentName, setStudentName] = useState('');
    const [departmentId, setDepartmentId] = useState('');
    const [birth, setBirth] = useState('');
    const [role, setRole] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const joinData = {
            username,
            password,
            studentName,
            departmentId,
            birth,
            role
        };
        const axiosInstance = axios.create({
            baseURL: 'http://localhost:8080'
        });

        console.log(joinData);
        try {
            const response = await axiosInstance.post('/join', joinData);
            if (response.status === 200) {
                alert('회원가입 성공');
                setUsername('');
                setPassword('');
                setStudentName('');
                setBirth('');
                setRole('');
            }
        } catch (error) {
            console.error(error);
        }
    };

    return { username, password, studentName, departmentId, birth, role, setUsername, setPassword, setStudentName, setDepartmentId, setBirth, setRole, handleSubmit };
}