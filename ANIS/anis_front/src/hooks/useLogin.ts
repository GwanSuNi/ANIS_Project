import {FormEvent, useEffect, useState} from 'react';
import {useSelector} from 'react-redux';
import {loginInstance} from '@utils';
import {useNavigate} from 'react-router-dom';
import {RootState} from '@redux';
import {useFirebase} from "./useFirebase";

export function useLogin() {
    const username = useSelector((state: RootState) => state.username.username);
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const firebaseToken = useFirebase();
    const [token, setToken] = useState<string | null>(null);

    // Firebase 토큰이 준비되면 상태 업데이트
    useEffect(() => {
        if (firebaseToken) {
            setToken(firebaseToken);
        }
    }, [firebaseToken]);

    const handleSubmit = async (event?: FormEvent) => {
        if (event) {
            event.preventDefault();
        }
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        if (token) {
            formData.append('fcmToken', token);
        }
        try {
            await loginInstance.post('/api/login', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            navigate('/');
        } catch (error) {
            console.log(username);
            console.error(error);
        }
    };

    return {password, setPassword, handleSubmit};
}