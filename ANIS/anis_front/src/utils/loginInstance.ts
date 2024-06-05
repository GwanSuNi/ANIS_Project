import axios from 'axios';
import {getAccessToken, setAccessToken} from './authUtils';

// Axios 인스턴스 생성
const loginInstance = axios.create({
    // baseURL: 'https://192.168.0.3:8080',
    baseURL: 'http://localhost:8080',
    // baseURL: 'https://kingfish-sound-goshawk.ngrok-free.app',
    timeout: 1000,
    withCredentials: true, // 쿠키를 전송받기 위해 필요
});

// 응답 인터셉터 설정
loginInstance.interceptors.response.use(
    response => {
        if (response.status === 200) {
            let accessToken = response.headers["access"].trim(); // 헤더에서 access 토큰 가져오기
            // console.log('Access Token:', accessToken);
            // Access 토큰을 메모리에 저장
            setAccessToken(accessToken);
            // 메모리에 있는 ACCESS 토큰 출력
            // console.log('sessionStorage:', getAccessToken());
            // Refresh 토큰은 서버에서 HTTP Only 쿠키로 설정되어 클라이언트에서 직접 접근할 수 없습니다.
            // alert('로그인 성공');
        }
        return response;
    },
    async error => {
        if (error.response.status === 401) { // Unauthorized
            console.log('401 발생!');
            try {
                // 토큰 재발급 API 호출
                const res = await loginInstance.post('/api/token/reissue');

                // 새로 발급받은 토큰을 세션 스토리지에 저장
                sessionStorage.setItem('access', res.headers["access"].trim());

                // 실패한 요청을 다시 실행
                return loginInstance(error.config);
            } catch (err) {
                console.error(err);
                // 토큰 재발급도 실패했을 경우 로그인 페이지로 리다이렉트 등의 처리를 할 수 있습니다.
            }
        } else if (error.response.status === 403) { // Forbidden
            // 사용자에게 권한이 없음을 알리는 메세지 표시 등의 작업 수행
        } else if (error.response.status === 500) {
            // 일시적인 서버 오류 메세지를 표시하는 등의 작업 수행
        }
        
        return Promise.reject(error);
    }
);

export default loginInstance;