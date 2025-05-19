import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStreetView } from "@fortawesome/free-solid-svg-icons";
import about from '../../../assets/about.png'

let AboutHero = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16 pt-36">
                <div className="text-center">
                    <h2 className="text-[40px] font-bold text-[#1e2a32] pb-10">About <span className="text-[#FFA500]">StudyCircle</span></h2>
                    <div className="md:grid grid-cols-5 gap-10 items-center">
                        <img src={about} alt="" className="col-span-2" />
                        <div className="col-span-3 mt-5 md:mt-0 md:text-start">
                            <h4 className="text-[30px] font-bold text-[#1e2a32] pb-6">Learn. Teach. Grow <span className="text-[#FFA500]">Together</span></h4>
                            <p className="text-[20px] text-[#5a5a5a]">At StudyCircle, we believe that knowledge grows best when it's shared. We’re building a student-powered learning community where anyone can:</p>
                            <button className="mt-7 text-white text-[18px] border-1 border-[#4A90E2] bg-[#4A90E2] py-1.5 px-7 rounded-full">Join Circle <FontAwesomeIcon icon={faStreetView} size="lg" className="text-white" /></button>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default AboutHero;