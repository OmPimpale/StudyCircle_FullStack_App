import students from '../../../assets/students.jpg'
import findTouter from '../../../assets/findTouter.svg'
import connectTouter from '../../../assets/connectTouter.svg'
import startLearn from '../../../assets/startLearn.svg'
import { motion } from 'motion/react';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

let HowWork = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16 bg-[#f4f4f4]">
                <div className="text-center mb-10">
                    <h2 className="text-[36px] font-bold text-[#1e2a32] pb-10">How <span className='text-[#ffa500]'>StudyCircle</span> Work?</h2>
                    <div className='lg:grid grid-cols-3 gap-10 hidden'>
                        <img className='rounded-[10px]' src={students} alt="" />
                        <div className='col-span-2'>
                            <p className="text-[20px] text-[#5a5a5a]">StudyCircle connects passionate and qualified tutors with students for personalized 1-to-1 learning experiences, helping them achieve academic success.</p>
                            <div className='w-[636px] mx-auto mt-12 text-start'>
                                <motion.div
                                    drag
                                    dragConstraints={{
                                        left: 10,
                                        right: 10,
                                        top: 10,
                                        bottom: 10
                                    }}
                                    dragSnapToOrigin
                                    initial={{
                                        x: 100,
                                        opacity: 0
                                    }}
                                    animate={{
                                        x: 0,
                                        opacity: 1
                                    }}
                                    transition={{
                                        duration: 2,
                                        ease: "anticipate"
                                    }}
                                    className='bg-[#dceefb] p-5 rounded-[10px] flex hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                                    <img src={findTouter} alt="find" />
                                    <div className='ms-4'>
                                        <h6 className='text-black font-bold'>Find the right match</h6>
                                        <p className='text-[#5a5a5a]'>Explore verified tutor profiles across a wide range of subjects.</p>
                                    </div>
                                </motion.div>
                                <motion.div
                                    drag
                                    dragConstraints={{
                                        left: 10,
                                        right: 10,
                                        top: 10,
                                        bottom: 10
                                    }}
                                    dragSnapToOrigin
                                    initial={{
                                        x: 100,
                                        opacity: 0
                                    }}
                                    animate={{
                                        x: 0,
                                        opacity: 1
                                    }}
                                    transition={{
                                        duration: 2,
                                        ease: "anticipate"
                                    }}
                                    className='bg-[#dceefb] p-5 rounded-[10px] flex my-4 hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                                    <img src={connectTouter} alt="find" />
                                    <div className='ms-4'>
                                        <h6 className='text-black font-bold'>Connect with your tutor</h6>
                                        <p className='text-[#5a5a5a]'>Message and schedule sessions that match your learning pace and needs.</p>
                                    </div>
                                </motion.div>
                                <motion.div
                                    drag
                                    dragConstraints={{
                                        left: 10,
                                        right: 10,
                                        top: 10,
                                        bottom: 10
                                    }}
                                    dragSnapToOrigin
                                    initial={{
                                        x: 100,
                                        opacity: 0
                                    }}
                                    animate={{
                                        x: 0,
                                        opacity: 1
                                    }}
                                    transition={{
                                        duration: 2,
                                        ease: "anticipate"
                                    }}
                                    className='bg-[#dceefb] p-5 rounded-[10px] flex hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                                    <img src={startLearn} alt="find" />
                                    <div className='ms-4'>
                                        <h6 className='text-black font-bold'>Start your learning journey</h6>
                                        <p className='text-[#5a5a5a]'>Join engaging sessions and track your progress with ease.</p>
                                    </div>
                                </motion.div>
                            </div>
                        </div>
                    </div>
                    <div className='lg:hidden mb-12'>
                        <p className="text-[20px] mb-5">StudyCircle connects passionate and qualified tutors with students for personalized 1-to-1 learning experiences, helping them achieve academic success.</p>
                        <img className='mx-auto rounded-[10px] w-[min(80%,400px)]' src={students} alt="students" />
                    </div>
                    <div className='lg:hidden text-start'>
                        <motion.div
                            initial={{
                                x: 100,
                                opacity: 0
                            }}
                            animate={{
                                x: 0,
                                opacity: 1
                            }}
                            transition={{
                                duration: 2,
                                ease: "anticipate"
                            }}
                            className='bg-[#dceefb] p-5 rounded-[10px] flex hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                            <img src={findTouter} alt="find" />
                            <div className='ms-4'>
                                <h6 className='text-black font-bold'>Find the right match</h6>
                                <p className='text-[#5a5a5a]'>Explore verified tutor profiles across a wide range of subjects.</p>
                            </div>
                        </motion.div>
                        <motion.div
                            initial={{
                                x: 100,
                                opacity: 0
                            }}
                            animate={{
                                x: 0,
                                opacity: 1
                            }}
                            transition={{
                                duration: 2,
                                ease: "anticipate"
                            }}
                            className='bg-[#dceefb] p-5 rounded-[10px] flex my-4 hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                            <img src={connectTouter} alt="find" />
                            <div className='ms-4'>
                                <h6 className='text-black font-bold'>Connect with your tutor</h6>
                                <p className='text-[#5a5a5a]'>Message and schedule sessions that match your learning pace and needs.</p>
                            </div>
                        </motion.div>
                        <motion.div
                            initial={{
                                x: 100,
                                opacity: 0
                            }}
                            animate={{
                                x: 0,
                                opacity: 1
                            }}
                            transition={{
                                duration: 2,
                                ease: "anticipate"
                            }}
                            className='bg-[#dceefb] p-5 rounded-[10px] flex hover:shadow-[0_0_10px_#4A90E2] duration-500'>
                            <img src={startLearn} alt="find" />
                            <div className='ms-4'>
                                <h6 className='text-black font-bold'>Start your learning journey</h6>
                                <p className='text-[#5a5a5a]'>Join engaging sessions and track your progress with ease.</p>
                            </div>
                        </motion.div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default HowWork;