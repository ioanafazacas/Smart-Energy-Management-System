import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import AdminDashboard from './pages/AdminDashboard';
import UserDashboard from './pages/UserDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import Register from './pages/Register';
import MonitoringLive from './components/MonitoringLive';
//import CustomerSupportChat from './components/CustomerSuportChat';
import OverconsumptionNotifications from './components/OverconsumptionNotifications';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
          <Route path="/admin" element={<AdminDashboard />} />
        </Route>
        <Route element={<ProtectedRoute allowedRoles={['USER']} />}>
          <Route path="/user" element={<UserDashboard />} />
          <Route path="/push" element={<MonitoringLive />} />
          <Route path="/alert" element={<OverconsumptionNotifications />} />
          {/*<Route path="/chat" element={<CustomerSupportChat />} />*/}
        </Route>
        <Route path="/register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
