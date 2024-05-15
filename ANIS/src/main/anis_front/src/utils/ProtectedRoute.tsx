import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

interface RootState {
    auth: {
        isLoggedIn: boolean;
    };
}

interface ProtectedRouteProps {
    element: React.ReactElement;
    rest?: any;
}

function ProtectedRoute({ element, ...rest }: ProtectedRouteProps): React.ReactElement {
    const isLoggedIn = useSelector((state: RootState) => state.auth.isLoggedIn);

    return isLoggedIn ? element : <Navigate to="/login" />;
}

export default ProtectedRoute;