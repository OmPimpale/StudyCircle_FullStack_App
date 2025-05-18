import Footer from "../../Components/Footer";
import Navbar from "../../Components/Navbar";
import ContactUs from "./homePage/ContactUs";
import Focus from "./homePage/Focus";
import HeroSection from "./homePage/HeroSection";
import HowWork from "./homePage/HowWork";
import UniquePoints from "./homePage/UniquePoints";

let Home = () => {
    return (
        <>
            <section>
                <Navbar />
                <HeroSection />
                <HowWork />
                <UniquePoints />
                <Focus />
                <ContactUs />
                <Footer />
            </section>
        </>
    )
}

export default Home;
