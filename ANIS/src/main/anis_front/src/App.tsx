import './App.css';
import Routes from "./routes"; // Node.js와 TypeScript는 import문에서 디렉토리를 지정하면 해당 디렉토리의 index 파일을 불러옴
import React, { useState } from "react";
import JoinComponent from "./components/JoinComponent";
import LoginComponent from "./components/LoginComponent";
import SecuredAPITest from "./components/SecuredAPITest";
import LogoutComponent from "./components/LogoutComponent";
import {Provider} from "react-redux";
import store from "./redux/store";
import QRLogin from "./components/QRLogin";
import BarcodeScanner from "./components/QrReader";
import SelfLogin from "./components/SelfLogin";
import SignUp from "./components/SignUp";
import DepartmentSelect from "./components/DepartmentSelect";

function App() {
    const [hello, setHello] = useState<string>('');

// Axios 인스턴스를 생성합니다.
//     const instance = axios.create({
//         baseURL: '/api',
//         timeout: 1000,
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
      // <Provider store={store}>
      // <div className="App">
      //     {/*{token}*/}
      //   {/*백엔드 데이터 : {hello}*/}
      //   <JoinComponent/>
      //     <hr/>
      //     <LoginComponent/>
      //     <hr/>
      //     <SecuredAPITest/>
      //     <hr/>
      //     <LogoutComponent/>
      // </div>
      // </Provider>


      // <BarcodeScanner/>
      // <QRLogin/>
      <Provider store={store}>
        {/*<SelfLogin/>*/}
          <SignUp/>
        {/*  <DepartmentSelect/>*/}
      </Provider>
  );
}

export default App;