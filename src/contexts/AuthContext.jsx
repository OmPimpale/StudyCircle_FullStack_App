import React, { createContext, useState, useEffect, useContext } from 'react';
import authenticatedFetch from '../utils/authenticatedFetch'; // Adjust path if needed
import Spinner from '../components/Spinner';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');

        const fetchUser = async () => {
            try {
                const response = await authenticatedFetch('/api/users/me'); // Adjust endpoint
                if (response.ok) {
                    const userData = await response.json();
                    setCurrentUser(userData);
                } else {
                    localStorage.removeItem('jwtToken'); // Token might be invalid
                    setCurrentUser(null);
                }
            } catch (error) {
                console.error('Error fetching user:', error);
                localStorage.removeItem('jwtToken');
                setCurrentUser(null);
            } finally {
                setLoading(false);
            }
        };

        if (token) {
            fetchUser();
        } else {
            setLoading(false);
        }
    }, []);

    // Optional login and logout handlers
    const login = (userData, token) => {
        localStorage.setItem('jwtToken', token);
        setCurrentUser(userData);
    };

    const logout = () => {
        localStorage.removeItem('jwtToken');
        setCurrentUser(null);
    };

    const value = {
        currentUser,
        loading,
        login,
        logout
    }

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;