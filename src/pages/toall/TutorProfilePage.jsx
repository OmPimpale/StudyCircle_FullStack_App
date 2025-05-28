jsx
import React from 'react';
import Navbar from '../Components/Navbar'; // Adjust the import path as necessary
import Footer from '../Components/Footer'; // Adjust the import path as necessary

function TutorProfilePage() {
  // Placeholder data - this will be fetched from your backend
  const tutor = {
    name: 'John Doe',
    subjects: ['Mathematics', 'Physics'],
    experience: '5 years of tutoring experience',
    bio: 'Passionate about helping students understand complex concepts.',
    reviews: [
      { id: 1, studentName: 'Alice', rating: 5, comment: 'Great tutor, very patient and clear explanations!' },
      { id: 2, studentName: 'Bob', rating: 4, comment: 'Helped me a lot with my physics homework.' },
    ],
  };

  return (
    <>
      <Navbar />
      <div className="container mx-auto py-8">
        <h1 className="text-3xl font-bold mb-6">Tutor Profile: {tutor.name}</h1>

        <div className="bg-white shadow-md rounded-lg p-6 mb-6">
          <h2 className="text-2xl font-semibold mb-4">About {tutor.name}</h2>
          <p className="text-gray-700 mb-2">
            <strong>Subjects:</strong> {tutor.subjects.join(', ')}
          </p>
          <p className="text-gray-700 mb-2">
            <strong>Experience:</strong> {tutor.experience}
          </p>
          <p className="text-gray-700">{tutor.bio}</p>
        </div>

        <div className="bg-white shadow-md rounded-lg p-6">
          <h2 className="text-2xl font-semibold mb-4">Student Reviews</h2>
          {tutor.reviews.length > 0 ? (
            <ul>
              {tutor.reviews.map((review) => (
                <li key={review.id} className="border-b border-gray-200 py-4">
                  <div className="flex justify-between items-center mb-2">
                    <span className="font-semibold">{review.studentName}</span>
                    <span className="text-yellow-500">{Array(review.rating).fill('⭐').join('')}</span>
                  </div>
                  <p className="text-gray-700">{review.comment}</p>
                </li>
              ))}
            </ul>
          ) : (
            <p>No reviews yet.</p>
          )}
        </div>
      </div>
      <Footer />
    </>
  );
}

export default TutorProfilePage;