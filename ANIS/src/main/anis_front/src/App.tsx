import './App.css';

import React, { useEffect, useState } from "react";
import axios, {AxiosResponse} from "axios";
import JoinComponent from "./components/JoinComponent";
import LoginComponent from "./components/LoginComponent";
import SecuredAPITest from "./components/SecuredAPITest";

function App() {
  const [hello, setHello] = useState<string>('');


// // 토큰을 가져옵니다. 이 예제에서는 localStorage에서 토큰을 가져옵니다.
//     const token = localStorage.getItem('access');
//
// // Axios 인스턴스를 생성합니다.
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
        {/*백엔드 데이터 : {hello}*/}
        <JoinComponent/>
          <hr/>
          <LoginComponent/>
          <hr/>
          <SecuredAPITest/>
      </div>
  );
}

export default App;