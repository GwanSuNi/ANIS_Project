import secInstance from '../utils/secInstance';
import {useQuery} from "@tanstack/react-query";

const fetchQrCode = async () => {
    try {
        const qrResponse = await secInstance.get('/api/qr', {responseType: 'arraybuffer'});
        const base64 = btoa(
            new Uint8Array(qrResponse.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                '',
            ),
        );
        return `data:image/png;base64,${base64}`;
    } catch (error) {
        console.error('QR 코드를 불러오기에 실패했습니다:', error);
    }
};

const useQrCode = (studentID: string) => {
    return useQuery({
        queryKey: ['qrCode', studentID],
        queryFn: fetchQrCode,
        staleTime: 5 * 60 * 1000, // 데이터가 신선한 상태로 유지되는 시간 (5분)
        gcTime: 10 * 60 * 1000 // 데이터가 캐시에 남아있는 시간 (10분) // v5는 cacheTime에서 이름이 변경됨
    });
};

export default useQrCode;