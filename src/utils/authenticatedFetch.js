const authenticatedFetch = async (url, options = {}) => {
  const token = localStorage.getItem('jwtToken');

  const headers = {
    ...options.headers,
  };

  // Add Authorization header if token is available
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const response = await fetch(url, {
    ...options,
    headers,
  });

  // Handle unauthorized access
  if (response.status === 401) {
    // Clear token and redirect to login
    localStorage.removeItem('jwtToken');
    window.location.href = '/login';
    throw new Error('Unauthorized: Session expired or invalid token.');
  }

  return response;
};

export default authenticatedFetch;