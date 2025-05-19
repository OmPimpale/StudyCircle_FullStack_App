import { Link } from 'react-router';
import login2 from '../../assets/login2.jpg'

let Login = () => {
    return (
        <>
            <section className={`p-5 lg:p-14 bg-contain lg:grid grid-cols-2 gap-2.5 items-center`}>
                <img src={login2} alt="" className='col-span-1 hidden lg:block'/>
                <div className="container max-w-md mx-auto xl:max-w-3xl h-full overflow-hidden col-span-1">
                    <div className="text-center w-full xl:w-1/2 p-8 mx-auto bg-gradient-to-tr from-[#4A90E2] to-indigo-800 rounded-t-lg">
                        <h2 className="text-[36px] font-bold text-[#FFA500]"><Link to="/">StudyCircle</Link></h2>
                        <p className="text-white">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Animi, optio?</p>
                    </div>
                    <div class="w-full xl:w-1/2 p-8 bg-[#f4f4f4] mx-auto rounded-b-lg">
                        <form method="post" action="#" onSubmit="return false">
                            <h1 class=" text-2xl font-bold">Sign in to your account</h1>
                            <div className="mt-1.5">
                                <span class="text-[#5a5a5a] text-sm">
                                    Don't have an account?
                                </span>
                                <Link to="/singUp">
                                    <span class="text-[#1e2a38] hover:text-[#ffa500] text-sm font-semibold ms-1">
                                        Sign up
                                    </span>
                                </Link>
                            </div>
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
                                />
                                <a
                                    class="inline-block align-baseline text-sm text-[#5a5a5a] hover:text-[#ffa500]"
                                    href="#"
                                >
                                    Forgot Password?
                                </a>
                            </div>
                            <div class="flex w-full mt-8">
                                <button
                                    class="w-full bg-[#4A90E2] hover:bg-[#FFA500] duration-300 text-white text-sm py-2 px-4 font-semibold rounded focus:outline-none focus:shadow-outline h-10"
                                    type="button"
                                >
                                    Sign in
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
        </>
    )
}

export default Login;