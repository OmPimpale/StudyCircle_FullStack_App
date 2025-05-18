import studentNew from '../../../assets/studentNew.jpg'
import { motion } from 'motion/react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPersonChalkboard } from '@fortawesome/free-solid-svg-icons';
import { faChalkboardUser } from '@fortawesome/free-solid-svg-icons';

let HeroSection = () => {

    return (
        <>
            <section className="md:flex items-center text-center md:text-start px-5 lg:px-10 py-16 pt-36">
                <div className="">
                    <motion.h1
                        initial={{
                            opacity: 0
                        }}
                        animate={{
                            opacity: 1
                        }}
                        transition={{
                            duration: 1
                        }}
                        className="text-[44px] lg:text-5xl font-bold mb-3 text-[#1e2a38]">Learn. Teach. Grow <span className='text-[#ffa500]'>Together</span></motion.h1>
                    <motion.p
                        initial={{
                            opacity: 0
                        }}
                        animate={{
                            opacity: 1
                        }}
                        transition={{
                            duration: 1
                        }}
                        className="font-medium text-[#5a5a5a] text-[20px]">Join a peer-powered platform where students teach and learn cutting-edge tech live, recorded, and affordable.</motion.p>
                    <motion.p
                        initial={{
                            opacity: 0
                        }}
                        animate={{
                            opacity: 1
                        }}
                        transition={{
                            duration: 1
                        }}
                        className="mt-5 font-medium text-black text-[20px]">Start your journey as a:</motion.p>
                    <div className="mt-2.5">
                        <motion.button
                            initial={{
                                opacity: 0,
                                y: -10
                            }}
                            animate={{
                                opacity: 1,
                                y: 0
                            }}
                            whileHover={{
                                color: 'white',
                                backgroundColor: '#4A90E2',
                                boxShadow: '0 0 15px #4A90E2'
                            }}
                            transition={{
                                duration: .5
                            }}
                            className="text-white text-[18px] border-1 border-[#4A90E2] bg-[#4A90E2] py-1.5 px-7 rounded-full me-7">
                            Tutor <FontAwesomeIcon icon={faPersonChalkboard} size="lg" />
                        </motion.button>
                        <motion.button
                            initial={{
                                opacity: 0,
                                y: -10
                            }}
                            animate={{
                                opacity: 1,
                                y: 0
                            }}
                            whileHover={{
                                color: 'white',
                                backgroundColor: '#4A90E2',
                                boxShadow: '0 0 15px #4A90E2'
                            }}
                            transition={{
                                duration: .5
                            }}
                            id='studentBtn'
                            className="text-black text-[18px] border-1 border-[#4A90E2] bg-white py-1.5 px-7 rounded-full me-2.5">
                            Student <FontAwesomeIcon icon={faChalkboardUser} size="lg" />
                        </motion.button>
                    </div>
                </div>
                <div className="mt-5 md:mt-0 w-fit lg:w-dvh">
                    <img className="w-6/12 mx-auto" src={studentNew} alt="student" />
                </div>
            </section>
        </>
    )
}

export default HeroSection;