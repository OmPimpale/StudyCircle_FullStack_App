import { Link } from 'react-router';
import createAcc from '../../assets/createAcc.jpg'
import circleLogo2 from '../../assets/circleLogo2.png'

let Login = () => {
    return (
        <>
            <section className={`p-5 lg:p-14 bg-contain lg:grid grid-cols-5 gap-2.5 items-center`}>
                <img src={createAcc} alt="" className='col-span-2 hidden lg:block w-full' />
                <div className=" col-span-3 rounded">
                    <div class="w-full xl:w-1/2 p-8 bg-[#f4f4f4] mx-auto rounded-b-lg">
                        <div className='text-center mb-3'>
                            <Link to="/" className='inline-block'><img className="text-[36px] font-bold text-[#FFA500] w-32" src={circleLogo2} /></Link>
                        </div>
                        <form method="post" action="#" onSubmit="return false">
                            <h1 class=" text-4xl font-bold text-center mb-2.5">Login</h1>

                            <div class="my-4">
                                <label
                                    class="block text-[#1e2a38] text-sm font-semibold mb-2"
                                    htmlFor="email"
                                >
                                    Full Name*
                                </label>
                                <input
                                    class="text-sm appearance-none rounded w-full py-2 px-3 text-gray-700 bg-gray-200 leading-tight focus:outline-none focus:shadow-outline h-10"
                                    id="email"
                                    type="text"
                                    placeholder="Your name"
                                    required
                                />
                                <div className='my-6'>
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
                            </div>
                            <div class="my-6">
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
                                {/* <div className='text-end'>
                                    <a
                                        class="inline-block align-baseline text-sm text-[#5a5a5a] hover:text-[#ffa500]"
                                        href="#"
                                    >
                                        Forgot Password?
                                    </a>
                                </div> */}
                            </div>
                            <div class="flex w-full mt-8">
                                <button
                                    class="w-full bg-[#4A90E2] hover:bg-[#FFA500] duration-300 text-white text-sm py-2 px-4 font-semibold rounded focus:outline-none focus:shadow-outline h-10"
                                    type="button"
                                >
                                    Sign in
                                </button>
                            </div>
                            <div className="mt-1.5 text-center">
                                <span class="text-[#5a5a5a] text-sm">
                                    Already have an Acoount?
                                </span>
                                <Link to="/login">
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