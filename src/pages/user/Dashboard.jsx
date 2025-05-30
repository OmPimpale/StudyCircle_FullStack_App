import React, { useState, useEffect } from 'react';
import Navbar from '../../Components/Navbar';
import Footer from '../../Components/Footer';

const Dashboard = () => {
  // In a real application, you would fetch the user role from the backend
  const [userRole, setUserRole] = useState('student'); // or 'tutor'
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('jwtToken');
      if (!token) {
        setError('No authentication token found.');
        setLoading(false);
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
        setUserRole(data.role); // Assuming the backend returns the user's role
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []); // Empty dependency array means this effect runs once on mount

  if (loading) {
    return <div>Loading dashboard...</div>;
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
              <h3 className="text-xl font-semibold mb-2">Upcoming Sessions</h3>
              <ul>
                <li>Placeholder Session 1 (Date, Time, Tutor)</li>
                <li>Placeholder Session 2 (Date, Time, Tutor)</li>
              </ul>
            </div>
          </div>
        ) : (
          <div>
            <h2 className="text-2xl mb-4">Welcome, Tutor!</h2>
            <p className="mb-6">This is your tutor dashboard. Here you can manage your sessions, students, and earnings.</p>
            
            {/* Add tutor-specific content here */} {/* Content added below */}
          </div>
        )}

        {/* Add more dashboard content based on user role */}
      </div>
      <Footer />
    </div>
  );
};

export default Dashboard;