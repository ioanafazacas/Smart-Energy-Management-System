import React, { useEffect, useState, useContext } from 'react';
import { deviceAPI } from '../api/devices.js';
import { AuthContext } from '../context/AuthContext';
import Navbar from '../components/Navbar';

export default function UserDashboard() {
  const { user, token } = useContext(AuthContext);
  const [devices, setDevices] = useState([]);

  useEffect(() => {
    deviceAPI
      .get(`/device/user/${user.user_id}`, {
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

// import React from 'react';
//
// const UserDashboard = () => {
//   return (
//     <div className="p-6 text-center">
//       <h1 className="text-3xl font-bold text-blue-600">
//         Bine ai venit în dashboardul utilizatorului!
//       </h1>
//     </div>
//   );
// };
//
// export default UserDashboard;
