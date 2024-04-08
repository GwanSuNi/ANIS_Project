import {Route, Routes} from "react-router-dom";

function Routers() {
    return (
        <Routes>
            <Route path="/" element={}/>
            <Route path="/login" element={}>
                <Route path="/manual" element={}/>
            </Route>
            <Route path="/friend" element={}>
                <Route path="/add" element={}/>
            </Route>
            <Route path="/lecture/application" element={}/>
            <Route path="/survey" element={}/>
            <Route path="/mypage" element={}/>
            <Route path="/admin" element={}>
                <Route path="/dash" element={}/>
                <Route path="/registration" element={}/>
                <Route path="/survey" element={}/>
                <Route path="/assessment" element={}/>
            </Route>
            <Route path="*" element={<div>404 NotFound</div>}/>
        </Routes>
    );
}

export default Routers;