import React, { useState } from 'react';
import authenticatedFetch from '../utils/authenticatedFetch'; // Adjust the path

const ReviewForm = ({ sessionId, userId, onSubmitSuccess }) => {
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(false);

        if (rating < 1 || rating > 5) {
            setError('Rating must be between 1 and 5.');
            setLoading(false);
            return;
        }

        try {
            const response = await authenticatedFetch('/api/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    sessionId: sessionId,
                    userId: userId, // Reviewer's user ID
                    rating: rating,
                    comment: comment,
                }),
            });

            if (response.ok) {
                setSuccess(true);
                setRating(0); // Clear form on success
                setComment('');
                if (onSubmitSuccess) {
                    onSubmitSuccess(); // Call a callback function if provided
                }
            } else {
                const errorData = await response.json();
                setError(errorData.message || 'Failed to submit review.');
            }
        } catch (error) {
            console.error('Review submission error:', error);
            setError('An error occurred while submitting the review. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h3>Leave a Review</h3>
            <div>
                <label htmlFor="rating">Rating (1-5):</label>
                <input
                    type="number"
                    id="rating"
                    min="1"
                    max="5"
                    value={rating}
                    onChange={(e) => setRating(parseInt(e.target.value))}
                    required
                />
            </div>
            <div>
                <label htmlFor="comment">Comment:</label>
                <textarea
                    id="comment"
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    rows="4"
                    cols="50"
                ></textarea>
            </div>
            <button type="submit" disabled={loading}>
                {loading ? 'Submitting...' : 'Submit Review'}
            </button>

            {error && <p style={{ color: 'red' }}>Error: {error}</p>}
            {success && <p style={{ color: 'green' }}>Review submitted successfully!</p>}
        </form>
    );
};

export default ReviewForm;