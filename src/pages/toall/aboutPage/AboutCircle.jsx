import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGraduationCap, faHandshake, faPeopleGroup } from "@fortawesome/free-solid-svg-icons";
import { motion } from "motion/react";

let AboutCircle = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16 bg-[#F4F4F4]">
                <div className="text-center lg:grid grid-cols-5 gap-10">
                    <div className="col-span-2 lg:sticky lg:top-24 lg:self-start lg:text-start">
                        <h2 className="text-[36px] font-bold text-[#1e2a32] mb-5">What <span className="text-[#FFA500]">You Can Do</span> with StudyCircle</h2>
                        <p className="text-[18px] text-[#5a5a5a] mb-10">Discover a platform where learners and mentors come together to share, grow, and support each other. Whether you're here to learn a new skill, teach what you know, or contribute to a more affordable education ecosystem — StudyCircle empowers you every step of the way.</p>
                    </div>
                    <div className="col-span-3">
                        <motion.div
                            // drag
                            // dragConstraints={{
                            //     left: 10,
                            //     right: 10,
                            //     top: 10,
                            //     bottom: 10
                            // }}
                            // dragSnapToOrigin
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
                            className='bg-[#dceefb] p-10 rounded-[10px] flex my-4 hover:shadow-[0_0_10px_#4A90E2] duration-500 w-[min(99%,725px)] mx-auto'>
                            <div className='text-center relative'>
                                <FontAwesomeIcon icon={faGraduationCap} size="3x" className="text-[#4A90E2] mb-2.5" />
                                <h6 className='text-black font-bold text-[25px]'>Learn in-demand technologies through live sessions and recorded lectures</h6>
                            </div>
                        </motion.div>
                        <motion.div
                            // drag
                            // dragConstraints={{
                            //     left: 10,
                            //     right: 10,
                            //     top: 10,
                            //     bottom: 10
                            // }}
                            // dragSnapToOrigin
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
                            className='bg-[#dceefb] p-10 rounded-[10px] flex my-4 hover:shadow-[0_0_10px_#4A90E2] duration-500 w-[min(99%,725px)] mx-auto'>
                            <div className='text-center mx-auto'>
                                <FontAwesomeIcon icon={faPeopleGroup} size="3x" className="text-[#4A90E2] mb-2.5" />
                                <h6 className='text-black font-bold text-[25px]'>Teach and earn by sharing expertise with fellow learners</h6>
                            </div>
                        </motion.div>
                        <motion.div
                            // drag
                            // dragConstraints={{
                            //     left: 10,
                            //     right: 10,
                            //     top: 10,
                            //     bottom: 10
                            // }}
                            // dragSnapToOrigin
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
                            className='bg-[#dceefb] p-10 rounded-[10px] flex my-4 hover:shadow-[0_0_10px_#4A90E2] duration-500 w-[min(99%,725px)] mx-auto'>
                            <div className='text-center'>
                                <FontAwesomeIcon icon={faHandshake} size="3x" className="text-[#4A90E2] mb-2.5" />
                                <h6 className='text-black font-bold text-[25px]'>Support peers by making education affordable and accessible</h6>
                            </div>
                        </motion.div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default AboutCircle;