import { Link, useNavigate } from 'react-router';
import createAcc from '../../assets/createAcc.jpg'
import circleLogo2 from '../../assets/circleLogo2.png'
import { useState } from 'react';

const CreateAccount = () => {
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    return (
        <>
            <section className="flex items-center justify-center min-h-screen bg-[#f4f4f4] p-4 lg:p-8">
                <div className="flex flex-col lg:flex-row w-full max-w-4xl bg-white rounded-lg shadow-lg overflow-hidden">
                    <div className="hidden lg:block lg:w-1/2 p-5">
                        <img src={createAcc} alt="" className="w-full h-full object-cover" />
                    </div>
                    <div className="w-full lg:w-1/2 p-5 sm:p-8">
                        <div className="w-full mx-auto rounded-b-lg">
                            <div className='text-center mb-3'>
                                <Link to="/" className='inline-block'><img className="text-[36px] font-bold text-[#FFA500] w-32" src={circleLogo2} /></Link>
                            </div>
                            <form onSubmit={async (e) => {
                                e.preventDefault(); // Prevent default form submission
                                setError(''); // Clear previous errors
                                setLoading(true); // Set loading to true

                                try {
                                    const response = await fetch('/api/auth/register', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify({ fullName, username: email, password }),
                                    });

                                    if (response.ok) {
                                        navigate('/login'); // Redirect to login on success
                                    } else {
                                        const errorData = await response.json();
                                        setError(errorData.message || 'Account creation failed. Please try again.');
                                    }
                                } catch (error) {
                                    console.error('Account creation error:', error);
                                    setError('An error occurred. Please try again later.');
                                } finally {
                                    setLoading(false); // Set loading to false
                                }
                            }}
                            >
                                <h2 class="text-3xl font-bold text-center text-gray-800 mb-6">Create Account</h2>
                                {/* Full Name */}
                                <div class="my-4">
                                    <label
                                        class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                        htmlFor="email"
                                    >
                                        Full Name*
                                    </label>
                                    <input
                                        class="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                                        id="email"
                                        type="text"
                                        placeholder="Your name"
                                        required
                                        value={fullName}
                                        onChange={(e) => setFullName(e.target.value)}
                                    />
                                    <div className='my-6'>
                                        {/* Email */}

                                        <label
                                            class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                            htmlFor="email"
                                        >
                                            Email*
                                        </label>
                                        <input
                                            class="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                                            id="email"
                                            type="text"
                                            placeholder="Your email address"
                                            required
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                        />
                                    </div>
                                    {/* Password */}

                                </div>
                                <div class="my-6">
                                    <label
                                        class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                        htmlFor="password"
                                    >
                                        Password*
                                    </label>
                                    <input
                                        class="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                                        id="password"
                                        type="password"
                                        placeholder="Your password"
                                        required
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                {error && (
                                    <div className="text-red-500 text-sm mb-4 text-center">{error}</div>
                                )}
                                <div class="flex w-full mt-8">
                                    <button
                                        class="w-full bg-[#4A90E2] hover:bg-[#FFA500] duration-300 text-white text-sm py-2 px-4 font-semibold rounded focus:outline-none focus:shadow-outline h-10"
                                        type="submit"
                                        disabled={loading}
                                    >
                                        Create Account
                                    </button>
                                </div>
                                <div className="mt-3 text-center">
                                    <span class="text-[#5a5a5a] text-sm">
                                        Already have an Acoount?
                                    </span>
                                    <Link to="/login">
                                        <span class="text-[#1e2a38] hover:text-[#FFA500] text-sm font-semibold ms-1">
                                            Login
                                        </span>
                                    </Link>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default CreateAccount;