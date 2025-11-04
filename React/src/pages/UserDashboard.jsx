import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import Navbar from '../components/Navbar';

export default function UserDashboard() {
  const { user, token } = useContext(AuthContext);
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    axios
      .get(`/devices/user/${user.id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setDevices(res.data));
  }, [user, token]);

  return (
    <div>
      <Navbar />
      <h1 className="text-3xl text-center mt-6">My Devices</h1>
      <div className="p-6">
        {devices.map((d) => (
          <div key={d.deviceId} className="border p-3 mb-2 rounded">
            <p>
              <b>{d.name}</b> ({d.serialNumber})
            </p>
            <p>Max consumption: {d.maxConsumption}W</p>
          </div>
        ))}
      </div>
    </div>
  );
}
