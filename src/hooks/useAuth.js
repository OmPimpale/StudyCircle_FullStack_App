// src/hooks/useAuth.js
import { useContext } from 'react';
// import { AuthContext } from '../context/authcontext';  // Adjust path as needed

const useAuth = () => {
  const context = useContext(AuthContext);

  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }

  return context;
};

export default useAuth;
