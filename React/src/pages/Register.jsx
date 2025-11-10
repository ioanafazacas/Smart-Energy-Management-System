import React, { useState } from 'react';
import { authAPI } from '../api/auth';
import { useNavigate } from 'react-router-dom';

export default function Register() {
  const navigate = useNavigate();

  // datele din formular
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    address: '',
    birthDate: '',
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      console.log('📤 Trimit datele de înregistrare:', formData);
      const response = await authAPI.post('/auth/register', formData);
      console.log('✅ Înregistrare reușită:', response);

      alert('Contul a fost creat cu succes!');
      navigate('/');
    } catch (err) {
      console.error('❌ Eroare la înregistrare:', err);
      alert('Înregistrarea a eșuat. Verifică datele și încearcă din nou.');
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md"
      >
        <h2 className="text-3xl font-bold mb-6 text-center text-blue-600">
          Înregistrare utilizator
        </h2>

        <div className="grid grid-cols-2 gap-4">
          <input
            type="text"
            name="firstName"
            placeholder="Prenume"
            value={formData.firstName}
            onChange={handleChange}
            className="border p-2 rounded"
            required
          />
          <input
            type="text"
            name="lastName"
            placeholder="Nume"
            value={formData.lastName}
            onChange={handleChange}
            className="border p-2 rounded"
            required
          />
        </div>

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          className="border p-2 rounded w-full mt-3"
          required
        />
        <input
          type="text"
          name="phoneNumber"
          placeholder="Telefon"
          value={formData.phoneNumber}
          onChange={handleChange}
          className="border p-2 rounded w-full mt-3"
          required
        />
        <input
          type="text"
          name="address"
          placeholder="Adresă"
          value={formData.address}
          onChange={handleChange}
          className="border p-2 rounded w-full mt-3"
          required
        />
        <input
          type="date"
          name="birthDate"
          value={formData.birthDate}
          onChange={handleChange}
          className="border p-2 rounded w-full mt-3"
          required
        />

        <hr className="my-4" />

        <input
          type="text"
          name="username"
          placeholder="Nume utilizator"
          value={formData.username}
          onChange={handleChange}
          className="border p-2 rounded w-full"
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Parolă"
          value={formData.password}
          onChange={handleChange}
          className="border p-2 rounded w-full mt-3"
          required
        />

        <button
          type="submit"
          className="mt-6 w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded transition duration-300"
        >
          Creează cont
        </button>

        <p className="mt-4 text-center text-gray-600">
          Ai deja un cont?{' '}
          <span
            onClick={() => navigate('/')}
            className="text-blue-600 cursor-pointer hover:underline"
          >
            Autentifică-te
          </span>
        </p>
      </form>
    </div>
  );
}
