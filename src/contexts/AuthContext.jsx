import React, { createContext, useState, useEffect } from 'react';
// import authenticatedFetch from '../utils/authenticatedFetch'; // Uncomment and adjust path if fetching user details here

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            // You might want to fetch user details from the backend here
            // using authenticatedFetch and setCurrentUser based on the response.
            // For now, we'll just assume the existence of a token means authenticated.
            // A more robust implementation would validate the token and fetch user data.

            // Example (assuming backend has an endpoint to get current user):
            // const fetchUser = async () => {
            //     try {
            //         const response = await authenticatedFetch('/api/users/me');
            //         if (response.ok) {
            //             const userData = await response.json();
            //             setCurrentUser(userData);
            //         } else {
            //             // Handle error, maybe token is invalid/expired
            //             localStorage.removeItem('jwtToken');
            //         }
            //     } catch (error) {
            //         console.error('Error fetching user:', error);
            //         localStorage.removeItem('jwtToken'); // Remove token on fetch error
            //     } finally {
            //         setLoading(false);
            //     }
            // };
            // fetchUser();

            // Basic implementation assuming token existence is enough for initial load:
            setCurrentUser({}); // Set a placeholder user or infer from token if possible
            setLoading(false);

        } else {
            setLoading(false);
        }
    }, []);

    // You might add login and logout functions here to update the user state and localStorage

    return (
        <AuthContext.Provider value={{ currentUser, loading, setCurrentUser }}>
            {!loading && children}
            {loading && <div>Loading authentication...</div>} {/* Optional loading indicator */}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};

import { useContext } from 'react'; // Import useContext here for useAuth