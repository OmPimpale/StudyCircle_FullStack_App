
import Footer from "../../Components/Footer";
import Navbar from "../../Components/Navbar";
import AboutCircle from "./aboutPage/AboutCircle";
import WhyCircle from "./aboutPage/WhyCircle";
import AboutHero from "./aboutPage/AboutHero";

let AboutUs = () => {
    return (
        <>
            <Navbar />
            <AboutHero />
            <AboutCircle />
            <WhyCircle />
            <Footer />
        </>
    )
}

export default AboutUs;