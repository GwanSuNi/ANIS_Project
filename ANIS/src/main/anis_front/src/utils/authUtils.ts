export function getAccessToken() {
    return sessionStorage.getItem('access');
}

export function setAccessToken(token: string) {
    sessionStorage.setItem('access', token);
}

export function removeAccessToken() {
    sessionStorage.removeItem('access');
}

export function isLoggedIn() {
    return !!getAccessToken(); // !!를 사용하여 Access 토큰이 존재하면 true, 아니면 false 반환
}