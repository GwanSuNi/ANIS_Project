import axios from "axios";
import {getAccessToken, removeAccessToken} from './authUtils';

const logoutInstance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 1000,
    withCredentials: true, // 쿠키를 전송받기 위해 필요
});

logoutInstance.interceptors.request.use(config => {
    config.headers['access'] = getAccessToken();
    return config;
});

logoutInstance.interceptors.response.use(response => {
    if (response.status === 200) {
        removeAccessToken();
        return response;
    }
    return response;
}, error => {
    console.error('Error:', error);
    return Promise.reject(error);
})

export default logoutInstance;