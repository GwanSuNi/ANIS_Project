import {createBrowserHistory} from 'history';

// 리액트 라우터의 useHistory 훅은 리액트 컴포넌트 내부에서만 사용할 수 있음
// 리액트 컴포넌트 외부에서 라우팅을 처리해야 하는 경우에는 history 라이브러리의 createBrowserHistory 함수를 사용해야 함
export default createBrowserHistory();