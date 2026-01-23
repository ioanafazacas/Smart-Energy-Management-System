import { useEffect, useState } from 'react';
import { connectWebSocket, disconnectWebSocket } from '../services/websocket';

export default function OverconsumptionNotifications() {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    connectWebSocket((notification) => {
      setNotifications((prev) => [notification, ...prev]);

      alert(
        `⚠️ Overconsumption!\nDevice: ${notification.deviceId}\nValue: ${notification.measurementValue}`,
      );
    });

    return () => {
      disconnectWebSocket();
    };
  }, []);

  return (
    <div>
      <h2>⚠️ Overconsumption Alerts</h2>

      {notifications.length === 0 && <p>No alerts yet.</p>}

      <ul>
        {notifications.map((n, index) => (
          <li key={index}>
            <strong>Device:</strong> {n.deviceId} <br />
            <strong>Value:</strong> {n.measurementValue} <br />
            <strong>Limit:</strong> {n.maxConsumption} <br />
            <strong>Time:</strong> {new Date(n.timestamp).toLocaleString()}
          </li>
        ))}
      </ul>
    </div>
  );
}
