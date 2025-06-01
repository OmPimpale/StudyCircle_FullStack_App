import React, { useState, useEffect } from 'react';
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';
import { useAuth } from '../../hooks/useAuth'; // Import useAuth hook
import authenticatedFetch from '../../utils/authenticatedFetch'; // Import authenticatedFetch utility
import Alert from '../../components/Alert'; // Import Alert component

const Dashboard = () => {
  const { currentUser, loading: authLoading } = useAuth(); // Get currentUser and authLoading from useAuth

  const [userData, setUserData] = useState(null);
  const [error, setError] = useState(null);
  const [userRole, setUserRole] = useState(null); // State for user role
  const [notifications, setNotifications] = useState([]); // State for notifications
  const [bookings, setBookings] = useState([]); // State for bookings
  const [bookingsLoading, setBookingsLoading] = useState(true); // State for bookings loading
  const [bookingsError, setBookingsError] = useState(null); // State for bookings errors
  const [page, setPage] = useState(0); // Current page (0-indexed)
  const [size, setSize] = useState(10); // Items per page
  const [notificationError, setNotificationError] = useState(null); // State for notification fetch errors
  const [markingAsRead, setMarkingAsRead] = useState(false); // State for marking as read loading

  const [isBookingsAlertVisible, setIsBookingsAlertVisible] = useState(true); // State for bookings alert visibility
  const [isNotificationAlertVisible, setIsNotificationAlertVisible] = useState(true); // State for notification alert visibility
  useEffect(() => {
    const fetchUserData = async () => {
      if (!currentUser || !currentUser.id) {
        setError('No authentication token found.');
        // setLoading(false); // Use authLoading from useAuth
        return;
      }

      try {
        const response = await fetch('/api/user/me', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error('Failed to fetch user data.');
        }
        const data = await response.json();
        setUserData(data);
        setUserRole(data.role); // Assuming the backend returns the user's role in userData
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    const fetchBookings = async () => {
      if (!currentUser || !currentUser.id || !currentUser.role) {
        setBookingsLoading(false);
        return; // Don't fetch if user is not loaded or role is unknown
      }

      setBookingsLoading(true);
      setBookingsError(null);
      setIsBookingsAlertVisible(true); // Show alert on new error

      let url = '';
      if (currentUser.role === 'STUDENT') {
        url = `/api/bookings/student/${currentUser.id}?page=${page}&size=${size}`;
      } else if (currentUser.role === 'TUTOR') {
        url = `/api/bookings/tutor/${currentUser.id}?page=${page}&size=${size}`;
      } else {
        setBookingsError('Invalid user role.');
        setBookingsLoading(false);
        return;
      }

      try {
        const response = await authenticatedFetch(url);
        if (response.ok) {
          const data = await response.json();
          setBookings(data.content || []); // Assuming your backend returns a Page object with 'content'
        } else {
          const errorData = await response.json();
          setBookingsError(errorData.message || 'Failed to fetch bookings.');
        }
      } catch (err) {
        console.error('Error fetching bookings:', err);
        setBookingsError('An error occurred while fetching bookings.');
      } finally {
        // Keep alert visible if there was an error, hide otherwise
        if (!bookingsError) {
          setIsBookingsAlertVisible(false);
        }
        setBookingsLoading(false);
      }
    };

    const fetchNotifications = async () => {
      if (!currentUser || !currentUser.id) {
        return; // Don't fetch if user is not loaded yet
      }
      try {
        setNotificationError(null); // Clear previous notification errors
        setIsNotificationAlertVisible(true); // Show alert on new error
        const response = await authenticatedFetch(`/api/users/${currentUser.id}/notifications`);
        if (response.ok) {
          const data = await response.json(); // Assuming the response is a list of notifications or a Page object
          setNotifications(data.content || data);
        } else {
          const errorData = await response.json();
          console.error('Failed to fetch notifications:', errorData);
 setNotificationError(errorData.message || 'Failed to fetch notifications.');
        }
      } catch (err) {
        console.error('Error fetching notifications:', err);
 setNotificationError('An error occurred while fetching notifications.');
      } finally {
        if (!notificationError) {
          setIsNotificationAlertVisible(false);
        }
      }
    };

    if (!authLoading && currentUser) { // Fetch data only when auth is not loading and currentUser is available
        fetchUserData();
        fetchBookings(); // Fetch bookings as well
        fetchNotifications();
    }
  }, [currentUser, authLoading, page, size]); // Add currentUser, authLoading, page, and size to dependencies

  const handleCloseBookingsAlert = () => {
    setIsBookingsAlertVisible(false);
  };

  const handleCloseNotificationAlert = () => {
    setIsNotificationAlertVisible(false);
  };

  const markNotificationAsRead = async (notificationId) => {
    if (!currentUser || !currentUser.id) {
      return; // Cannot mark as read if user is not loaded
    }

    setMarkingAsRead(true);
    setNotificationError(null); // Clear previous errors before marking
    setIsNotificationAlertVisible(true); // Show alert on new error if any

    try {
      const response = await authenticatedFetch(`/api/users/${currentUser.id}/notifications/${notificationId}/mark-as-read`, {
        method: 'PUT',
      });

      if (response.ok) {
        // Update the state to mark the notification as read without refetching
        setNotifications(notifications.map(notification =>
          notification.id === notificationId ? { ...notification, read: true } : notification
        ));
      } else {
        const errorData = await response.json();
        console.error('Failed to mark notification as read:', errorData);
        setNotificationError(errorData.message || 'Failed to mark notification as read.');
      }
    } catch (err) {
      console.error('Error marking notification as read:', err);
      setNotificationError('An error occurred while marking notification as read.');
    } finally {
      setIsNotificationAlertVisible(false); // Hide alert after action
      setMarkingAsRead(false);
    }
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  if (authLoading || !currentUser || !userRole) { // Show loading if auth is loading or user/role is not available yet
    return <div>Loading user data...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }


  return (
    <div>
      <Navbar />
      <div className="container mx-auto mt-8 p-4">
        <h1 className="text-3xl font-bold mb-4">User Dashboard</h1>
        
        {/* Displaying user-specific greeting */}
        {userData && (
          <h2 className="text-2xl mb-4">Welcome, {userData.fullName || userData.username}!</h2>
        )}
        
        {userRole === 'student' ? (
          <div>
            <h2 className="text-2xl mb-4">Welcome, Student!</h2>
            <p className="mb-6">This is your student dashboard. Here you can see your enrolled subjects, upcoming sessions, and progress.</p>
            
            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">Enrolled Subjects</h3>
              <ul>
                <li>Placeholder Subject 1</li>
                <li>Placeholder Subject 2</li>
              </ul>
            </div>

            <div>
              <h3 className="text-xl font-semibold mb-2">Upcoming Bookings</h3>
              {bookingsLoading ? (
                <div>Loading bookings...</div>
              ) : bookingsError && isBookingsAlertVisible ? (
                <Alert message={bookingsError} type="error" onClose={handleCloseBookingsAlert} />
 ) : bookingsError ? ( // Fallback for error display if alert is closed
                <div style={{ color: 'red' }}>Error: {bookingsError}</div>
              ) : bookings.length === 0 ? (
                <p>No bookings found.</p>
              ) : (
              <ul>
                {bookings.map(booking => (
                   // Ensure booking and its nested properties are not null before accessing
                  <li key={booking.id}>
                    Booking ID: {booking.id}
                    {/* You'll need to display relevant booking details based on your backend response */}
                    {/* Example: Session Subject: {booking.session?.subject} */}
                    {/* Example: Tutor: {booking.tutor?.user?.firstName} {booking.tutor?.user?.lastName} */}
                    {/* Example: Date and Time: {booking.bookingTime} */}
                     {booking.session && booking.session.subject && (
                      <p>Session Subject: {booking.session.subject}</p>
                    )}
                    {booking.tutor && booking.tutor.user && (
                      <p>Tutor: {booking.tutor.user.firstName} {booking.tutor.user.lastName}</p>
                    )}
                     {booking.bookingTime && (
                      <p>Date and Time: {new Date(booking.bookingTime).toLocaleString()}</p>
                    )}
                  </li>
                ))}
              </ul>
              )}
              {/* Pagination Controls */}
            </div>

            {/* Display Notifications */}
            <div className="mt-6">
              <h3 className="text-xl font-semibold mb-2">Notifications</h3>
              {notificationError && isNotificationAlertVisible ? (
                <Alert message={notificationError} type="error" onClose={handleCloseNotificationAlert} />
 ) : notificationError ? ( // Fallback for error display if alert is closed
              {notifications.length === 0 ? (
                <p>No new notifications.</p>
              ) : (
                <ul>
                  {notifications.map(notification => (
                     <li
                      key={notification.id}
                      className={notification.read ? 'bg-gray-200' : 'bg-white font-bold'} // Visually distinguish read/unread
                    >
                      {notification.message} - {new Date(notification.timestamp).toLocaleString()}
                      {!notification.read && ( // Only show button if not already read (assuming 'read' property exists)
                        <button onClick={() => markNotificationAsRead(notification.id)} disabled={markingAsRead}>
                          {markingAsRead ? '...' : 'Mark as Read'}
                        </button>
                      )}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        ) : (
          // Tutor Specific Dashboard Content
          <div>
             <h2 className="text-2xl mb-4">Welcome, Tutor!</h2>
             <p className="mb-6">This is your tutor dashboard. Here you can manage your sessions, resources, and view your bookings.</p>

             <div className="mb-6">
               <h3 className="text-xl font-semibold mb-2">Your Bookings</h3>
                {bookingsLoading ? (
                <div>Loading bookings...</div>
               ) : bookingsError && isBookingsAlertVisible ? (
                <Alert message={bookingsError} type="error" onClose={handleCloseBookingsAlert} />
 ) : bookingsError ? ( // Fallback for error display if alert is closed
                <div style={{ color: 'red' }}>Error: {bookingsError}</div>
               ) : bookings.length === 0 ? (
                <p>No bookings found.</p>
               ) : (
                <ul>
                 {bookings.map(booking => (
                    // Ensure booking and its nested properties are not null before accessing
                   <li key={booking.id}>
                     Booking ID: {booking.id}
                     {/* Display relevant booking details for a tutor */}
                      {booking.session && booking.session.subject && (
                       <p>Session Subject: {booking.session.subject}</p>
                     )}
                     {booking.student && booking.student.user && (
                       <p>Student: {booking.student.user.firstName} {booking.student.user.lastName}</p>
                     )}
                     {booking.bookingTime && (
                       <p>Date and Time: {new Date(booking.bookingTime).toLocaleString()}</p>
                     )}
                   </li>
                 ))}
               </ul>
               )}
               {/* Pagination Controls */}
             </div>

          </div>
        )}

        {/* Add more dashboard content based on user role */}
      </div>
      <Footer />
    </div>
  );
};

export default Dashboard;