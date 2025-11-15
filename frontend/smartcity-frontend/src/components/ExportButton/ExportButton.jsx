import api from "../../utils/axiosClient";
import styles from "./ExportButton.module.css";

const ExportButton = ({ endpoint, filename = "export.csv" }) => {
  const handleExport = async () => {
    try {
      const res = await api.get(endpoint, { responseType: "blob" });
      const blob = new Blob([res.data], { type: "text/csv;charset=utf-8;" });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = filename;
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    } catch (err) {
      console.error("Export failed", err);
      alert("Export failed. Check server logs.");
    }
  };

  return (
    <button className={styles.btn} onClick={handleExport}>
      Download CSV
    </button>
  );
};

export default ExportButton;
