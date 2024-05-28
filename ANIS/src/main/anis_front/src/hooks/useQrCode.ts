import { useState, useEffect } from 'react';
import secInstance from '../utils/secInstance';

const useQrCode = () => {
    const [qrCode, setQrCode] = useState<string>();

    useEffect(() => {
        const fetchQrCode = async () => {
            try {
                const qrResponse = await secInstance.get('/api/qr', {responseType: 'arraybuffer'});
                const base64 = btoa(
                    new Uint8Array(qrResponse.data).reduce(
                        (data, byte) => data + String.fromCharCode(byte),
                        '',
                    ),
                );
                setQrCode(`data:image/png;base64,${base64}`);
            } catch (error) {
                console.error('QR 코드를 불러오기에 실패했습니다:', error);
            }
        };
        fetchQrCode();
    }, []);

    return qrCode;
};

export default useQrCode;