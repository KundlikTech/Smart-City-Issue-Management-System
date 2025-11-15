import { useEffect } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import L from "leaflet";
import styles from "./MapView.module.css";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const WorkerTracking = () => {
  useEffect(() => {
    const map = L.map("map2").setView([19.076, 72.8777], 12);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(map);

    const workers = {};

    const socket = new SockJS("http://localhost:8080/ws");
    const client = Stomp.over(socket);

    client.connect({}, () => {
      client.subscribe("/topic/workers", (msg) => {
        const data = JSON.parse(msg.body);

        if (!data.lat || !data.lng) return;

        const workerId = data.workerId;

        if (workers[workerId]) map.removeLayer(workers[workerId]);

        workers[workerId] = L.circleMarker([data.lat, data.lng], {
          radius: 8,
          color: "blue",
          fillColor: "cyan",
          fillOpacity: 0.8,
        })
          .addTo(map)
          .bindPopup(`Worker #${workerId}`);
      });
    });

    return () => client.disconnect();
  }, []);

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Live Worker Tracking</h2>
        <div id="map2" className={styles.map}></div>
      </div>
    </DashboardLayout>
  );
};

export default WorkerTracking;
