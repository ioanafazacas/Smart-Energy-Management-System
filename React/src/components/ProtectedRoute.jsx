import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const ProtectedRoute = ({ allowedRoles }) => {
  const { user, token } = useContext(AuthContext);

  console.log('🔍 ProtectedRoute:', { user, token, allowedRoles });

  if (!user || !token) {
    console.warn('🚫 No user or token, redirecting to /');
    return <Navigate to="/" replace />;
  }

  if (!allowedRoles.includes(user.role)) {
    console.warn('🚫 Role not allowed, redirecting to /');
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
