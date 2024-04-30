import React, { useState } from 'react';
import axios from 'axios';

const JoinComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [studentName, setStudentName] = useState('');
    const [birth, setBirth] = useState(0);
    const [role, setRole] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const joinData = {
            username,
            password,
            studentName,
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

            }
        } catch (error) {
            console.error(error);
        }

    };

    return (
        <form onSubmit={handleSubmit} encType={"multipart/form-data"}>
            <label>
                Username:
                <input type="text" value={username} onChange={e => setUsername(e.target.value)} />
            </label>
            <br/>
            <label>
                Password:
                <input type="password" value={password} onChange={e => setPassword(e.target.value)} />
            </label>
            <br/>
            <label>
                Student Name:
                <input type="text" value={studentName} onChange={e => setStudentName(e.target.value)} />
            </label>
            <br/>
            <label>
                Birth:
                <input type="number" value={birth} onChange={e => setBirth(Number(e.target.value))} />
            </label>
            <br/>
            <label>
                Role:
                <input type="text" value={role} onChange={e => setRole(e.target.value)} />
            </label>
            <br/>
            <input type="submit" value="Submit" />
        </form>
    );
};

export default JoinComponent;