import React, { useState, useEffect } from 'react';
import Navbar from '../Components/Navbar'; // Adjust the import path as needed
import Footer from '../Components/Footer'; // Adjust the import path as needed

function GoalSettingPage() {
  const [goals, setGoals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  useEffect(() => {
    const fetchGoals = async () => {
      const token = localStorage.getItem('jwtToken');
      if (!token) {
        setError('User not authenticated');
        setLoading(false);
        return;
      }

      try {
        const response = await fetch(`/api/goals/me`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setGoals(data);
      } catch (error) {
        setError('Failed to fetch goals. Please try again.');
        console.error('Error fetching goals:', error);
      } finally {
        setLoading(false);
      }    };
    if (localStorage.getItem('jwtToken')) { // Only fetch if token exists
 fetchGoals();
    }  else {
        setLoading(false);
        setError('Student ID is not set.');
    }
  }, [studentId]); // Rerun effect if studentId changes

  return (
    <>
      <Navbar />
      <div className="container mx-auto px-4 py-8">
        <h1>Student Goal Setting Page</h1>
        {loading && <p>Loading goals...</p>}
        {error && <p className="text-red-500">Error: {error}</p>}
        {!loading && !error && (
          <div>
            <h2 className="text-xl font-semibold mb-2">Your Goals:</h2>
            {goals.length === 0 ? (
              <p>No goals set yet.</p>
            ) : (
              <ul>
                {goals.map(goal => (
                  <li key={goal.id}>{goal.description}</li> // Assuming goal object has 'id' and 'description'
                ))}
              </ul>
            )}
          </div>
        )}
        {/* Add goal setting form components here */}
      </div>
      <Footer />
    </>
  );
}

export default GoalSettingPage;