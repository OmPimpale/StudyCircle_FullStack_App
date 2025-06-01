import circleLogo2 from '../assets/circleLogo2.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { motion } from 'motion/react'
import { Link, useNavigate } from 'react-router';
import { useState, useEffect } from 'react';
import useLogout from '../utils/useLogout'; // Import the hook

let Navbar = () => {
    const logout = useLogout(); // Call the hook to get the logout function
    const handleLogout = () => {
        localStorage.removeItem('jwtToken'); // Remove the JWT from localStorage
        navigate('/login'); // Redirect to the login page
    };

    const [toggleNav, setToggleNav] = useState(true);

    const navigate = useNavigate(); // Hook to navigate
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        setIsAuthenticated(!!token); // Set isAuthenticated based on token presence
    }, []);

    return (
        <>
            <section className='py-2.5 px-5 lg:px-10 border-b-[0.5px] border-gray-300 bg-white fixed top-0 right-0 left-0 z-50 shadow-md'>
                <div className='container mx-auto flex justify-between items-center'>
                    <div>
                        <Link to="/">
                            <motion.img
                                initial={{
                                    y: -20,
                                    opacity: 0
                                }}
                                animate={{
                                    y: 0,
                                    opacity: 1
                                }}
                                transition={{
                                    duration: .75,
                                    ease: 'linear'
                                }}
                                className='w-24' src={circleLogo2} alt="Logo" />
                        </Link>
                    </div>
                    <div className='flex items-center'>
                        {/* Mobile menu button */}
                            initial={{
                                opacity: 0
                            }}
                            animate={{
                                opacity: 1
                            }}
                            transition={{
                                duration: 1
                            }}
                            className='lg:hidden'>
                             <button onClick={() => setToggleNav(!toggleNav)} className="text-gray-600 hover:text-[#4A90E2] focus:outline-none">
                                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d={toggleNav ? "M4 6h16M4 12h16M4 18h16" : "M6 18L18 6M6 6l12 12"}></path></svg>
                            </button>
                        {/* Desktop navigation */}
                        <nav className='hidden lg:block'>
                            <motion.ul
                                initial={{
                                    opacity: 0
                                }}
                                animate={{
                                    opacity: 1
                                }}
                                transition={{
                                    duration: 1
                                }} className='flex items-center font-medium space-x-6'>
                                <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/">Home</Link></li>
                                <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/sessions">Sessions</Link></li> {/* Assuming you have a Sessions page */}
                                <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/tutors">Find a Tutor</Link></li> {/* Assuming you have a Tutors page */}
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="">BecomeTutor</a></li>
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="#contactus">Contact</a></li>

                                {isAuthenticated ? (
                                    <motion.li
                                        whileHover={{
                                            color: 'white',
                                            backgroundColor: '#4A90E2'
                                        }}
                                        transition={{
                                            duration: .5
                                        }}
                                        className='text-gray-700 border border-[#4A90E2] bg-white py-1 px-3 rounded-full cursor-pointer hover:bg-[#4A90E2] hover:text-white transition-colors duration-300'
                                        onClick={logout} // Call the logout function
                                    >
                                        Logout
                                    </motion.li>
                                ) : (
                                    <motion.li
                                    whileHover={{
                                        color: 'white',
                                        backgroundColor: '#4A90E2'
                                    }}
                                    transition={{
                                        duration: .5
                                    }} className='text-gray-700 border border-[#4A90E2] bg-white py-1 px-3 rounded-full hover:bg-[#4A90E2] hover:text-white transition-colors duration-300'><Link to="/login">Get Started</Link></motion.li>
                                )}
                            </motion.ul>
                        </nav>
                    </motion.div>
                    </div>
                </div>
                <div className={`mt-5 text-center border-t-[0.5px] border-[#5a5a5a] ${toggleNav ? 'hidden' : 'block'}`}>
                    <motion.ul
                        initial={{
                            opacity: 0
                        }}
                        animate={{
                            opacity: 1
                        }}
                        transition={{
                            duration: 1
                        }} className='flex flex-col space-y-4 pt-4'>
                        <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/">Home</Link></li>
                        <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/sessions">Sessions</Link></li>
                        <li className='text-gray-700 hover:text-[#4A90E2] duration-300'><Link to="/tutors">Find a Tutor</Link></li>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><a href="">BecomeTutor</a></li>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><a href="#contactus">Contact</a></li>{isAuthenticated ? (
                                    <motion.li
                                        whileHover={{
                                            color: 'white',
                                            backgroundColor: '#4A90E2'
                                        }}
                                        transition={{
                                            duration: .5
                                        }}
                                        className='text-gray-700 border border-[#4A90E2] bg-white py-1 px-3 rounded-full mx-auto w-[109px] cursor-pointer hover:bg-[#4A90E2] hover:text-white transition-colors duration-300'
                                        onClick={logout} // Call the logout function
                                    >
                                        Logout
                                    </motion.li>
                                ) : (
                                    <motion.li
                                    whileHover={{
                                        color: 'white',
                                        backgroundColor: '#4A90E2'
                                    }}
                                    transition={{
                                        duration: .5
                                    }} className='text-gray-700 border border-[#4A90E2] bg-white py-1 px-3 rounded-full mx-auto w-[109px] hover:bg-[#4A90E2] hover:text-white transition-colors duration-300'><Link to="/login">Get Started</Link></motion.li>
                                )}
                    </motion.ul>
                </div>
            </section>
        </>
    );
};

export default Navbar;