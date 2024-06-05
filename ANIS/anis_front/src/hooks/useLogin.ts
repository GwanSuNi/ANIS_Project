import {FormEvent, useState} from 'react';
import {useSelector} from 'react-redux';
import {loginInstance} from '@utils';
import {useNavigate} from 'react-router-dom';
import {RootState} from '@redux';

export function useLogin() {
    const username = useSelector((state: RootState) => state.username.username);
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();
        // console.log('useLogin의 ', username);
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

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