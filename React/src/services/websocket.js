import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;

export const connectWebSocket = (onMessage) => {
  stompClient = new Client({
    webSocketFactory: () => new SockJS('http://localhost:8085/ws'),
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('✅ WebSocket connected');

      stompClient.subscribe('/topic/overconsumption', (message) => {
        const notification = JSON.parse(message.body);
        onMessage(notification);
      });
    },
    onStompError: (frame) => {
      console.error('❌ STOMP error', frame);
    },
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    console.log('🔌 WebSocket disconnected');
  }
};
