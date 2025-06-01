import React from 'react';
import { Link, useParams } from 'react-router-dom';

const PaymentFailurePage = () => {
    const { bookingId } = useParams(); // Optional: Get bookingId from URL parameters

    return (
        <div>
            <h2>Payment Failed</h2>
            <p>Unfortunately, your payment could not be processed. Please try again.</p>
            {/* Optional: Display a more specific error message if you have one */}
            {/* <p>Error: [Display error message here if available]</p> */}

            {/* Link back to the payment page or booking details page */}
            {bookingId ? (
                <Link to={`/payment/${bookingId}`}>
                    <button>Try Payment Again</button>
                </Link>
            ) : (
                <Link to="/dashboard"> {/* Or a booking details page if you have one */}
                    <button>Go to Dashboard</button>
                </Link>
            )}
        </div>
    );
};

export default PaymentFailurePage;