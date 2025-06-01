jsx
import React, { useEffect, useState } from 'react';
import Navbar from '../Components/Navbar'; // Adjust the import path as needed
import { motion } from 'framer-motion';
import Footer from '../Components/Footer'; // Adjust the import path as needed
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import authenticatedFetch from '../../utils/authenticatedFetch'; // Adjust the path
import Spinner from '../components/Spinner'; // Import the Spinner component
import Alert from '../components/Alert'; // Import the Alert component

function SubjectPage() {
  // NOTE: SubjectPage is being repurposed for Session search and display.
  // The sessionId useParams is likely from a previous use case for displaying a single session.
  // If this page will only display search results, sessionId might not be needed from params.
  // However, keeping it for now in case it's used for other purposes on this page.
  const { sessionId } = useParams();
  const [resources, setResources] = useState([]);
  const [page, setPage] = useState(0); // Current page for reviews (0-indexed)
  const [size, setSize] = useState(5); // Items per page for reviews
  const [totalPages, setTotalPages] = useState(0); // Total number of pages for reviews

  // State for Review Filter Criteria
  const [filterRating, setFilterRating] = useState(''); // Filter by rating
  const [filterReviewStartDate, setFilterReviewStartDate] = useState(''); // Filter by review date range start
  const [filterReviewEndDate, setFilterReviewEndDate] = useState(''); // Filter by review date range end

  // State for Session Search/Filtering
  const [reviews, setReviews] = useState([]); // State to store session reviews
  const [reviewsLoading, setReviewsLoading] = useState(false); // Loading state for reviews
  const [reviewsError, setReviewsError] = useState(null); // Error state for reviews

  const [sessions, setSessions] = useState([]); // State to store sessions from search
  const [sessionsLoading, setSessionsLoading] = useState(false); // Loading state for sessions
  const [sessionsError, setSessionsError] = useState(null); // Error state for sessions

  // State for Tutor Search/Filtering
  const [tutors, setTutors] = useState([]); // State to store tutors from search
  const [tutorsLoading, setTutorsLoading] = useState(false); // Loading state for tutors
  const [tutorsError, setTutorsError] = useState(null); // Error state for tutors

    // State for overall error and alert visibility
    const [pageError, setPageError] = useState(null);
    const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    const fetchSessionResources = async () => {
      // setSessionsLoading(true); // Using sessionsLoading for resources, should use a separate state
      // setSessionsError(null); // Using sessionsError for resources, should use a separate state
      /* try {
        // Assuming the route provides a sessionId
        if (!sessionId) {
          setLoading(false);
          return;
        }

        const response = await authenticatedFetch(`/api/sessions/${sessionId}/resources`);
        if (response.ok && response.status !== 204) { // Check for no content status as well
          const data = await response.json();
          setResources(data); // Assuming the backend returns a list or array of resources
        } else if (response.status !== 204) {
          const errorData = await response.json(); // Added async await here
          setSessionsError(errorData.message || 'Failed to fetch session resources.'); // Using sessionsError, should use a separate state
        }
      } catch (error) {
        console.error('Error fetching session resources:', error);
        setSessionsError('An error occurred while fetching session resources.'); // Using sessionsError, should use a separate state
      } finally {
        setSessionsLoading(false); // Unset loading for resources, should use a separate state
      }
      // Fetch session reviews
    };
 console.log("Fetching session resources for session:", sessionId);
    fetchSessionResources();
  }, [sessionId, page, size]); // Refetch when sessionId, page, or size changes

    // Function to close the alert
    const closeAlert = () => {
        setShowAlert(false);
        setPageError(null); // Also clear the error state
    };

    // useEffect to fetch reviews based on sessionId and filters
    useEffect(() => {
             setReviewsLoading(true); // Set loading for reviews
             setReviewsError(null);
             try {
                 const queryParams = new URLSearchParams();
                 queryParams.append('sessionId', sessionId); // Always include sessionId
                 if (filterRating) queryParams.append('rating', filterRating);
                 if (filterReviewStartDate) queryParams.append('startDate', filterReviewStartDate);
                 if (filterReviewEndDate) queryParams.append('endDate', filterReviewEndDate);
                 queryParams.append('page', page);
                 queryParams.append('size', size);

                 const reviewsResponse = await authenticatedFetch(`/api/reviews/search?${queryParams.toString()}`);
                 if (reviewsResponse.ok) {
                     const reviewsData = await reviewsResponse.json();
                     setReviews(reviewsData.content);
                     setTotalPages(reviewsData.totalPages);
                 } else {
                     const reviewsErrorData = await reviewsResponse.json().catch(() => ({ message: 'Failed to parse review error.' }));
                     const errorMessage = reviewsErrorData.message || 'Failed to fetch session reviews.';
                     setReviewsError(errorMessage);
                 }
             } catch (reviewsFetchError) {
                 console.error('Error fetching session reviews:', reviewsFetchError);
                 setReviewsError('An error occurred while fetching session reviews.'); // Set reviewsError specifically for this fetch
             } finally {
                 setReviewsLoading(false); // Unset loading for reviews
             }
        };

        fetchSessionReviews();
    }, [sessionId, page, size, filterRating, filterReviewStartDate, filterReviewEndDate]); // Refetch when filters change

  // State for Filter Criteria
  const [filterSubject, setFilterSubject] = useState('');
  const [filterStartDate, setFilterStartDate] = useState('');
  const [filterEndDate, setFilterEndDate] = useState('');
  const [filterStatus, setFilterStatus] = useState(''); // e.g., 'SCHEDULED', 'COMPLETED', 'CANCELLED'

  // Function to handle session search
  const handleSearchSessions = async () => {
    setSessionsLoading(true);
    setSessionsError(null);
    try {
      const queryParams = new URLSearchParams();
      if (filterSubject) queryParams.append('subject', filterSubject);
      if (filterStartDate) queryParams.append('startDate', filterStartDate);
      if (filterEndDate) queryParams.append('endDate', filterEndDate);
      if (filterStatus) queryParams.append('status', filterStatus);
      
      // Add pagination parameters for the search results if needed,
      // currently assuming pagination for reviews only on this page.
      // If search results also need pagination, you'll need separate pagination state for sessions.
      // queryParams.append('page', sessionSearchPage);
      // queryParams.append('size', sessionSearchSize);

      const response = await authenticatedFetch(`/api/sessions/search?${queryParams.toString()}`);

      if (response.ok) {
        const data = await response.json();
        setSessions(data.content); // Assuming the backend returns a Page object with 'content'
        // If search results are paginated, set totalPages for sessions here as well.
        // setSessionSearchTotalPages(data.totalPages);
      } else {
        const errorData = await response.json();
        const errorMessage = errorData.message || 'Failed to search sessions.';
        setSessionsError(errorMessage); // Set sessionsError specifically for this search
    }
    } catch (error) {
      console.error('Error searching sessions:', error);
      setSessionsError('An error occurred while searching sessions.');
    } finally {
      setSessionsLoading(false);
    }
  };

  // State for Tutor Filter Criteria
  const [filterTutorSubject, setFilterTutorSubject] = useState('');

  // Function to handle tutor search
  const handleSearchTutors = async () => {
    setTutorsLoading(true);
    setTutorsError(null);
    try {
      const queryParams = new URLSearchParams();
      if (filterTutorSubject) queryParams.append('subject', filterTutorSubject);

      // Add pagination parameters for tutor search results if needed
      // queryParams.append('page', tutorSearchPage);
      // queryParams.append('size', tutorSearchSize);

      const response = await authenticatedFetch(`/api/tutors/search?${queryParams.toString()}`);

      if (response.ok) {
        const data = await response.json();
        setTutors(data.content); // Assuming the backend returns a Page object with 'content'
        // If search results are paginated, set totalPages for tutors here as well.
        // setTutorSearchTotalPages(data.totalPages);
      } else {
        const errorData = await response.json();
        const errorMessage = errorData.message || 'Failed to search tutors.';
        setTutorsError(errorMessage); // Set tutorsError specifically for this search
    }
    } catch (error) {
      console.error('Error searching tutors:', error);
      setTutorsError('An error occurred while searching tutors.');
    } finally {
      setTutorsLoading(false);
    }
  };
    const handlePageChange = (newPage) => {
        setPage(newPage);
    };

  return (
    <div>
      <Navbar />
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 0.5 }}>
      {/* Main Container */}
      <div className="container mx-auto px-4 py-8"> {/* Added container, centering, padding */}
      {/* Filter/Search UI */}
      <h2 className="text-2xl font-bold mb-4">Find Sessions</h2> {/* Styled heading */}
      <div className="flex flex-wrap gap-4 mb-6"> {/* Styled container with flexbox and spacing */}
        <input type="text" className="p-2 border rounded" placeholder="Subject" value={filterSubject} onChange={(e) => setFilterSubject(e.target.value)} /> {/* Styled input */}
        <input type="date" className="p-2 border rounded" placeholder="Start Date" value={filterStartDate} onChange={(e) => setFilterStartDate(e.target.value)} /> {/* Styled input */}
        <input type="date" className="p-2 border rounded" placeholder="End Date" value={filterEndDate} onChange={(e) => setFilterEndDate(e.target.value)} /> {/* Styled input */}
        {/* Add a dropdown for status if needed */}
        {/* <select value={filterStatus} onChange={(e) => setFilterStatus(e.target.value)}>
          <option value="">All Statuses</option>
          <option value="SCHEDULED">Scheduled</option>
          <option value="COMPLETED">Completed</option>
           <option value="CANCELLED">Cancelled</option>
        </select> */}
        <button className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 disabled:opacity-50" onClick={handleSearchSessions} disabled={sessionsLoading}>{sessionsLoading ? 'Searching...' : 'Search Sessions'}</button> {/* Styled button */}
      </div>

      {/* Tutor Search UI */}
      <h2>Find Tutors</h2>
      <input type="text" placeholder="Subject" value={filterTutorSubject} onChange={(e) => setFilterTutorSubject(e.target.value)} />
      <button onClick={handleSearchTutors} disabled={tutorsLoading}>{tutorsLoading ? 'Searching...' : 'Search Tutors'}</button>

      {/* Display Session Reviews */}
      {/* Review Filtering UI */}
        <div className="mb-6"> {/* Styled container with spacing */}
            <h4 className="text-xl font-semibold mb-3">Filter Reviews</h4> {/* Styled heading */}
            <select className="p-2 border rounded mr-2" value={filterRating} onChange={(e) => setFilterRating(e.target.value)}> {/* Styled select */}
                <option value="">All Ratings</option>
                {[1, 2, 3, 4, 5].map(rating => <option key={rating} value={rating}>{rating} Stars</option>)}
            </select>
            <input type="date" className="p-2 border rounded mr-2" placeholder="From Date" value={filterReviewStartDate} onChange={(e) => setFilterReviewStartDate(e.target.value)} /> {/* Styled input */}
            <input type="date" className="p-2 border rounded mr-2" placeholder="To Date" value={filterReviewEndDate} onChange={(e) => setFilterReviewEndDate(e.target.value)} /> {/* Styled input */}
 <button onClick={fetchSessionReviews} disabled={reviewsLoading}>{reviewsLoading ? 'Filtering...' : 'Filter Reviews'}</button> {/* Added a button to trigger review filtering */}
        </div> {/* Added closing div tag */}
      {loading ? (
        <Spinner />
      ) : reviewsError ? (
        <Alert message={reviewsError} type="error" onClose={() => setReviewsError(null)} /> // Use Alert for reviewsError
      ) : sessionsError ? (
        <Alert message={sessionsError} type="error" onClose={() => setSessionsError(null)} /> // Use Alert for sessionsError
      ) : reviews.length === 0 ? (
        // <p>No reviews yet for this session.</p> // Moved this message inside the review list section logic
      ) : (
        <ul className="space-y-4"> {/* Added spacing between list items */}
          {reviews.map(review => (
            <li key={review.id} className="p-4 border rounded shadow-sm"> {/* Styled list item */}
              <p><span className="font-semibold">Rating:</span> {review.rating}</p> {/* Styled text */}
              <p><span className="font-semibold">Comment:</span> {review.comment}</p> {/* Styled text */}
            </li>
          ))}
        </ul>
      )}
      {!reviewsLoading && !reviewsError && reviews.length === 0 && (
          <p>No reviews yet for this session.</p> // Display "No reviews" when not loading and no error
      )}
        {/* Pagination Controls for Reviews */}
            {totalPages > 1 && (
            <div className="flex justify-center items-center mt-6 space-x-4"> {/* Styled pagination container */}
                <button className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded disabled:opacity-50" disabled={page === 0} onClick={() => handlePageChange(page - 1)}>Previous</button> {/* Styled button */}
                <span>Page {page + 1} of {totalPages}</span>
                <button className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded disabled:opacity-50" disabled={page === totalPages - 1} onClick={() => handlePageChange(page + 1)}>Next</button> {/* Styled button */}
            </div>)}

      {/* Display Session Search Results */}
      <h3>Search Results</h3>
      {sessionsLoading && !sessionsError ? (
        <Spinner />
      ) : sessionsError && !reviewsError ? ( // Only show sessionsError if there is one and no reviewsError (to avoid multiple alerts)
        <p style={{ color: 'red' }}>Error: {sessionsError}</p>
      ) : sessions.length === 0 ? (
        <p>No sessions found matching your criteria.</p>
      ) : (
        <ul className="space-y-4 mt-4"> {/* Added spacing and margin-top */}
          {sessions.map(session => (
            <li key={session.id} className="p-4 border rounded shadow-sm"> {/* Styled list item */}
              {/* Display session details from search results */}
              <p><span className="font-semibold">Session:</span> {session.subject} - <span className="font-semibold">Status:</span> {session.status}</p> {/* Styled text */}
              {/* Add more details as needed */}
            </li>
          ))}
        </ul>
      )}
      {/* ... rest of your page content */}
      </div> {/* Closed main container div */}
 </motion.div>
    </div>
  );
}

export default SubjectPage;