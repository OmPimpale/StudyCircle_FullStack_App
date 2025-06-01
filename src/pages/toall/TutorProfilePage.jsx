import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router';

import authenticatedFetch from '../../utils/authenticatedFetch';
import { motion } from 'framer-motion';
import Alert from '../../components/Alert';
import Spinner from '../../components/Spinner';
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';
import useAuth from '../../hooks/useAuth';

function TutorProfilePage() {
  const { tutorId } = useParams();
  const [tutorUser, setTutorUser] = useState(null);
  const [tutorProfile, setTutorProfile] = useState(null);
  const [availableSlots, setAvailableSlots] = useState([]);
  const [selectedDate, setSelectedDate] = useState('');
  const [bookingLoading, setBookingLoading] = useState(false);
  const [bookingError, setBookingError] = useState('');
  const [bookingSuccess, setBookingSuccess] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [availableSlotsError, setAvailableSlotsError] = useState('');
  const [availableSlotsLoading, setAvailableSlotsLoading] = useState(false);
  const [tutorReviews, setTutorReviews] = useState([]);
  const [tutorResources, setTutorResources] = useState([]);
  const [reviewsLoading, setReviewsLoading] = useState(true);
  const [resourcesLoading, setResourcesLoading] = useState(true);
  const [isOverallAlertVisible, setIsOverallAlertVisible] = useState(true);
  const [isBookingAlertVisible, setIsBookingAlertVisible] = useState(true);

  const { currentUser } = useAuth();

  const handleCloseOverallAlert = () => setIsOverallAlertVisible(false);
  const handleCloseBookingAlert = () => setIsBookingAlertVisible(false);

  useEffect(() => {
    const fetchTutorInitialData = async () => {
      setLoading(true);
      setError('');
      try {
        const userResponse = await authenticatedFetch(`/api/users/${tutorId}`);
        if (userResponse.ok) {
          const userData = await userResponse.json();
          setTutorUser(userData);
        } else throw new Error('Failed to fetch tutor user data.');

        const profileResponse = await authenticatedFetch(`/api/users/${tutorId}/tutor`);
        if (profileResponse.ok) {
          const profileData = await profileResponse.json();
          setTutorProfile(profileData);
        } else throw new Error('Failed to fetch tutor profile data.');
      } catch (error) {
        console.error('Error fetching tutor data:', error);
        setError(error.message || 'Error loading tutor data.');
      } finally {
        setLoading(false);
      }
    };

    fetchTutorInitialData();
    fetchTutorResources();
    fetchTutorReviews();
  }, [tutorId]);

  const fetchAvailableSlots = async (date) => {
    setAvailableSlotsLoading(true);
    setAvailableSlotsError(null);
    setAvailableSlots([]);
    try {
      const response = await authenticatedFetch(`/api/tutors/${tutorId}/available-slots?date=${date}`);
      if (response.ok) {
        const data = await response.json();
        setAvailableSlots(data);
      } else {
        const errorData = await response.json();
        setAvailableSlotsError(errorData.message || 'Failed to fetch slots.');
      }
    } catch (error) {
      console.error('Error fetching slots:', error);
      setAvailableSlotsError('Error loading available slots.');
    } finally {
      setAvailableSlotsLoading(false);
    }
  };

  const handleDateChange = (e) => {
    const newDate = e.target.value;
    setSelectedDate(newDate);
    fetchAvailableSlots(newDate);
  };

  const handleBookSession = async (sessionId) => {
    setBookingLoading(true);
    setBookingError('');
    setBookingSuccess('');
    setIsBookingAlertVisible(true);
    if (!currentUser?.id || !sessionId) {
      setBookingError("Missing user or session info.");
      setBookingLoading(false);
      return;
    }
    try {
      const response = await authenticatedFetch('/api/bookings', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          studentId: currentUser.id,
          tutorId,
          sessionId
        })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Booking failed.');
      }
      await response.json();
      setBookingSuccess('Session booked successfully!');
    } catch (error) {
      setBookingError(error.message || 'Booking failed.');
    } finally {
      setBookingLoading(false);
    }
  };

  const fetchTutorResources = useCallback(async () => {
    setResourcesLoading(true);
    try {
      const res = await authenticatedFetch(`/api/resources/user/${tutorId}`);
      if (res.ok) {
        const data = await res.json();
        setTutorResources(data.content);
      } else {
        const err = await res.json();
        console.error('Fetch resource error:', err.message);
      }
    } catch (err) {
      console.error('Error:', err);
    } finally {
      setResourcesLoading(false);
    }
  }, [tutorId]);

  const fetchTutorReviews = useCallback(async () => {
    setReviewsLoading(true);
    try {
      const res = await authenticatedFetch(`/api/reviews/user/${tutorId}`);
      if (res.ok) {
        const data = await res.json();
        setTutorReviews(data.content);
      } else {
        const err = await res.json();
        console.error('Fetch review error:', err.message);
      }
    } catch (err) {
      console.error('Error:', err);
    } finally {
      setReviewsLoading(false);
    }
  }, [tutorId]);

  return (
    <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ duration: 0.5 }} className="bg-gray-100 min-h-screen">
      <Navbar />
      <div className="container mx-auto py-8 px-4">
        <h1 className="text-3xl font-bold mb-6 text-center">Tutor Profile</h1>
        {loading && <Spinner />}
        {error && isOverallAlertVisible && <Alert message={error} type="error" onClose={handleCloseOverallAlert} />}

        {tutorUser && tutorProfile && (
          <>
            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">About {tutorUser.firstName} {tutorUser.lastName}</h2>
              <p className="text-gray-700 mb-2"><strong>Email:</strong> {tutorUser.email}</p>
              <p className="text-gray-700 mb-2"><strong>Subjects:</strong> {tutorProfile.subjectsTaught}</p>
              <p className="text-gray-700 mb-2"><strong>Hourly Rate:</strong> <span className="text-green-600 font-bold">${tutorProfile.hourlyRate}</span></p>
            </div>

            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">Available Time Slots</h2>
              {availableSlotsLoading && <Spinner />}
              {availableSlotsError && <Alert message={`Error: ${availableSlotsError}`} type="error" />}

              <div className="mb-4">
                <label htmlFor="date" className="block text-sm font-bold mb-2">Select Date:</label>
                <input type="date" id="date" value={selectedDate} onChange={handleDateChange} className="shadow border rounded w-full py-2 px-3" />
              </div>

              {!availableSlotsLoading && !availableSlotsError && availableSlots.length === 0 && selectedDate && <p>No slots available.</p>}

              {!availableSlotsLoading && !availableSlotsError && availableSlots.length > 0 && (
                <ul className="divide-y divide-gray-200">
                  {availableSlots.map(slot => (
                    <li key={slot.sessionId} className="py-3 flex justify-between items-center">
                      <span>{slot.startTime} - {slot.endTime}</span>
                      {currentUser?.role === 'STUDENT' && (
                        <button onClick={() => handleBookSession(slot.sessionId)} disabled={bookingLoading} className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-1 px-3 rounded text-xs">
                          {bookingLoading ? 'Booking...' : 'Book Session'}
                        </button>
                      )}
                    </li>
                  ))}
                </ul>
              )}

              {bookingSuccess && isBookingAlertVisible && <Alert message={bookingSuccess} type="success" onClose={handleCloseBookingAlert} />}
              {bookingError && isBookingAlertVisible && <Alert message={bookingError} type="error" onClose={handleCloseBookingAlert} />}
            </div>

            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">Resources</h2>
              {resourcesLoading && <Spinner />}
              {!resourcesLoading && tutorResources.length === 0 && <p>No resources uploaded.</p>}
              <ul className="divide-y divide-gray-200">
                {tutorResources.map(resource => (
                  <li key={resource.id} className="py-2">{resource.title}</li>
                ))}
              </ul>
            </div>

            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">Reviews</h2>
              {reviewsLoading && <Spinner />}
              {!reviewsLoading && tutorReviews.length === 0 && <p>No reviews yet.</p>}
              <ul className="divide-y divide-gray-200">
                {tutorReviews.map(review => (
                  <li key={review.id} className="py-2">{review.comment}</li>
                ))}
              </ul>
            </div>
          </>
        )}
      </div>
      <Footer />
    </motion.div>
  );
}

export default TutorProfilePage;