import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import login2 from '../../assets/login2.jpg';
import circleLogo2 from '../../assets/circleLogo2.png'

let Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();
    const handleLogin = async (e) => {
        e.preventDefault();

        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username: email, password: password }),
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('jwtToken', data.token);
            navigate('/dashboard');
        } else {
            setError('Invalid email or password. Please try again.');
            // You might want to set an error state and display it to the user
        }

    };
    return (
        <>
            <section className={`p-5 lg:p-14 bg-contain lg:grid grid-cols-2 gap-2.5 items-center`}>
                <img src={login2} alt="" className='col-span-1 hidden lg:block' />
                <div className="container max-w-md mx-auto xl:max-w-3xl h-full overflow-hidden col-span-1 rounded">
                    <div class="w-full xl:w-1/2 p-8 bg-[#f4f4f4] mx-auto rounded-b-lg">
                        <div className='text-center mb-3'>
                            <Link to="/" className='inline-block'><img className="text-[36px] font-bold text-[#FFA500] w-32" src={circleLogo2} /></Link>
                        </div>
                        {error && (
                            <div className="text-red-500 text-sm mb-4 text-center">{error}</div>
                        )}
                        <form onSubmit={handleLogin}>
                            <h1 class=" text-4xl font-bold text-center mb-2.5">Login</h1>

                            <div class="mb-4 mt-6">
                                <label
                                    class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                    htmlFor="email"
                                >
                                    Email*
                                </label>
                                <input
                                    class="text-sm appearance-none rounded w-full py-2 px-3 text-gray-700 bg-gray-200 leading-tight focus:outline-none focus:shadow-outline h-10"
                                    id="email"
                                    type="text"
                                    placeholder="Your email address"
                                    required
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div class="mb-6 mt-6">
                                <label
                                    class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                    htmlFor="password"
                                >
                                    Password*
                                </label>
                                <input
                                    class="text-sm bg-gray-200 appearance-none rounded w-full py-2 px-3 text-gray-700 mb-1 leading-tight focus:outline-none focus:shadow-outline h-10"
                                    id="password"
                                    type="password"
                                    placeholder="Your password"
                                    required
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                                <div className='text-end'>
                                    <a
                                        class="inline-block align-baseline text-sm text-[#5a5a5a] hover:text-[#ffa500]"
                                        href="#"
                                    >
                                        Forgot Password?
                                    </a>
                                </div>
                            </div>
                            <div class="flex w-full mt-8">
                                <button
                                    class="w-full bg-[#4A90E2] hover:bg-[#FFA500] duration-300 text-white text-sm py-2 px-4 font-semibold rounded focus:outline-none focus:shadow-outline h-10"
                                    type="submit" >
                                    Sign in
                                </button>
                            </div>
                            <div className="mt-1.5 text-center">
                                <span class="text-[#5a5a5a] text-sm">
                                    Don't have an account?
                                </span>
                                <Link to="/singUp">
                                    <span class="text-[#1e2a38] hover:text-[#ffa500] text-sm font-semibold ms-1">
                                        Sign up
                                    </span>
                                </Link>
                            </div>

                        </form>
                    </div>
                </div>
            </section>
        </>
    )
}

export default Login;