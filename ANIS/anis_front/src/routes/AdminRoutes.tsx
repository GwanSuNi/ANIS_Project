import {Outlet} from "react-router-dom";

const AdminRoutes = {
    path: '/admin',
    element: <div>AdminLayout <Outlet/></div>,
    children: [
        {
            path: '/admin',
            element: <div>Admin Page</div>,
        },
        {
            path: 'dashboard',
            element: <div>Dashboard</div>
        },
        {
            path: 'registration',
            element: <div>Registration</div>
        },
        {
            path: 'survey',
            element: <div>Survey</div>
        },
        {
            path: 'assessment',
            element: <div>Assessment</div>
        }
    ]
};

export default AdminRoutes;