import circleLogo2 from '../assets/circleLogo2.png'
import { motion } from 'motion/react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelopeCircleCheck, faLocationDot, faPhone } from '@fortawesome/free-solid-svg-icons';
import { faSquareInstagram, faSquareGithub, faLinkedin } from '@fortawesome/free-brands-svg-icons';
import { Link } from 'react-router';

let Footer = () => {
    return (
        <>
            <section className="px-5 lg:px-10 pt-16 pb-5 bg-[#f4f4f4]">
                <div className='lg:grid grid-cols-4 gap-5'>
                    <div>
                        <img className='w-52 mb-6' src={circleLogo2} alt="footerLogo" />
                        <p className="text-[18px] text-[#5a5a5a]">"StudyCircle connects students with passionate tutors for personalized, empowering 1-on-1 learning.</p>
                    </div>
                    <div className='my-10 lg:my-0'>
                        <h4 className='text-black font-bold text-2xl mb-6'>Quick Links</h4>
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
                            className='flex flex-col items-start font-medium gap-3'>
                            <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><Link to="/">Home</Link></li>
                            <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><Link to="/about">About</Link></li>
                            <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="">BecomeTutor</a></li>
                            <li className='text-[#4A90E2] hover:text-[#FFA500] duration-300 mx-5'><a href="">Contact</a></li>
                            <motion.li
                                whileHover={{
                                    color: 'white',
                                    backgroundColor: '#4A90E2'
                                }}
                                transition={{
                                    duration: .5
                                }}
                                className='text-[#5a5a5a] border-1 border-[#4A90E2] bg-white py-1 px-3 rounded-full mx-5'><a href="">Get Started</a></motion.li>
                        </motion.ul>
                    </div>
                    <div className='my-10 lg:my-0'>
                        <h4 className='text-black font-bold text-2xl mb-6'>Contact Info</h4>
                        <p><FontAwesomeIcon icon={faLocationDot} size='lg' color='#4A90E2' /> Pune, Maharashtra, India</p>
                        <p className='my-5'><a className='hover:text-[#FFA500] duration-300' href=""><FontAwesomeIcon icon={faEnvelopeCircleCheck} size='lg' color='#4A90E2' /> support@studycircle.com</a></p>
                        <p><FontAwesomeIcon icon={faPhone} size='lg' color='#4A90E2' /> +91 98765 43210</p>
                    </div>
                    <div className='my-10 lg:my-0'>
                        <h4 className='text-black font-bold text-2xl mb-6'>Social Links</h4>
                        <p className="text-[17px] text-[#5a5a5a] mb-3">Follow us for tips, updates, and learning inspiration:</p>
                        <div className='text-[#4A90E2]'>
                            <a className='hover:text-[#FFA500] duration-300' href="https://www.linkedin.com/in/om-pimpale-83a524279" target='_blank'><FontAwesomeIcon icon={faLinkedin} size='2x' /></a>
                            <a className='mx-4 hover:text-[#FFA500] duration-300' href="https://github.com/OmPimpale" target='_blank'><FontAwesomeIcon icon={faSquareGithub} size='2x' /></a>
                            <a className='hover:text-[#FFA500] duration-300' href="https://www.instagram.com/_om_pimpal" target='_blank'><FontAwesomeIcon icon={faSquareInstagram} size='2x' /></a>
                        </div>
                    </div>
                </div>
                <div className='border-[0.1px] border-gray-300 my-5'></div>
                <div className='text-center'>
                    <p>&copy; {new Date().getFullYear()} StudyCircle. All rights reserved.</p>
                </div>
            </section>
        </>
    )
}

export default Footer;