// useFirebase.ts
import { useEffect, useState } from 'react';
import { messaging, getToken } from '../firebase-config';
import * as process from "process";

export function useFirebase() {
    const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
        const fetchToken = async () => {
            try {
                const token = await getToken(messaging, {
                    vapidKey: process.env.REACT_APP_VAPID_KEY as string
                });

                if (token) {
                    setToken(token);
                    console.log('FCM 토큰:', token);
                } else {
                    console.log('FCM 토큰을 받을 수 없습니다.');
                }
            } catch (error) {
                console.error('FCM 토큰 요청 중 오류 발생:', error);
            }
        };

        fetchToken();
    }, []);

    return token;
}