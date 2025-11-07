import React, { useEffect, useState, useContext } from 'react';
import { deviceAPI} from '../api/devices.js';
import { AuthContext } from '../context/AuthContext';
import Navbar from '../components/Navbar';
import { userAPI } from '../api/users.js';

export default function AdminDashboard() {
  const { token } = useContext(AuthContext);
  const [users, setUsers] = useState([]);
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    userAPI
      .get('/user/all', { headers: { Authorization: `Bearer ${token}` } })
      .then((res) => setUsers(res.data));
    deviceAPI
      .get('/device/all', { headers: { Authorization: `Bearer ${token}` } })
      .then((res) => setDevices(res.data));
  }, [token]);

  return (
    <div>
      <Navbar />
      <h1 className="text-3xl text-center mt-6">Admin Dashboard</h1>

      <div className="p-6 grid grid-cols-2 gap-6">
        <div>
          <h2 className="font-bold text-xl mb-2">Users</h2>
          {users.map((u) => (
            <div key={u.id} className="border p-2 mb-2">
              {u.username}
            </div>
          ))}
        </div>

        <div>
          <h2 className="font-bold text-xl mb-2">Devices</h2>
          {devices.map((d) => (
            <div key={d.deviceId} className="border p-2 mb-2">
              {d.name} — {d.serialNumber}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
