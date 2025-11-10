import React, { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";

export default function AdminDashboard() {
  const [users, setUsers] = useState([]);
  const [devices, setDevices] = useState([]);
  const [loading, setLoading] = useState(true);

  // Modal state
  const [showUserModal, setShowUserModal] = useState(false);
  const [showDeviceModal, setShowDeviceModal] = useState(false);
  const [editingUser, setEditingUser] = useState(null);
  const [editingDevice, setEditingDevice] = useState(null);

  const [formUser, setFormUser] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    address: "",
    birthDate: "",
    role: "USER",
  });

  const [formDevice, setFormDevice] = useState({
    name: "",
    serialNumber: "",
    maxConsumption: "",
  });

  const USER_API = "http://user.localhost/user";
  const DEVICE_API = "http://device.localhost/device";
  const AUTH_API = "http://auth.localhost/auth";

  // -------------------------------
  // FETCH DATA INITIALLY
  // -------------------------------
  useEffect(() => {
    const fetchData = async () => {
      try {
        const [usersRes, devicesRes] = await Promise.all([
          axios.get(`${USER_API}/all`),
          axios.get(`${DEVICE_API}/all`),
        ]);
        setUsers(
          usersRes.data.map((u) => ({
            ...u,
            id: u.id || u.user_id, // normalizează ID-ul
          })),
        );

        //setUsers(usersRes.data);
        setDevices(devicesRes.data);
      } catch (err) {
        console.error("❌ Eroare la fetch:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  // -------------------------------
  // USERS CRUD
  // -------------------------------
  const openUserModal = (user = null) => {
    setEditingUser(user);
    setFormUser(
      user || {
        username: "",
        password: "",
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        address: "",
        birthDate: "",
        role: "USER",
      }
    );
    setShowUserModal(true);
  };

  const handleUserSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingUser) {
        // UPDATE
        const userId = editingUser.id || editingUser.user_id;
        const res = await axios.put(`${USER_API}/${userId}`, formUser);

        setUsers(users.map((u) => (u.id === editingUser.id ? res.data : u)));
      } else {
        // CREATE
        const authRes = await axios.post(`${AUTH_API}/register`, formUser);
        const createdUser = authRes.data;

        if (createdUser && createdUser.id) {
          console.log("✅ Creat în AUTH:", createdUser);
          setUsers([...users, createdUser]);
        } else {
          console.warn("⚠️ Backendul nu a returnat un utilizator valid:", createdUser);
        }

        setShowUserModal(false);
      }
    } catch (err) {
      console.error("❌ Eroare la salvare user:", err.response?.data || err.message);
    }
  };

  const handleDeleteUser = async (id) => {
    try {
      await axios.delete(`${USER_API}/${id}`);
      setUsers(users.filter((u) => u.id !== id));
    } catch (err) {
      console.error("❌ Eroare la ștergere user:", err);
    }
  };

  // -------------------------------
  // DEVICES CRUD
  // -------------------------------
  const openDeviceModal = (device = null) => {
    setEditingDevice(device);
    setFormDevice(
      device || {
        name: "",
        serialNumber: "",
        maxConsumption: "",
      }
    );
    setShowDeviceModal(true);
  };

  const handleDeviceSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editingDevice) {
        const res = await axios.put(`${DEVICE_API}/${editingDevice.deviceId}`, formDevice);
        setDevices(devices.map((d) => (d.deviceId === editingDevice.deviceId ? res.data : d)));
      } else {
        const res = await axios.post(`${DEVICE_API}/create`, formDevice);
        setDevices([...devices, res.data]);
      }
      setShowDeviceModal(false);
    } catch (err) {
      console.error("❌ Eroare la salvare device:", err);
    }
  };

  const handleDeleteDevice = async (id) => {
    try {
      await axios.delete(`${DEVICE_API}/${id}`);
      setDevices(devices.filter((d) => d.deviceId !== id));
    } catch (err) {
      console.error("❌ Eroare la ștergere device:", err);
    }
  };

  // -------------------------------
  // UI RENDERING
  // -------------------------------
  if (loading)
    return (
      <div className="text-center mt-10 text-gray-500 text-lg">
        Se încarcă datele...
      </div>
    );

  return (
    <div>
      <Navbar />
      <div className="p-6">
        <h1 className="text-3xl font-bold text-center text-blue-600 mb-6">
          Admin Dashboard
        </h1>

        {/* USERS TABLE */}
        <div className="bg-white shadow-lg rounded-2xl p-4 mb-10">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-semibold text-gray-700">
              Utilizatori
            </h2>
            <button
              onClick={() => openUserModal()}
              className="bg-blue-500 text-white px-4 py-2 rounded-xl hover:bg-blue-600"
            >
              ➕ Adaugă
            </button>
          </div>

          <table className="w-full text-sm text-left text-gray-700">
            <thead className="bg-blue-100 text-blue-700">
              <tr>
                <th className="px-4 py-2">Username</th>
                <th className="px-4 py-2">Rol</th>
                <th className="px-4 py-2">Nume</th>
                <th className="px-4 py-2">Email</th>
                <th className="px-4 py-2">Telefon</th>
                <th className="px-4 py-2">Adresă</th>
                <th className="px-4 py-2">Data nașterii</th>
                <th className="px-4 py-2 text-center">Acțiuni</th>
              </tr>
            </thead>

            <tbody>
              {users.map((u) => (
                <tr
                  key={u.id || u.user_id}
                  className="border-b hover:bg-gray-50"
                >
                  <td className="px-4 py-2">{u.username || '-'}</td>
                  <td className="px-4 py-2">{u.role?.role_name || '-'}</td>
                  <td className="px-4 py-2">
                    {`${u.firstName || ''} ${u.lastName || ''}`.trim() || '-'}
                  </td>
                  <td className="px-4 py-2">{u.email || '-'}</td>
                  <td className="px-4 py-2">{u.phoneNumber || '-'}</td>
                  <td className="px-4 py-2">{u.address || '-'}</td>
                  <td className="px-4 py-2">{u.birthDate || '-'}</td>
                  <td className="px-4 py-2 text-center">
                    <button
                      onClick={() => openUserModal(u)}
                      className="text-yellow-500 hover:text-yellow-600 mr-3"
                    >
                      ✏️
                    </button>
                    <button
                      onClick={() => handleDeleteUser(u.id || u.user_id)}
                      className="text-red-500 hover:text-red-600"
                    >
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* DEVICES TABLE */}
        <div className="bg-white shadow-lg rounded-2xl p-4">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-semibold text-gray-700">Device-uri</h2>
            <button
              onClick={() => openDeviceModal()}
              className="bg-green-500 text-white px-4 py-2 rounded-xl hover:bg-green-600"
            >
              ➕ Adaugă
            </button>
          </div>

          <table className="w-full text-sm text-left text-gray-700">
            <thead className="bg-green-100 text-green-700">
              <tr>
                <th className="px-4 py-2">Nume</th>
                <th className="px-4 py-2">Serial Number</th>
                <th className="px-4 py-2">Consum maxim</th>
                <th className="px-4 py-2 text-center">Acțiuni</th>
              </tr>
            </thead>

            <tbody>
              {devices.map((d) => (
                <tr key={d.deviceId} className="border-b hover:bg-gray-50">
                  <td className="px-4 py-2">{d.name}</td>
                  <td className="px-4 py-2">{d.serialNumber}</td>
                  <td className="px-4 py-2">{d.maxConsumption} W</td>
                  <td className="px-4 py-2 text-center">
                    <button
                      onClick={() => openDeviceModal(d)}
                      className="text-yellow-500 hover:text-yellow-600 mr-3"
                    >
                      ✏️
                    </button>
                    <button
                      onClick={() => handleDeleteDevice(d.deviceId)}
                      className="text-red-500 hover:text-red-600"
                    >
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
      {/* USER MODAL */}
      {showUserModal && (
        <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center">
          <div className="bg-white p-6 rounded-xl w-1/2 shadow-lg">
            <h3 className="text-xl font-semibold mb-4">
              {editingUser ? 'Editează utilizator' : 'Adaugă utilizator'}
            </h3>

            <form
              onSubmit={handleUserSubmit}
              className="grid grid-cols-2 gap-4"
            >
              {/* 🔹 Username și Parolă – DOAR LA CREARE */}
              {!editingUser && (
                <>
                  <input
                    type="text"
                    placeholder="Username"
                    value={formUser.username}
                    onChange={(e) =>
                      setFormUser({ ...formUser, username: e.target.value })
                    }
                    className="border p-2 rounded-md"
                    required
                  />

                  <input
                    type="password"
                    placeholder="Parolă"
                    value={formUser.password}
                    onChange={(e) =>
                      setFormUser({ ...formUser, password: e.target.value })
                    }
                    className="border p-2 rounded-md"
                    required
                  />
                </>
              )}

              {/* Prenume */}
              <input
                type="text"
                placeholder="Prenume"
                value={formUser.firstName}
                onChange={(e) =>
                  setFormUser({ ...formUser, firstName: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* Nume */}
              <input
                type="text"
                placeholder="Nume"
                value={formUser.lastName}
                onChange={(e) =>
                  setFormUser({ ...formUser, lastName: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* Email */}
              <input
                type="email"
                placeholder="Email"
                value={formUser.email}
                onChange={(e) =>
                  setFormUser({ ...formUser, email: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* Telefon */}
              <input
                type="text"
                placeholder="Telefon"
                value={formUser.phoneNumber}
                onChange={(e) =>
                  setFormUser({ ...formUser, phoneNumber: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* Adresă */}
              <input
                type="text"
                placeholder="Adresă"
                value={formUser.address}
                onChange={(e) =>
                  setFormUser({ ...formUser, address: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* Data nașterii */}
              <input
                type="date"
                value={formUser.birthDate}
                onChange={(e) =>
                  setFormUser({ ...formUser, birthDate: e.target.value })
                }
                className="border p-2 rounded-md"
              />

              {/* 🔽 Select rol */}
              <select
                value={formUser.role?.role_name || formUser.role || 'USER'}
                onChange={(e) =>
                  setFormUser({
                    ...formUser,
                    role: {
                      role_id: e.target.value === 'ADMIN' ? 2 : 1,
                      role_name: e.target.value,
                    },
                  })
                }
                className="border p-2 rounded-md col-span-2"
              >
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>

              {/* Butoane */}
              <div className="col-span-2 flex justify-end mt-4">
                <button
                  type="button"
                  onClick={() => setShowUserModal(false)}
                  className="bg-gray-300 px-4 py-2 rounded-md mr-2"
                >
                  Anulează
                </button>
                <button
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded-md"
                >
                  Salvează
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
