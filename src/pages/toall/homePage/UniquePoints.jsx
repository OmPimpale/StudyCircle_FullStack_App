import atRiskStudents from '../../../assets/atRiskStudents.png'
import personalizedSupport from '../../../assets/personalizedSupport.png'
import strengthenedCommunity from '../../../assets/strengthenedCommunity.png'
import broaderReach2 from '../../../assets/broaderReach2.png'
import broaderReach from '../../../assets/broaderReach.png'
import virtualSupport from '../../../assets/virtualSupport.png'

let UniquePoints = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16">
                <div className="text-center">
                    <h2 className="text-[36px] font-bold text-[#1e2a32] pb-10">How StudyCircle Helps Students With a <br /><span className='text-[#ffa500]'>"Unique Edge"</span></h2>
                    <div className='grid grid-cols-1 gap-4 md:grid md:grid-cols-2 lg:grid lg:grid-cols-3'>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={broaderReach2} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Match Based on Learning Personality</h6>
                            <p className="text-[#5a5a5a]">We don’t just match by subject we consider learning styles (visual, auditory, kinesthetic) and tutor personalities to create the best possible student-tutor chemistry. This results in faster understanding and a more enjoyable learning experience.</p>
                        </div>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={personalizedSupport} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Skill Gap Analysis Before Starting</h6>
                            <p className="text-[#5a5a5a]">Before beginning lessons, students take a short diagnostic to identify specific knowledge gaps. Tutors use this insight to target weak areas, ensuring no time is wasted on topics already mastered.</p>
                        </div>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={strengthenedCommunity} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Goal-Oriented Learning Paths</h6>
                            <p className="text-[#5a5a5a]">Students set short- and long-term academic goals (e.g., scoring 90+ in math, mastering JavaScript, or improving spoken English), and StudyCircle creates a structured path to reach those milestones with periodic check-ins.</p>
                        </div>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={atRiskStudents} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Session Replays for Revision</h6>
                            <p className="text-[#5a5a5a]">We don’t just match by subject we consider learning styles (visual, auditory, kinesthetic) and tutor personalities to create the best possible student-tutor chemistry. This results in faster understanding and a more enjoyable learning experience.</p>
                        </div>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={broaderReach} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Peer Collaboration Rooms (Beta)</h6>
                            <p className="text-[#5a5a5a]">A unique feature where students can join moderated study circles (group video chats) with others learning the same subject. Great for collaborative problem-solving and building social confidence.</p>
                        </div>
                        <div className="p-5 border-[0.5px] border-[#4A90E2] rounded-[10px] hover:bg-[#dceefb] duration-[.4s] hover:shadow-[0_0_10px_gray]">
                            <img src={virtualSupport} alt="" className='mx-auto w-[133px] mb-5' />
                            <h6 className='text-black font-bold mb-2.5'>Instant Doubt Resolution</h6>
                            <p className="text-[#5a5a5a]">Students can quickly get help when stuck — even outside scheduled sessions. With instant messaging and optional emergency micro-sessions, StudyCircle ensures that no question goes unanswered for too long, keeping learning momentum intact.</p>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default UniquePoints;