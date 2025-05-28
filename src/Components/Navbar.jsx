import circleLogo2 from '../assets/circleLogo2.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBarsStaggered } from '@fortawesome/free-solid-svg-icons';
import { motion } from 'motion/react'
import { Link } from 'react-router';
import { useState } from 'react';

let Navbar = () => {

    const [toggleNav, setToggleNav] = useState('true');

    return (
        <>
            <section className='py-2.5 px-5 lg:px-10 border-b-[0.5px] border-[#5a5a5a] bg-[#F4F4F4] fixed top-0 right-0 left-0 z-50'>
                <div className='flex justify-between items-center'>
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
                                className='w-20' src={circleLogo2} alt="Logo" />
                        </Link>
                    </div>
                    <div className='ps-10'>
                        <motion.div
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
                            <FontAwesomeIcon icon={faBarsStaggered} size="lg" color="#4A90E2" onClick={() => setToggleNav(prev => !prev)} />
                        </motion.div>
                        <div className='hidden lg:block'>
                            <motion.ul
                                initial={{
                                    opacity: 0
                                }}
                                animate={{
                                    opacity: 1
                                }}
                                transition={{
                                    duration: 1
                                }}
                                className='flex items-center font-medium'>
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><Link to="/">Home</Link></li>
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><Link to="/about">About</Link></li>
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="">BecomeTutor</a></li>
                                <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="#contactus">Contact</a></li>
                                <motion.li
                                    whileHover={{
                                        color: 'white',
                                        backgroundColor: '#4A90E2'
                                    }}
                                    transition={{
                                        duration: .5
                                    }}
                                    className='text-[#5a5a5a] border-1 border-[#4A90E2] bg-white py-1 px-3 rounded-full mx-5'><Link to="/login">Get Started</Link></motion.li>
                            </motion.ul>
                        </div>
                    </div>
                </div>
                <div className={`mt-5 text-center border-t-[0.5px] border-[#5a5a5a] ${toggleNav ? 'hidden' : 'block'}` }>
                    <motion.ul
                        initial={{
                            opacity: 0
                        }}
                        animate={{
                            opacity: 1
                        }}
                        transition={{
                            duration: 1
                        }}
                        className=''>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><Link to="/">Home</Link></li>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><Link to="/about">About</Link></li>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><a href="">BecomeTutor</a></li>
                        <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 m-5'><a href="#contactus">Contact</a></li>
                        <motion.li
                            whileHover={{
                                color: 'white',
                                backgroundColor: '#4A90E2'
                            }}
                            transition={{
                                duration: .5
                            }}
                            className='text-[#5a5a5a] border-1 border-[#4A90E2] bg-white py-1 px-3 rounded-full my-5 mx-auto w-[109px]'><Link to="/login">Get Started</Link></motion.li>
                    </motion.ul>
                </div>
            </section>
        </>
    );
};

export default Navbar;