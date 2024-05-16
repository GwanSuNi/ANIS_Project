import axios from "axios";
import {getAccessToken, setAccessToken} from "./authUtils";

// Axios 인스턴스 생성
const secInstance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 1000,
    withCredentials: true, // 쿠키를 전송받기 위해 필요
});

// 요청 인터셉터 설정
secInstance.interceptors.request.use(config => {
    // /api/token/reissue 요청이 아닐 때만 access 토큰을 헤더에 포함
    if (config.url !== '/api/token/reissue') {
        config.headers['access'] = getAccessToken();
    }
    return config;
});

// 응답 인터셉터 설정
secInstance.interceptors.response.use(
    response => {
        if (response.status === 200) {
            return response;
        }
        return response;
    },
    async error => {
        if (error.response && error.response.status === 401) {
            const errorMessage = error.response.data;
            if (errorMessage === "access token expired") {
                console.log('Access token expired!');
                console.log('header에 담아보낸 access토큰:', getAccessToken());


                const res = await secInstance.post('/api/token/reissue');
                let newAccessToken = res.headers["access"].trim();
                console.log('새로 발급받은 access토큰:', newAccessToken);
                setAccessToken(newAccessToken);

                return secInstance(error.config);

            } else {
                console.log('401 발생!');
                // "access token expired" 메시지가 아닌 다른 401 에러에 대한 처리를 여기서 수행합니다.
            }
        } else if (error.response && error.response.status === 400) {
            const errorMessage = error.response.data;
            if (errorMessage === "refresh token expired") {
                console.log('refresh token 만료');
                // 리프레시 토큰이 만료되었을 때의 처리
                alert("다시 로그인 해주세요.")
                // 리프레시 토큰 만료 시 로그인 페이지로 이동
                window.location.href = '/';
            }
        } else if (error.response.status === 403) {
            // Forbidden
            // 사용자에게 권한이 없음을 알리는 메시지 표시 등의 작업 수행
            console.log('403 발생!');
        } else if (error.response.status === 500) {
            // Internal Server Error
            // 일시적인 서버 오류 메시지를 표시하는 등의 작업 수행
        }

        return Promise.reject(error);
    }
);

export default secInstance;