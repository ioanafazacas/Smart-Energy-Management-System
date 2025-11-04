import axios from 'axios';

export const getUsers = async () => (await axios.get('/users')).data;
export const createUser = async (user) =>
  (await axios.post('/users', user)).data;
export const deleteUser = async (id) =>
  (await axios.delete(`/users/${id}`)).data;
v