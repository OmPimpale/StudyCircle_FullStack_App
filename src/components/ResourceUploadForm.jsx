import React, { useState, useEffect } from 'react';
import authenticatedFetch from '../utils/authenticatedFetch'; // Adjust the path
import { useAuth } from '../hooks/useAuth'; // Assuming you have the useAuth hook

const ResourceUploadForm = () => {
    const { currentUser } = useAuth(); // Get current user from AuthContext
    const [selectedFile, setSelectedFile] = useState(null);
    const [selectedSessionId, setSelectedSessionId] = useState('');
    const [sessions, setSessions] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        const fetchTutorSessions = async () => {
            if (!currentUser || currentUser.role !== 'TUTOR') {
                // Only fetch sessions if the user is a tutor
                return;
            }

            try {
                // Assuming you have an endpoint to get sessions for a tutor
                const response = await authenticatedFetch(`/api/sessions/tutor/${currentUser.id}`);
                if (response.ok) {
                    const data = await response.json();
                    // Assuming your backend returns a list of sessions directly or in data.content
                    setSessions(data.content || data);
                } else {
                    const errorData = await response.json();
                    setError(errorData.message || 'Failed to fetch tutor sessions.');
                }
            } catch (error) {
                console.error('Error fetching tutor sessions:', error);
                setError('An error occurred while fetching tutor sessions.');
            }
        };

        fetchTutorSessions();
    }, [currentUser]); // Refetch when current user changes

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleSessionChange = (event) => {
        setSelectedSessionId(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setError(null);
        setSuccess(null);

        if (!selectedFile || !selectedSessionId || !currentUser || !currentUser.id) {
            setError('Please select a file, a session, and ensure you are logged in.');
            setLoading(false);
            return;
        }

        const formData = new FormData();
        formData.append('file', selectedFile);
        formData.append('userId', currentUser.id); // Assuming your backend expects userId
        formData.append('sessionId', selectedSessionId); // Assuming your backend expects sessionId

        try {
            const response = await authenticatedFetch('/api/resources', {
                method: 'POST',
                body: formData, // No 'Content-Type' header needed for FormData
            });

            if (response.ok) {
                setSuccess('Resource uploaded successfully!');
                setSelectedFile(null); // Clear selected file
                setSelectedSessionId(''); // Clear selected session
                // Optionally, refetch the list of resources for the session/user
            } else {
                const errorData = await response.json();
                setError(errorData.message || 'Failed to upload resource.');
            }
        } catch (error) {
            console.error('Resource upload error:', error);
            setError('An error occurred during resource upload.');
        } finally {
            setLoading(false);
        }
    };

    if (!currentUser || currentUser.role !== 'TUTOR') {
        return <p>You must be a tutor to upload resources.</p>;
    }


    return (
        <div>
            <h2>Upload Resource</h2>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div>
                    <label htmlFor="session">Select Session:</label>
                    <select id="session" value={selectedSessionId} onChange={handleSessionChange} required>
                        <option value="">--Select a Session--</option>
                        {sessions.map(session => (
                            <option key={session.id} value={session.id}>
                                {session.subject} - {new Date(session.startTime).toLocaleString()} {/* Display session info */}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="file">Select File:</label>
                    <input type="file" id="file" onChange={handleFileChange} required />
                </div>
                <button type="submit" disabled={loading}>
                    {loading ? 'Uploading...' : 'Upload Resource'}
                </button>
            </form>

            {error && <p style={{ color: 'red' }}>Error: {error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
        </div>
    );
};

export default ResourceUploadForm;