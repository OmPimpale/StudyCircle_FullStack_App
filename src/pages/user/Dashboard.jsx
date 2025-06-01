import React, { useState, useEffect } from 'react';
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';
import authenticatedFetch from '../../utils/authenticatedFetch';
import Alert from '../../components/Alert';
import useAuth from '../../hooks/useAuth';

const Dashboard = () => {
  const { currentUser, loading: authLoading } = useAuth();

  const [userData, setUserData] = useState(null);
  const [error, setError] = useState(null);
  const [userRole, setUserRole] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [bookingsLoading, setBookingsLoading] = useState(true);
  const [bookingsError, setBookingsError] = useState(null);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [notificationError, setNotificationError] = useState(null);
  const [markingAsRead, setMarkingAsRead] = useState(false);
  const [isBookingsAlertVisible, setIsBookingsAlertVisible] = useState(true);
  const [isNotificationAlertVisible, setIsNotificationAlertVisible] = useState(true);

  useEffect(() => {
    const fetchUserData = async () => {
      if (!currentUser || !currentUser.id || !currentUser.token) {
        setError('No authentication token found.');
        return;
      }

      try {
        const response = await fetch('/api/user/me', {
          headers: {
            Authorization: `Bearer ${currentUser.token}`,
          },
        });
        if (!response.ok) throw new Error('Failed to fetch user data.');
        const data = await response.json();
        setUserData(data);
        setUserRole(data.role);
      } catch (err) {
        setError(err.message);
      }
    };

    const fetchBookings = async () => {
      if (!currentUser || !currentUser.id || !currentUser.role) {
        setBookingsLoading(false);
        return;
      }

      setBookingsLoading(true);
      setBookingsError(null);
      setIsBookingsAlertVisible(true);

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
          setBookings(data.content || []);
        } else {
          const errorData = await response.json();
          setBookingsError(errorData.message || 'Failed to fetch bookings.');
        }
      } catch (err) {
        setBookingsError('An error occurred while fetching bookings.');
      } finally {
        if (!bookingsError) {
          setIsBookingsAlertVisible(false);
        }
        setBookingsLoading(false);
      }
    };

    const fetchNotifications = async () => {
      if (!currentUser || !currentUser.id) return;

      try {
        setNotificationError(null);
        setIsNotificationAlertVisible(true);
        const response = await authenticatedFetch(`/api/users/${currentUser.id}/notifications`);
        if (response.ok) {
          const data = await response.json();
          setNotifications(data.content || data);
        } else {
          const errorData = await response.json();
          setNotificationError(errorData.message || 'Failed to fetch notifications.');
        }
      } catch (err) {
        setNotificationError('An error occurred while fetching notifications.');
      } finally {
        if (!notificationError) {
          setIsNotificationAlertVisible(false);
        }
      }
    };

    if (!authLoading && currentUser) {
      fetchUserData();
      fetchBookings();
      fetchNotifications();
    }
  }, [currentUser, authLoading, page, size]);

  const handleCloseBookingsAlert = () => {
    setIsBookingsAlertVisible(false);
  };

  const handleCloseNotificationAlert = () => {
    setIsNotificationAlertVisible(false);
  };

  const markNotificationAsRead = async (notificationId) => {
    if (!currentUser || !currentUser.id) return;

    setMarkingAsRead(true);
    setNotificationError(null);
    setIsNotificationAlertVisible(true);

    try {
      const response = await authenticatedFetch(
        `/api/users/${currentUser.id}/notifications/${notificationId}/mark-as-read`,
        { method: 'PUT' }
      );

      if (response.ok) {
        setNotifications(notifications.map(n =>
          n.id === notificationId ? { ...n, read: true } : n
        ));
      } else {
        const errorData = await response.json();
        setNotificationError(errorData.message || 'Failed to mark notification as read.');
      }
    } catch (err) {
      setNotificationError('An error occurred while marking notification as read.');
    } finally {
      setIsNotificationAlertVisible(false);
      setMarkingAsRead(false);
    }
  };

  if (authLoading || !currentUser || !userRole) {
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

        {userData && (
          <h2 className="text-2xl mb-4">
            Welcome, {userData.fullName || userData.username}!
          </h2>
        )}

        {userRole === 'STUDENT' ? (
          <div>
            <h2 className="text-2xl mb-4">Welcome, Student!</h2>
            <p className="mb-6">
              This is your student dashboard. Here you can see your enrolled subjects, upcoming sessions, and progress.
            </p>

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
              ) : bookingsError ? (
                <div style={{ color: 'red' }}>Error: {bookingsError}</div>
              ) : bookings.length === 0 ? (
                <p>No bookings found.</p>
              ) : (
                <ul>
                  {bookings.map(booking => (
                    <li key={booking.id}>
                      Booking ID: {booking.id}
                      {booking.session?.subject && <p>Session Subject: {booking.session.subject}</p>}
                      {booking.tutor?.user && (
                        <p>Tutor: {booking.tutor.user.firstName} {booking.tutor.user.lastName}</p>
                      )}
                      {booking.bookingTime && (
                        <p>Date and Time: {new Date(booking.bookingTime).toLocaleString()}</p>
                      )}
                    </li>
                  ))}
                </ul>
              )}
            </div>

            <div className="mt-6">
              <h3 className="text-xl font-semibold mb-2">Notifications</h3>
              {notificationError && isNotificationAlertVisible ? (
                <Alert message={notificationError} type="error" onClose={handleCloseNotificationAlert} />
              ) : notifications.length === 0 ? (
                <p>No new notifications.</p>
              ) : (
                <ul>
                  {notifications.map(notification => (
                    <li
                      key={notification.id}
                      className={notification.read ? 'bg-gray-200 p-2 mb-2' : 'bg-white font-bold p-2 mb-2'}
                    >
                      {notification.message} - {new Date(notification.timestamp).toLocaleString()}
                      {!notification.read && (
                        <button
                          onClick={() => markNotificationAsRead(notification.id)}
                          disabled={markingAsRead}
                          className="ml-2 px-2 py-1 bg-blue-500 text-white rounded"
                        >
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
          <div>
            <h2 className="text-2xl mb-4">Welcome, Tutor!</h2>
            <p className="mb-6">This is your tutor dashboard. Here you can manage your sessions, resources, and view your bookings.</p>

            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">Your Bookings</h3>
              {bookingsLoading ? (
                <div>Loading bookings...</div>
              ) : bookingsError && isBookingsAlertVisible ? (
                <Alert message={bookingsError} type="error" onClose={handleCloseBookingsAlert} />
              ) : bookingsError ? (
                <div style={{ color: 'red' }}>Error: {bookingsError}</div>
              ) : bookings.length === 0 ? (
                <p>No bookings found.</p>
              ) : (
                <ul>
                  {bookings.map(booking => (
                    <li key={booking.id}>
                      Booking ID: {booking.id}
                      {booking.session?.subject && <p>Session Subject: {booking.session.subject}</p>}
                      {booking.student?.user && (
                        <p>Student: {booking.student.user.firstName} {booking.student.user.lastName}</p>
                      )}
                      {booking.bookingTime && (
                        <p>Date and Time: {new Date(booking.bookingTime).toLocaleString()}</p>
                      )}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        )}
      </div>
      <Footer />
    </div>
  );
};

export default Dashboard;