import { Link, useNavigate } from "react-router";
import { useState } from 'react';
import login2 from "../../assets/login2.jpg";
import circleLogo2 from '../../assets/circleLogo2.png'

let Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false); // Add loading state

    const navigate = useNavigate();
    const handleLogin = async (e) => {

        e.preventDefault();
        setError(''); // Clear previous errors
        setLoading(true); // Set loading to true

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: email, password: password }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Login failed. Please check your credentials.');
            }

            const data = await response.json();
            localStorage.setItem('jwtToken', data.token);
            navigate('/dashboard');

        } catch (error) {
            console.error('Login error:', error);
            if (error.message) {
                setError(error.message);
            } else {
                setError('An unexpected error occurred. Please try again.');
            }
        } finally {
            setLoading(false); // Set loading to false
        }

    };
    return (
        <section className="flex items-center justify-center min-h-screen bg-gray-100 p-4 lg:p-8" style={{ backgroundImage: `url(${login2})`, backgroundSize: 'cover', backgroundPosition: 'center' }}>
            <div className="flex flex-col lg:flex-row w-full max-w-4xl bg-white rounded-lg shadow-lg overflow-hidden">
                {/* Image Section (Hidden on small screens) */}
                <div className="hidden lg:block lg:w-1/2">
                    <img src={login2} alt="Login Background" className="w-full h-full object-cover" />
                </div>

                {/* Login Form Section */}
                <div className="w-full lg:w-1/2 p-6 sm:p-8">
                    <div className="text-center mb-6">
                        <Link to="/" className="inline-block">
                            <img className="w-28 mx-auto" src={circleLogo2} alt="Circle Logo" />
                        </Link>
                    </div>

                    <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Login</h2>

                    {error && (
                        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
                            <span className="block sm:inline">{error}</span>
                        </div>
                    )}

                    <form onSubmit={handleLogin}>
                        <div className="mb-4">
                            <label className="block text-gray-700 text-sm font-semibold mb-2" htmlFor="email">
                                Email*
                            </label>
                            <input
                                className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                                id="email"
                                type="email"
                                placeholder="your@example.com"
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="mb-6">
                            <label className="block text-gray-700 text-sm font-semibold mb-2" htmlFor="password">
                                Password*
                            </label>
                            <input
                                className="appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                                id="password"
                                type="password"
                                placeholder="********"
                                required
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <div className="text-right">
                                <a className="inline-block align-baseline text-sm text-blue-500 hover:text-blue-800" href="#">
                                    Forgot Password?
                                </a>
                            </div>
                        </div>

                        <div className="flex items-center justify-between mb-4">
                            <button
                                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full disabled:opacity-50 disabled:cursor-not-allowed"
                                type="submit"
                                disabled={loading}
                            >
                                {loading ? 'Logging In...' : 'Sign In'}
                            </button>
                        </div>

                        <div className="text-center">
                            <p className="text-gray-600 text-sm">
                                Don't have an account?{' '}
                                <Link to="/signup" className="text-blue-500 hover:text-blue-800 font-bold">
                                    Don't have an account?
                                </Link>{' '}
                                <Link to="/signup" className="text-blue-500 hover:text-blue-800 font-bold">
                                    Sign Up
                                </Link>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    );
}

export default Login;