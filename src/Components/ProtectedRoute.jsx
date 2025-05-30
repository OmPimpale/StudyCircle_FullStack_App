jsx
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
    const isAuthenticated = localStorage.getItem('jwtToken');

    if (isAuthenticated) {
        return children;
    } else {
        return <Navigate to="/login" replace />;
    }
};

export default ProtectedRoute;