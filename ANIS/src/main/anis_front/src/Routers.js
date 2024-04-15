import {Route, Routes} from "react-router-dom";

function Routers() {
    return (
        <Routes>
            <Route path="/" element={<div>Home Page</div>}/>
            <Route path="/login" element={<div>Login Page</div>}>
                <Route path="/manual" element={<div>Manual Page</div>}/>
            </Route>
            <Route path="/friend" element={<div>Friend Page</div>}>
                <Route path="/add" element={<div>Add Friend Page</div>}/>
            </Route>
            <Route path="/lecture/application" element={<div>Lecture Application Page</div>}/>
            <Route path="/survey" element={<div>Survey Page</div>}/>
            <Route path="/mypage" element={<div>My Page</div>}/>
            <Route path="/admin" element={<div>Admin Page</div>}>
                <Route path="/dash" element={<div>Admin Dashboard Page</div>}/>
                <Route path="/registration" element={<div>Admin Registration Page</div>}/>
                <Route path="/survey" element={<div>Admin Survey Page</div>}/>
                <Route path="/assessment" element={<div>Admin Assessment Page</div>}/>
            </Route>
            <Route path="*" element={<div>404 NotFound</div>}/>
        </Routes>
    );
}

export default Routers;