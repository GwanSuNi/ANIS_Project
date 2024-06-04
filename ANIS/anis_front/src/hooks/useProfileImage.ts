// useProfileImage.ts
import {useQuery, useSuspenseQuery} from '@tanstack/react-query';
import secInstance from '../utils/secInstance';
import {AxiosResponse} from "axios";

    // Skeleton 테스트를 위해 고의적 딜레이
// function delay(t: number, v:AxiosResponse) {
//     return new Promise<AxiosResponse>(function(resolve) {
//         setTimeout(resolve.bind(null, v), t)
//     });
// }

const fetchProfileImage = async (studentID: string) => {
    // const imageResponse: AxiosResponse = await secInstance.get(`/api/image/download/${studentID}`, {responseType: 'arraybuffer'}).then(res => delay(30000, res));
    const imageResponse: AxiosResponse = await secInstance.get(`/api/image/download/${studentID}`, {responseType: 'arraybuffer'});
    const base64 = btoa(
        new Uint8Array(imageResponse.data).reduce(
            (data, byte) => data + String.fromCharCode(byte),
            '',
        ),
    );
    return `data:image/png;base64,${base64}`;
};

const useProfileImage = (studentID: string) => {
    return useQuery({
        queryKey: ['profileImage', studentID],
        queryFn: () => fetchProfileImage(studentID as string),
        staleTime: 5 * 60 * 1000, // 데이터가 신선한 상태로 유지되는 시간 (5분)
        gcTime: 10 * 60 * 1000 // 데이터가 캐시에 남아있는 시간 (10분) // v5는 cacheTime에서 이름이 변경됨
    });
};

export default useProfileImage;