import contact from '../../../assets/contact.jpg'

let ContactUs = () => {
    return (
        <>
            <section className="px-5 lg:px-10 py-16" id='contactus'>
                <div>
                    <h2 className="text-center text-[36px] font-bold text-[#1e2a32] pb-10">Contact <span className="text-[#FFA500]">StudyCircle</span></h2>
                    <div className='lg:grid grid-cols-2 gap-16 items-start' >
                        <div>
                            <form className="max-w-md mx-auto">
                                <div className="grid md:grid-cols-2 md:gap-6">
                                    <div className="relative z-0 w-full mb-5 group">
                                        <input type="text" name="floating_first_name" id="floating_first_name" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " required />
                                        <label htmlFor="floating_first_name" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">First name*</label>
                                    </div>
                                    <div className="relative z-0 w-full mb-5 group">
                                        <input type="text" name="floating_last_name" id="floating_last_name" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " />
                                        <label htmlFor="floating_last_name" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Last name</label>
                                    </div>
                                </div>
                                <div className="relative z-0 w-full mb-5 group">
                                    <input type="email" name="floating_email" id="floating_email" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " required />
                                    <label htmlFor="floating_email" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Email address*</label>
                                </div>
                                <div className="relative z-0 w-full mb-5 group">
                                    <input type="password" name="floating_password" id="floating_password" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " required />
                                    <label htmlFor="floating_password" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Subject*</label>
                                </div>
                                <div className="grid md:grid-cols-1 md:gap-6">
                                    <div className="relative z-0 w-full mb-5 group">
                                        <input type="tel" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" name="floating_phone" id="floating_phone" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " required />
                                        <label htmlFor="floating_phone" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Phone number*</label>
                                    </div>
                                    {/* <div className="relative z-0 w-full mb-5 group">
                                <input type="text" name="floating_company" id="floating_company" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" placeholder=" " required />
                                <label htmlFor="floating_company" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Company (Ex. Google)</label>
                            </div> */}
                                </div>
                                <div className="relative z-0 w-full mb-5 group">
                                    <textarea name="repeat_password" id="floating_repeat_password" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-[#1e2a32] dark:border-gray-600 dark:focus:border-[#4A90E2] focus:outline-none focus:ring-0 focus:border-[#4A90E2] peer" rows={4} placeholder=" " required></textarea>
                                    <label htmlFor="floating_repeat_password" className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-[#4A90E2] peer-focus:dark:text-[#4A90E2] peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Massege*</label>
                                </div>
                                <button type="submit" className="text-white bg-[#4A90E2] hover:bg-[#FFA500] duration-300 focus:ring-4 focus:outline-none font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Submit</button>
                            </form>
                        </div>
                        <div className="mt-12 lg:mt-0">
                            <img src={contact} alt="contact" className='mx-auto rounded-[10px] w-[min(80%,700px)]' />
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default ContactUs;