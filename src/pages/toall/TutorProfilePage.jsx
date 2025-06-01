have jsx
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Navbar from '../Components/Navbar'; // Adjust the import path as necessary
import Footer from '../Components/Footer'; // Adjust the import path as necessary
import authenticatedFetch from '../../utils/authenticatedFetch'; // Adjust the path
import { useAuth } from '../../hooks/useAuth'; // Import the useAuth hook
import { motion } from 'framer-motion';
import Alert from '../../components/Alert'; // Import the Alert component
import Spinner from '../../components/Spinner'; // Import the Spinner component

function TutorProfilePage() {
  const { tutorId } = useParams(); // Get tutorId from URL parameters
  const [tutorUser, setTutorUser] = useState(null);
  const [tutorProfile, setTutorProfile] = useState(null);
  const [availableSlots, setAvailableSlots] = useState([]);
  const [selectedDate, setSelectedDate] = useState(''); // State for selecting a date
  const [bookingLoading, setBookingLoading] = useState(false); // Loading state for booking
  const [bookingError, setBookingError] = useState(''); // Error state for booking
  const [loading, setLoading] = useState(true); // Overall loading state
  const [error, setError] = useState(''); // Overall error state
  const [availableSlotsError, setAvailableSlotsError] = useState(''); // Error state for available slots
  const [availableSlotsLoading, setAvailableSlotsLoading] = useState(false); // Loading state for available slots
  const [tutorReviews, setTutorReviews] = useState([]); // State to store tutor's reviews
  const [tutorResources, setTutorResources] = useState([]); // State to store tutor's resources
  const [reviewsLoading, setReviewsLoading] = useState(true); // Loading state for reviews
  const [resourcesLoading, setResourcesLoading] = useState(true); // Loading state for resources

 const [isOverallAlertVisible, setIsOverallAlertVisible] = useState(true);
 const [isBookingAlertVisible, setIsBookingAlertVisible] = useState(true);

  const { currentUser } = useAuth(); // Get the current user from the useAuth hook

  useEffect(() => {
    const fetchTutorInitialData = async () => {
      setLoading(true);
 setError('');
      try {
        // Fetch basic user details for the tutor
        const userResponse = await authenticatedFetch(`/api/users/${tutorId}`);
        if (userResponse.ok) {
          const userData = await userResponse.json();
          setTutorUser(userData);
        } else {
          throw new Error('Failed to fetch tutor user data.');
        }

        // Fetch tutor-specific profile details
        const profileResponse = await authenticatedFetch(`/api/users/${tutorId}/tutor`);
        if (profileResponse.ok) {
          const profileData = await profileResponse.json();
          setTutorProfile(profileData);
        } else {
          throw new Error('Failed to fetch tutor profile data.');
        }

      } catch (error) {
        console.error('Error fetching tutor initial data:', error);
        setError(error.message || 'An error occurred while fetching tutor data.');
    };
 finally {
 setLoading(false);
 }
};

 fetchTutorInitialData();
    fetchTutorResources();
    fetchTutorReviews();
 setIsOverallAlertVisible(true); // Show overall error alert initially

 }, [tutorId]); // Refetch when tutorId changes

    const fetchResources = async () => {
      setResourcesLoading(true);
      setError(null); // Clear overall error before fetching resources
      try {
        const resourcesResponse = await authenticatedFetch(`/api/resources/user/${tutorId}`);
        if (resourcesResponse.ok) {
          const resourcesData = await resourcesResponse.json();
          setTutorResources(resourcesData.content); // Assuming backend returns a Page object with 'content'
 setError(null); // Clear overall error on successful resource fetch
        } else {
          const errorData = await resourcesResponse.json();
 setError(`Failed to fetch resources: ${errorData.message}`); // Set overall error
        }
      } catch (error) {
        console.error('Error fetching resources:', error);
 setError('An error occurred while fetching resources.'); // Set overall error
 } finally {
        setResourcesLoading(false);
      }
    };

    const fetchReviews = async () => {
      setReviewsLoading(true);
      try {
        const reviewsResponse = await authenticatedFetch(`/api/reviews/user/${tutorId}`);
        if (reviewsResponse.ok) {
          const reviewsData = await reviewsResponse.json();
          setTutorReviews(reviewsData.content); // Assuming backend returns a Page object with 'content'
 setError(null); // Clear overall error on successful review fetch
        } else {
          const errorData = await reviewsResponse.json();
 setError(`Failed to fetch reviews: ${errorData.message}`); // Set overall error
        }
      } catch (error) {
        console.error('Error fetching reviews:', error);
 setError('An error occurred while fetching reviews.'); // Set overall error
      } finally {
        setReviewsLoading(false);
      }
    };


  const fetchAvailableSlots = async (date) => {
    setAvailableSlotsLoading(true);
    setAvailableSlotsError(null);
 setAvailableSlots([]); // Clear previous slots
    try {
      const slotsResponse = await authenticatedFetch(`/api/tutors/${tutorId}/available-slots?date=${date}`); // Include date parameter
      if (slotsResponse.ok) {
        const slotsData = await slotsResponse.json();
        setAvailableSlots(slotsData); // Assuming the backend returns a list of slots
      } else {
        setAvailableSlots([]); // Clear slots on error
        const errorData = await slotsResponse.json();
 setAvailableSlotsError(errorData.message || 'Failed to fetch available slots.');
      }
    } catch (error) {
      console.error('Error fetching available slots:', error);
      setAvailableSlotsError('An error occurred while fetching available slots.');
 } finally {
      setAvailableSlotsLoading(false);
    }
  }



  const handleDateChange = (e) => {
    const newDate = e.target.value;
    setSelectedDate(newDate);
    fetchAvailableSlots(newDate); // Fetch slots for the selected date
  };

    // Function to handle booking a session
    const handleBookSession = async (sessionId) => {
 setBookingLoading(true);
 setBookingError('');
 setBookingSuccess('');
 setIsBookingAlertVisible(true);
        // Ensure currentUser and sessionId are available
        if (!currentUser || !currentUser.id || !sessionId) {
 setBookingError("Missing user or session information.");
 setBookingLoading(false);
            return;
        }

        try {
 const response = await authenticatedFetch('/api/bookings', {
 method: 'POST',
 headers: {
 'Content-Type': 'application/json',
 },
 body: JSON.stringify({
 studentId: currentUser.id, // Get studentId from authenticated user
 tutorId: tutorId, // Get tutorId from URL params
 sessionId: sessionId, // Pass the selected sessionId
 }),
 });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to book session.');
            }
            const createdBooking = await response.json(); // Parse the response body
            console.log('Booking created successfully:', createdBooking); // Log or use the booking ID
            setBookingSuccess('Session booked successfully!');
 setBookingError(''); // Clear any previous booking error
        } catch (error) {
            setBookingError(error.message || 'An error occurred during booking. Please try again.');
 setBookingSuccess(''); // Clear success message on error
 setIsBookingAlertVisible(true); // Show booking error alert
        } finally {
 // Ensure both success and error alerts can be closed independently
 // The state update for booking success/error will trigger re-render
        } finally {
            setBookingLoading(false);
        }
    };

    const handleEditResource = (resourceId) => {
        // Implement logic to handle resource editing
        // This might involve navigating to an edit page or opening a modal
        console.log('Edit resource with ID:', resourceId);
        // Example: navigate(`/resource/edit/${resourceId}`);
    };

    const handleDeleteResource = async (resourceId) => {
        if (window.confirm('Are you sure you want to delete this resource?')) {
            try {
                const response = await authenticatedFetch(`/api/resources/${resourceId}`, {
                    method: 'DELETE',
                });
                if (response.ok) {
                    // Remove the deleted resource from the state
                    setTutorResources(tutorResources.filter(resource => resource.id !== resourceId));
                    console.log('Resource deleted successfully.');
                    // Optionally show a success message
                } else {
                    const errorData = await response.json();
                    console.error('Failed to delete resource:', errorData.message);
                    // Optionally show an error message
                }
            } catch (error) {
                console.error('Error deleting resource:', error);
                // Optionally show an error message
            }
        }
    };

    // Check if the current user is the tutor whose profile is being viewed
    const isCurrentUserTutor = currentUser && tutorUser && currentUser.id === tutorUser.id;

    // Function to fetch tutor's resources (can be called on initial load and after resource actions)
    const fetchTutorResources = async () => {
        setResourcesLoading(true);
        try {
            const resourcesResponse = await authenticatedFetch(`/api/resources/user/${tutorId}`);
            if (resourcesResponse.ok) {
                const resourcesData = await resourcesResponse.json();
                setTutorResources(resourcesData.content); // Assuming backend returns a Page object with 'content'
 setError(null); // Clear overall error on successful resource fetch
            } else {
                setTutorResources([]); // Clear resources on error
                const errorData = await resourcesResponse.json();
                console.error('Failed to fetch resources:', errorData.message);
            }
        } catch (error) {
            console.error('Error fetching resources:', error);
        } finally {
            setResourcesLoading(false);
        }
    };

    // Call fetch resources on component mount
 useEffect(() => {
        fetchTutorResources();
    }, [tutorId]); // Refetch when tutorId changes

    // Function to fetch tutor's reviews
    const fetchTutorReviews = async () => {
        setReviewsLoading(true);
        try {
            const reviewsResponse = await authenticatedFetch(`/api/reviews/user/${tutorId}`);
            if (reviewsResponse.ok) {
                const reviewsData = await reviewsResponse.json();
                setTutorReviews(reviewsData.content); // Assuming backend returns a Page object with 'content'
 setError(null); // Clear overall error on successful review fetch
            } else {
                setTutorReviews([]); // Clear reviews on error
                const errorData = await reviewsResponse.json();
                console.error('Failed to fetch reviews:', errorData.message);
            }
        } catch (error) {
            console.error('Error fetching reviews:', error);
        } finally {
            setReviewsLoading(false);
        }
    };

    // Call fetch reviews on component mount
 const handleCloseOverallAlert = () => {
 setIsOverallAlertVisible(false);
    };

    const handleCloseBookingAlert = () => {
 setIsBookingAlertVisible(false);
    };

 useEffect(() => {
        fetchTutorReviews();
    }, [tutorId]); // Refetch when tutorId changes



 return (
 <motion.div
 initial={{ opacity: 0 }} // Initial state (hidden)
 animate={{ opacity: 1 }} // Animate to this state (visible)
 transition={{ duration: 0.5 }} // Animation duration
 className="bg-gray-100 min-h-screen" // Added overall background
 >
      <Navbar />
 <div className="container mx-auto py-8 px-4"> {/* Added horizontal padding */}
 <h1 className="text-3xl font-bold mb-6 text-center">Tutor Profile</h1> {/* Styled heading */}
 {/* Overall Loading/Error for initial tutor data */}
        {loading && <Spinner />}
        {error && isOverallAlertVisible && (
          <Alert
            message={error}
            type="error"
            onClose={handleCloseOverallAlert}
          />
        )}

        {/* Content visible only when not loading and no overall error */}

        {/* Display tutor details if loaded */}
        {tutorUser && tutorProfile && (
          <>
            {/* About Tutor Section - Styled */}
            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">About {tutorUser.firstName} {tutorUser.lastName}</h2>
              <p className="text-gray-700 mb-2">
                <strong>Email:</strong> {tutorUser.email}
              </p>
              {/* Add other basic user details display - Style as needed */}

              <p className="text-gray-700 mb-2">
                <strong>Subjects:</strong> {tutorProfile.subjectsTaught}
              </p>
              <p className="text-gray-700 mb-2">
                <strong>Hourly Rate:</strong> <span className="text-green-600 font-bold">${tutorProfile.hourlyRate}</span> {/* Highlight hourly rate */}
              </p>
              {/* Add other tutor profile details display - Style as needed */}
            </div>

            {/* Available Time Slots Section - Styled */}
            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
              <h2 className="text-2xl font-semibold mb-4">Available Time Slots</h2>
              {/* Loading and Error for Available Slots */}              {availableSlotsLoading && <Spinner />}

              {availableSlotsError && (
                <Alert
                  message={`Error loading slots: ${availableSlotsError}`}
 type="error"
                />
              )}
              {/* Date Selection */}
              <div className="mb-4">
                <label htmlFor="date" className="block text-gray-700 text-sm font-bold mb-2">Select Date:</label>
                <input
                  type="date"
                  id="date"
                  value={selectedDate}
                  onChange={handleDateChange}
                  className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                />
              </div>

              {/* Available Slots List - Styled */}
              {!availableSlotsLoading && !availableSlotsError && availableSlots.length === 0 && selectedDate && (
                <p>No available slots for the selected date.</p>
              ) : (
                 !availableSlotsLoading && !availableSlotsError && (
                <ul className="divide-y divide-gray-200"> {/* Added divider */}
                  {availableSlots.map((slot) => (
                    <li key={slot.sessionId} className="py-3 flex justify-between items-center"> {/* Adjusted padding */}
                      <span className="text-gray-800">{slot.startTime} - {slot.endTime}</span> {/* Styled time text */}
                       {/* Booking Button */}
                       {currentUser?.role === 'STUDENT' && ( // Only show book button for students
                         <button
                          onClick={() => handleBookSession(slot.sessionId)}
                          disabled={bookingLoading} // Disable while booking is in progress
                          className="bg-blue-600 hover:bg-blue-700 text-white font-bold py-1 px-3 rounded text-xs disabled:opacity-50 disabled:cursor-not-allowed transition duration-200 ease-in-out" {/* Added disabled styles and transition */}
                        >
                          {bookingLoading ? 'Booking...' : 'Book Session'}
                        </button>
                       )}
                    </li>
                  ))}
 </ul>
                )
              )}

              {/* Display booking success message */}
              {bookingSuccess && isBookingAlertVisible && (
                <Alert
                  message={bookingSuccess}
                  type="success"
                  onClose={handleCloseBookingAlert}
                />
              )}
              {/* Display booking error message */}
              {bookingError && isBookingAlertVisible && (
                <Alert
                  message={bookingError}
                  type="error"
                  onClose={handleCloseBookingAlert}
                />
              )}

            </div>

          {/* Resources Section - Styled */}
        <div className="bg-white shadow-md rounded-lg p-6 mb-6">
          <h2 className="text-2xl font-semibold mb-4">Resources Uploaded by {tutorUser?.firstName}</h2>
          {/* Loading for Resources */}
          {resourcesLoading && <Spinner />}

          {/* Error for Resources (using overall error state) */}
          {/* If you had a dedicated resourceError state, you would use it here */}
          {error && !resourcesLoading && isOverallAlertVisible && (
            <Alert message={`Error loading resources: ${error}`} type="error" onClose={handleCloseOverallAlert} />
          )}

          {!resourcesLoading && !error && tutorResources.length === 0 && (
            <p>No resources uploaded by this tutor yet.</p>
          ) : (
             !resourcesLoading && !error && (
            <ul className="divide-y divide-gray-200"> {/* Added divider */}
              {tutorResources.map(resource => (
                <li key={resource.id} className="py-3 flex justify-between items-center"> {/* Adjusted padding */}
                  <a href={resource.url} target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:underline">
                    {resource.fileName}
                  </a>
                  {/* Display other resource details if needed */}
                  {isCurrentUserTutor && ( // Only show edit/delete for the tutor
                    <div>
                      <button
                        onClick={() => handleEditResource(resource.id)}
 className="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-1 px-2 rounded text-xs mr-2 transition duration-200 ease-in-out" {/* Added transition */}
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => handleDeleteResource(resource.id)}
 className="bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-2 rounded text-xs transition duration-200 ease-in-out" {/* Added transition */}
                      >
                        Delete
                      </button>
                    </div>
                  )}
                </li>
              ))}
            </ul>
            )
          )}
        </div>

          {/* Reviews Section - Styled */}
          <div className="bg-white shadow-md rounded-lg p-6">
            <h2 className="text-2xl font-semibold mb-4">Reviews Written by Students</h2>
            {/* Loading for Reviews */}
            {reviewsLoading && <Spinner />}

            {/* Error for Reviews (using overall error state) */}
            {/* If you had a dedicated reviewError state, you would use it here */}
            {error && !reviewsLoading && isOverallAlertVisible && (
              <Alert message={`Error loading reviews: ${error}`} type="error" onClose={handleCloseOverallAlert} />
            )}
            {/* Display reviews */}
            {!reviewsLoading && !error && tutorReviews.length === 0 ? (
              <p>No reviews yet for this tutor.</p>
            ) : (
               !reviewsLoading && !error && (
                <ul className="divide-y divide-gray-200"> {/* Added divider */}
                {tutorReviews.map((review) => (
                  <li key={review.id} className="py-4 last:border-b-0"> {/* Adjusted padding */}
                    <div className="flex justify-between items-center mb-2">
                      {/* Assuming review object has student details or name */}
                      <span className="font-semibold">{review.studentName || 'Anonymous'}</span> {/* Example: Use a studentName field if available */}
                      <span className="text-yellow-500">{Array(review.rating).fill('⭐').join('')}</span>
                    </div>
                    <p className="text-gray-700">{review.comment}</p>
                  </li>
                ))}
              </ul>
               )
            )}
          </div>

          </>
        )}


 </div>
      <Footer />
    </>
  );
}

export default TutorProfilePage;