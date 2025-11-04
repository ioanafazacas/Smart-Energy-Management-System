import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Navbar() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  return (
    <nav className="bg-gray-800 text-white flex justify-between p-3">
      <span className="font-bold">{user?.role} Panel</span>
      <button
        onClick={() => {
          logout();
          navigate('/');
        }}
      >
        Logout
      </button>
    </nav>
  );
}
