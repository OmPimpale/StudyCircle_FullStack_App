import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router';

import authenticatedFetch from '../../utils/authenticatedFetch'; // Adjust the path
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';
import useAuth from '../../hooks/useAuth';


function PaymentPage() {
  const { bookingId } = useParams(); // Get bookingId from URL parameters
    const navigate = useNavigate();
    const { currentUser } = useAuth();

    const [bookingDetails, setBookingDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [paymentLoading, setPaymentLoading] = useState(false);
    const [paymentError, setPaymentError] = useState(null);
    const [paymentSuccess, setPaymentSuccess] = useState(false);

    // State for payment information (will depend on your payment gateway)
    const [cardNumber, setCardNumber] = useState('');
    const [expiryDate, setExpiryDate] = useState('');
    const [cvv, setCvv] = useState('');
    // ... other payment details as needed by your gateway

    useEffect(() => {
        const fetchBookingDetails = async () => {
            if (!bookingId) {
                setError("Booking ID not provided.");
                setLoading(false);
                return;
            }

            setLoading(true);
            setError(null);

            try {
                // Fetch booking details from the backend to display summary and amount
                const response = await authenticatedFetch(`/api/bookings/${bookingId}`);
                if (response.ok) {
                    const data = await response.json();
                    setBookingDetails(data);
                } else {
                    const errorData = await response.json();
                    setError(errorData.message || 'Failed to fetch booking details.');
                }
            } catch (error) {
                console.error('Error fetching booking details:', error);
                setError('An error occurred while fetching booking details.');
            } finally {
                setLoading(false);
            }
        };

        fetchBookingDetails();
    }, [bookingId]); // Refetch when bookingId changes

    const handlePaymentSubmit = async (e) => {
        e.preventDefault();

        if (!bookingDetails) {
            setPaymentError("Booking details not loaded.");
            return;
        }

        setPaymentLoading(true);
        setPaymentError(null);
        setPaymentSuccess(false);

        try {
            // --- Payment Gateway Frontend Interaction Here ---
            // Use your payment gateway's frontend library to securely collect payment details and get a token.
            const paymentToken = "placeholder_payment_token"; // Replace with actual token from gateway

            // Send the payment token and booking ID to your backend
            const response = await authenticatedFetch('/api/payments/process', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    bookingId: bookingId,
                    paymentToken: paymentToken, // Send the token from the gateway
                    // Include other details your backend needs (e.g., amount, currency - though backend might get this from booking)
                }),
            });

            if (response.ok) {
                setPaymentSuccess(true);
                // Redirect to a success page or show success message
                 navigate(`/payment-success/${bookingId}`); // Example redirect
            } else {
                const errorData = await response.json();
                setPaymentError(errorData.message || 'Payment failed.');
            }
        } catch (error) {
            console.error('Payment submission error:', error);
            setPaymentError('An error occurred during payment. Please try again.');
        } finally {
            setPaymentLoading(false);
        }
    };

    if (loading) {
        return <div>Loading booking details...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!bookingDetails) {
        return <div>Booking not found.</div>;
    }

  return (
    <>
      <Navbar />
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-2xl font-bold mb-4">Complete Your Booking Payment</h1>
        {/* Display booking summary and amount */}
            <p>Booking ID: {bookingDetails.id}</p>
            {/* Assuming bookingDetails includes session details and amount */}
            <p>Session Subject: {bookingDetails.session?.subject}</p> {/* Use optional chaining */}
            {/* Assuming amount is available and is a number, format it */}
            <p>Amount: ${bookingDetails.amount ? bookingDetails.amount.toFixed(2) : 'N/A'}</p>

            <h3>Payment Information</h3>
            <form onSubmit={handlePaymentSubmit}>
                {/* --- Payment Gateway Frontend UI Elements Here --- */}
                {/* Use your payment gateway's provided UI elements (e.g., hosted fields, drop-in UI) */}
                {/* Example using simple input fields (replace with secure elements from your gateway) */}
                <div>
                    <label htmlFor="cardNumber">Card Number:</label>
                    <input
                        type="text"
                        id="cardNumber"
                        value={cardNumber}
                        onChange={(e) => setCardNumber(e.target.value)}
                        required
                    />
                </div>
                 <div>
                    <label htmlFor="expiryDate">Expiry Date:</label>
                    <input
                        type="text"
                        id="expiryDate"
                        value={expiryDate}
                        onChange={(e) => setExpiryDate(e.target.value)}
                        placeholder="MM/YY"
                        required
                    />
                </div>
                 <div>
                    <label htmlFor="cvv">CVV:</label>
                    <input
                        type="text"
                        id="cvv"
                        value={cvv}
                        onChange={(e) => setCvv(e.target.value)}
                        required
                    />
                </div>
                {/* ... other payment input fields ... */}
                 {/* --- End of Payment Gateway Frontend UI Elements --- */}


                <button type="submit" disabled={paymentLoading}>
                    {paymentLoading ? 'Processing Payment...' : 'Pay Now'}
                </button>
            </form>

            {/* Display payment loading, error, or success messages */}
            {paymentLoading && <p>Processing your payment...</p>}
            {paymentError && <p style={{ color: 'red' }}>Error: {paymentError}</p>}
            {paymentSuccess && <p style={{ color: 'green' }}>Payment successful!</p>}

      </div>
      <Footer />
    </>
  );
}

export default PaymentPage;