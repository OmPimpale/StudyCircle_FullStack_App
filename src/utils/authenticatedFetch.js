const authenticatedFetch = async (url, options = {}) => {
    const token = localStorage.getItem('jwtToken');

    const headers = {
        ...options.headers,
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, {
        ...options,
        headers,
    });

    if (response.status === 401) {
        // Handle unauthorized: remove token and redirect to login
        localStorage.removeItem('jwtToken');
        window.location.href = '/login'; // Redirect to login page
        throw new Error('Unauthorized: Session expired or invalid token.'); // Stop further processing
    }

    return response;
};

export default authenticatedFetch;
const authenticatedFetch = async (url, options = {}) => {
    const token = localStorage.getItem('jwtToken');

    const headers = {
        ...options.headers,
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, {
        ...options,
        headers,
    });

    return response;
};

export default authenticatedFetch;