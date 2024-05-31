import React from 'react';
import {Navigate} from 'react-router-dom';
import {isLoggedIn} from "./authUtils";


interface ProtectedRouteProps {
    element: React.ReactElement;
    rest?: any;
}

function ProtectedRoute({element, ...rest}: ProtectedRouteProps): React.ReactElement {
    return isLoggedIn() ? element : <Navigate to='/login'/>;
}

export default ProtectedRoute;