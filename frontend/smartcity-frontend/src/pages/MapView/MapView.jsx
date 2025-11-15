import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import { useEffect, useState } from "react";
import { connectSocket } from "../../utils/socket";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import styles from "./MapView.module.css";

const MapView = () => {
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    connectSocket((msg) => {
      if (msg.type === "ISSUE_STATUS_UPDATED_MAP") {
        setMarkers((prev) => [
          ...prev,
          {
            id: msg.issueId,
            title: msg.issueTitle,
            status: msg.issueStatus,
            lat: msg.latitude,
            lng: msg.longitude,
          },
        ]);
      }
    });
  }, []);

  return (
    <DashboardLayout>
      <h2>Live Issue Map</h2>

      <MapContainer center={[19.076, 72.8777]} zoom={11} className={styles.map}>
        <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

        {markers.map((m) => (
          <Marker key={m.id} position={[m.lat, m.lng]}>
            <Popup>
              <strong>{m.title}</strong><br />
              Status: {m.status}
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </DashboardLayout>
  );
};

export default MapView;
