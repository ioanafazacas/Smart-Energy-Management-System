import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;

export const connectChat = (userId, onMessageReceived) => {
  const socket = new SockJS('http://websocket.localhost/chat-websocket');


  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    onConnect: () => {
      stompClient.subscribe(`/topic/${userId}`, (message) => {
        onMessageReceived(JSON.parse(message.body));
      });
    },
  });

  stompClient.activate();
};

export const sendChatMessage = (message) => {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: '/app/chat.sendMessage',
      body: JSON.stringify(message),
    });
  }
};
