import { useNavigate } from 'react-router-dom';

const useLogout = () => {
    const navigate = useNavigate();

    const logout = () => {
        // Remove the JWT token from localStorage
        localStorage.removeItem('jwtToken');
        // You might also remove other user-related data from localStorage if stored
        // localStorage.removeItem('user');

        // Redirect to the login page
        navigate('/login');
    };

    return logout;
};

export default useLogout;