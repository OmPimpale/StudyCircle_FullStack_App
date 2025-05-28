jsx
import React from 'react';
import Navbar from '../Components/Navbar'; // Adjust the import path as needed
import Footer from '../Components/Footer'; // Adjust the import path as needed
import { useParams } from 'react-router-dom';

function SubjectPage() {
  const { subjectName } = useParams(); // This will get the subject name from the URL

  return (
    <div>
      <Navbar />
      <div className="container mx-auto p-4">
        <h1 className="text-2xl font-bold mb-4">Subject: {subjectName ? subjectName : 'Loading Subject...'}</h1>
        {/* Placeholder for subject content */}
        <p>This is the content for the {subjectName ? subjectName : 'selected'} subject.</p>
        {/* More content will be added here, such as available tutors, course materials, etc. */}
      </div>
      <Footer />
    </div>
  );
}

export default SubjectPage;