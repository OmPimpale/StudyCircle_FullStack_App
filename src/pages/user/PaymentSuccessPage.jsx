import React from 'react';
import { Link, useParams } from 'react-router-dom';

const PaymentSuccessPage = () => {
    const { bookingId } = useParams(); // Optional: Get bookingId from URL

    return (
        <div>
            <h2>Payment Successful!</h2>
            <p>Your payment was processed successfully.</p>
            {bookingId && (
                <p>Booking ID: {bookingId}</p>
            )}
            <Link to="/dashboard">Go to Dashboard</Link>
        </div>
    );
};

export default PaymentSuccessPage;