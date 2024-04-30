const AuthenticationRoutes = {
    path: '/login',
    children: [
        {
            path: 'qr-code',
            element: <div>QR Code</div>
        },
        {
            path: 'manual',
            element: <div>Manual</div>
        }
    ]
};

export default AuthenticationRoutes;