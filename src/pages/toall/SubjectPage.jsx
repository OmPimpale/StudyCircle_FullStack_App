import React, { useEffect, useState } from 'react';

import { motion } from 'framer-motion';
import { useParams } from 'react-router';
import authenticatedFetch from '../../utils/authenticatedFetch';
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';
import Spinner from '../../components/Spinner';
import Alert from '../../components/Alert';

function SubjectPage() {
  const { sessionId } = useParams();
  const [resources, setResources] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);
  const [totalPages, setTotalPages] = useState(0);

  const [filterRating, setFilterRating] = useState('');
  const [filterReviewStartDate, setFilterReviewStartDate] = useState('');
  const [filterReviewEndDate, setFilterReviewEndDate] = useState('');

  const [reviews, setReviews] = useState([]);
  const [reviewsLoading, setReviewsLoading] = useState(false);
  const [reviewsError, setReviewsError] = useState(null);

  const [sessions, setSessions] = useState([]);
  const [sessionsLoading, setSessionsLoading] = useState(false);
  const [sessionsError, setSessionsError] = useState(null);

  const [tutors, setTutors] = useState([]);
  const [tutorsLoading, setTutorsLoading] = useState(false);
  const [tutorsError, setTutorsError] = useState(null);

  const [pageError, setPageError] = useState(null);
  const [showAlert, setShowAlert] = useState(false);

  const [filterSubject, setFilterSubject] = useState('');
  const [filterStartDate, setFilterStartDate] = useState('');
  const [filterEndDate, setFilterEndDate] = useState('');
  const [filterStatus, setFilterStatus] = useState('');

  const [filterTutorSubject, setFilterTutorSubject] = useState('');

  const closeAlert = () => {
    setShowAlert(false);
    setPageError(null);
  };

  useEffect(() => {
    const fetchSessionResources = async () => {
      console.log("Fetching session resources for session:", sessionId);
      try {
        if (!sessionId) return;

        const response = await authenticatedFetch(`/api/sessions/${sessionId}/resources`);
        if (response.ok && response.status !== 204) {
          const data = await response.json();
          setResources(data);
        } else if (response.status !== 204) {
          const errorData = await response.json();
          setSessionsError(errorData.message || 'Failed to fetch session resources.');
        }
      } catch (error) {
        console.error('Error fetching session resources:', error);
        setSessionsError('An error occurred while fetching session resources.');
      }
    };

    fetchSessionResources();
  }, [sessionId]);

  useEffect(() => {
    const fetchSessionReviews = async () => {
      setReviewsLoading(true);
      setReviewsError(null);
      try {
        const queryParams = new URLSearchParams();
        queryParams.append('sessionId', sessionId);
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
          setReviewsError(reviewsErrorData.message || 'Failed to fetch session reviews.');
        }
      } catch (error) {
        console.error('Error fetching session reviews:', error);
        setReviewsError('An error occurred while fetching session reviews.');
      } finally {
        setReviewsLoading(false);
      }
    };

    fetchSessionReviews();
  }, [sessionId, page, size, filterRating, filterReviewStartDate, filterReviewEndDate]);

  const handleSearchSessions = async () => {
    setSessionsLoading(true);
    setSessionsError(null);
    try {
      const queryParams = new URLSearchParams();
      if (filterSubject) queryParams.append('subject', filterSubject);
      if (filterStartDate) queryParams.append('startDate', filterStartDate);
      if (filterEndDate) queryParams.append('endDate', filterEndDate);
      if (filterStatus) queryParams.append('status', filterStatus);

      const response = await authenticatedFetch(`/api/sessions/search?${queryParams.toString()}`);

      if (response.ok) {
        const data = await response.json();
        setSessions(data.content);
      } else {
        const errorData = await response.json();
        setSessionsError(errorData.message || 'Failed to search sessions.');
      }
    } catch (error) {
      console.error('Error searching sessions:', error);
      setSessionsError('An error occurred while searching sessions.');
    } finally {
      setSessionsLoading(false);
    }
  };

  const handleSearchTutors = async () => {
    setTutorsLoading(true);
    setTutorsError(null);
    try {
      const queryParams = new URLSearchParams();
      if (filterTutorSubject) queryParams.append('subject', filterTutorSubject);

      const response = await authenticatedFetch(`/api/tutors/search?${queryParams.toString()}`);

      if (response.ok) {
        const data = await response.json();
        setTutors(data.content);
      } else {
        const errorData = await response.json();
        setTutorsError(errorData.message || 'Failed to search tutors.');
      }
    } catch (error) {
      console.error('Error searching tutors:', error);
      setTutorsError('An error occurred while searching tutors.');
    } finally {
      setTutorsLoading(false);
    }
  };

  return (
    <div>
      <Navbar />
      <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ duration: 0.5 }}>
        <div className="container mx-auto px-4 py-8">
          <h2 className="text-2xl font-bold mb-4">Find Sessions</h2>
          <div className="flex flex-wrap gap-4 mb-6">
            <input type="text" className="p-2 border rounded" placeholder="Subject" value={filterSubject} onChange={(e) => setFilterSubject(e.target.value)} />
            <input type="date" className="p-2 border rounded" value={filterStartDate} onChange={(e) => setFilterStartDate(e.target.value)} />
            <input type="date" className="p-2 border rounded" value={filterEndDate} onChange={(e) => setFilterEndDate(e.target.value)} />
            <button className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 disabled:opacity-50" onClick={handleSearchSessions} disabled={sessionsLoading}>{sessionsLoading ? 'Searching...' : 'Search Sessions'}</button>
          </div>

          <h2>Find Tutors</h2>
          <input type="text" placeholder="Subject" value={filterTutorSubject} onChange={(e) => setFilterTutorSubject(e.target.value)} />
          <button onClick={handleSearchTutors} disabled={tutorsLoading}>{tutorsLoading ? 'Searching...' : 'Search Tutors'}</button>

          <div className="mb-6">
            <h4 className="text-xl font-semibold mb-3">Filter Reviews</h4>
            <select className="p-2 border rounded mr-2" value={filterRating} onChange={(e) => setFilterRating(e.target.value)}>
              <option value="">All Ratings</option>
              {[1, 2, 3, 4, 5].map(rating => <option key={rating} value={rating}>{rating} Stars</option>)}
            </select>
            <input type="date" className="p-2 border rounded mr-2" value={filterReviewStartDate} onChange={(e) => setFilterReviewStartDate(e.target.value)} />
            <input type="date" className="p-2 border rounded mr-2" value={filterReviewEndDate} onChange={(e) => setFilterReviewEndDate(e.target.value)} />
          </div>

          {reviewsLoading ? (
            <Spinner />
          ) : reviewsError ? (
            <Alert message={reviewsError} type="error" onClose={() => setReviewsError(null)} />
          ) : (
            <ul className="space-y-4">
              {reviews.map(review => (
                <li key={review.id} className="p-4 border rounded shadow-sm">
                  <p><strong>Rating:</strong> {review.rating}</p>
                  <p><strong>Comment:</strong> {review.comment}</p>
                </li>
              ))}
            </ul>
          )}

          {!reviewsLoading && !reviewsError && reviews.length === 0 && (
            <p>No reviews yet for this session.</p>
          )}
        </div>
      </motion.div>
      <Footer />
    </div>
  );
}

export default SubjectPage;