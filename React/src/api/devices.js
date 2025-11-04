import axios from 'axios';

export const getDevices = async () => (await axios.get('/devices')).data;
export const createDevice = async (device) =>
  (await axios.post('/devices', device)).data;
export const deleteDevice = async (id) =>
  (await axios.delete(`/devices/${id}`)).data;
export const getDevicesByUser = async (userId) =>
  (await axios.get(`/devices/user/${userId}`)).data;
