jsx
import React from 'react';
import Navbar from '../Components/Navbar'; // Adjust the import path as necessary
import Footer from '../Components/Footer'; // Adjust the import path as necessary

function HelpCenterPage() {
  return (
    <>
      <Navbar />
      <div className="help-center-container">
        <h1>FAQ/Help Center</h1>
        <p>This is the Help Center page. Here you will find answers to frequently asked questions.</p>
        
        {/* Placeholder for FAQ list */}
        <div className="faq-list">
          <h2>Frequently Asked Questions</h2>
          <ul>
            <li>
              <strong>Q: How do I create an account?</strong>
              <p>A: You can create an account by clicking on the "Sign Up" link in the navigation bar and following the instructions.</p>
            </li>
            <li>
              <strong>Q: How can I find a tutor?</strong>
              <p>A: You can find a tutor by browsing subjects or using the search functionality on the platform.</p>
            </li>
            <li>
              <strong>Q: How do I book a session?</strong>
              <p>A: Once you find a tutor, you can view their availability and book a session through their profile or the subject page.</p>
            </li>
            {/* Add more FAQs here */}
          </ul>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default HelpCenterPage;