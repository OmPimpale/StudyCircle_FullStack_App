import subscribe from '../../../assets/subscribe.svg'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRocket } from '@fortawesome/free-solid-svg-icons';

let Focus = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16 bg-[#f4f4f4]">
                <div className="text-center ">
                    <h2 className="text-[36px] font-bold text-[#1e2a32] pb-10">100% <span className="text-[#ffa500]">Student Focused</span></h2>
                    <div className='md:grid grid-cols-2'>
                        <div>
                            <img src={subscribe} alt="subscribe" className='mx-auto' />
                        </div>
                        <div className='md:text-start mt-10 md:mt-0'>
                            <p className="text-[20px] text-[#5a5a5a]">Every StudyCircle tutor is handpicked for their teaching passion and subject expertise. Students receive personalized attention, guidance, and mentorship not just lessons. We prioritize your goals, pace, and confidence.</p>
                            <button className='mt-7 text-white text-[18px] bg-[#4A90E2] py-1.5 px-7 rounded-full hover:bg-[#ffa500] duration-[.4s]'>Start Learning <FontAwesomeIcon icon={faRocket} size="lg" className='ms-1' /></button>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default Focus;