import './App.css';

import {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import Routes from "./routes"; // Node.js와 TypeScript는 import문에서 디렉토리를 지정하면 해당 디렉토리의 index 파일을 불러옴

function App() {
    const [hello, setHello] = useState<string>('');


// 토큰을 가져옵니다. 이 예제에서는 localStorage에서 토큰을 가져옵니다.
    const token = localStorage.getItem('access');

// Axios 인스턴스를 생성합니다.
//     const instance = axios.create({
//         baseURL: '/api',
//         timeout: 1000,
//         headers: {'Authorization': `Bearer ${token}`}
//     });
//
//     // 이제 이 인스턴스를 사용하여 요청을 보낼 수 있습니다.
//     instance.get('/test')
//         .then(response => {
//             console.log(response.data);
//         });

    // useEffect(() => {
    //   axios.get('/api/test')
    //       .then((res: AxiosResponse<string>) => {
    //         setHello(res.data);
    //       });
    // }, []);

    return (
        <div className="App">
            {/*{token}*/}
            <Routes/>
        </div>
    );
}

export default App;