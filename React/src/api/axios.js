import axios from 'axios';

const api = axios.create({
  baseURL: 'http://auth.localhost', // 🔥 direcționează cererile către microserviciul corect
});

export default api;
