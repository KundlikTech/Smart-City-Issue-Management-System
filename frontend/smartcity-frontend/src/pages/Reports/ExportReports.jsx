import { useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import styles from "./ExportReports.module.css";

const ExportReports = () => {
  const [msg, setMsg] = useState("");
  const [months, setMonths] = useState(6);

  const exportCsv = async () => {
    try {
      const res = await api.get(`/admin/export?type=monthly&months=${months}`, {
        responseType: "blob"
      });

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "report.csv");
      document.body.appendChild(link);
      link.click();

      setMsg("Download Started!");
    } catch (err) {
      console.error(err);
      setMsg("Failed to export.");
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Export Reports</h2>

        <label>Months</label>
        <select value={months} onChange={(e) => setMonths(e.target.value)}>
          <option value="3">Last 3 Months</option>
          <option value="6">Last 6 Months</option>
          <option value="12">Last 12 Months</option>
        </select>

        <button onClick={exportCsv}>Download CSV</button>

        {msg && <p>{msg}</p>}
      </div>
    </DashboardLayout>
  );
};

export default ExportReports;
