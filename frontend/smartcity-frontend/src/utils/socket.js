import { Client } from "@stomp/stompjs";

let socketClient = null;

export const connectSocket = (onMessage) => {
  socketClient = new Client({
    brokerURL: "ws://localhost:8080/ws",
    reconnectDelay: 5000,

    onConnect: () => {
      console.log("Connected to WebSocket");

      // LISTEN FOR NOTIFICATIONS
      socketClient.subscribe("/topic/notifications", (msg) => {
        onMessage(JSON.parse(msg.body));
      });

      // LISTEN FOR MAP UPDATES
      socketClient.subscribe("/topic/map-events", (msg) => {
        onMessage(JSON.parse(msg.body));
      });
    },
  });

  socketClient.activate();
};

export const disconnectSocket = () => {
  if (socketClient) socketClient.deactivate();
};
