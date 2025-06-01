import { faSquareCheck } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router";

let WhyCircle = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16">
                <div className="text-center">
                    <h2 className="text-[36px] font-bold text-[#1e2a32] mb-5">Why <span className="text-[#FFA500]">StudyCircle</span>?</h2>
                    <div className="lg:flex justify-evenly">
                        <ul className="text-[#5a5a5a] text-[18px] text-start">
                            <div className="flex items-center my-2.5">
                                <FontAwesomeIcon icon={faSquareCheck} size="lg" className="me-2.5 text-[#4A90E2]" />
                                <li>Peer-to-peer learning from real students and tech enthusiasts</li>
                            </div>
                            <div className="flex items-center my-2.5">
                                <FontAwesomeIcon icon={faSquareCheck} size="lg" className="me-2.5 text-[#4A90E2]" />
                                <li>Affordable access to live and recorded courses</li>
                            </div>
                            <div className="flex items-center my-2.5">
                                <FontAwesomeIcon icon={faSquareCheck} size="lg" className="me-2.5 text-[#4A90E2]" />
                                <li>Tutors earn while helping others succeed</li>
                            </div>
                            <div className="flex items-center my-2.5">
                                <FontAwesomeIcon icon={faSquareCheck} size="lg" className="me-2.5 text-[#4A90E2]" />
                                <li>Financial aid options for students in need</li>
                            </div>
                            <div className="flex items-center my-2.5">
                                <FontAwesomeIcon icon={faSquareCheck} size="lg" className="me-2.5 text-[#4A90E2]" />
                                <li>Safe, verified, and supportive community</li>
                            </div>
                        </ul>
                        <div className="mt-12 lg:mt-20">
                            <Link to="/singUp" className="text-white text-[18px] bg-[#4A90E2] py-2 px-7 rounded-full hover:bg-[#ffa500] duration-[.4s]">Start Learning Today</Link>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default WhyCircle;