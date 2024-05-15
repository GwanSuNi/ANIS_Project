import './App.css';
import Routes from "./routes"; // Node.js와 TypeScript는 import문에서 디렉토리를 지정하면 해당 디렉토리의 index 파일을 불러옴
function App() {
    // const token = localStorage.getItem('access');
    //
    // const [lectures, setLectures] = useState(null);
    //
    // const fetchTodos = async () => {
    //     const { data } = await axios.get("http://localhost:8080/lecture/getMyLectureList");
    //     setLectures(data); // 서버로부터 fetching한 데이터를 useState의 state로 set 합니다.
    // };
    //
    // // 생성한 함수를 컴포넌트가 mount 됐을 떄 실행하기 위해 useEffect를 사용합니다.
    // useEffect(() => {
    //     // effect 구문에 생성한 함수를 넣어 실행합니다.
    //     fetchTodos();
    // }, []);
    // <LectureTest items={lectures || []}/>
//     const [hello, setHello] = useState<string>('');
// 토큰을 가져옵니다. 이 예제에서는 localStorage에서 토큰을 가져옵니다.


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
// /   useEffect(() => {
//     axios.get('/api/test')
//         .then((res: AxiosResponse<string>) => {
//           setHello(res.data);
//         });
//   }, []);

  return (
      <div className="App">
          <Routes/>
    </div>
  );
}

export default App;