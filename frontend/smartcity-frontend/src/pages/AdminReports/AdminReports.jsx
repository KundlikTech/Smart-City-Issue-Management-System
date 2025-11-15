import { useEffect, useState } from "react";
import DashboardLayout from "../../components/Layout/DashboardLayout";
import api from "../../utils/axiosClient";
import ExportButton from "../../components/ExportButton/ExportButton";
import TrendChart from "../../components/Analytics/TrendChart";
import styles from "./AdminReports.module.css";

const AdminReports = () => {
  const [stats, setStats] = useState(null);
  const [weekTrend, setWeekTrend] = useState([]);
  const [monthTrend, setMonthTrend] = useState([]);
  const [exportType, setExportType] = useState("monthly");
  const [months, setMonths] = useState(6);

  useEffect(() => {
    fetchStats();
    fetchTrends();
  }, []);

  const fetchStats = async () => {
    try {
      const res = await api.get("/admin/summary");
      setStats(res.data);
    } catch (err) {
      console.error("Failed to fetch admin summary", err);
    }
  };

  const fetchTrends = async () => {
    try {
      const w = await api.get("/admin/trend/week");
      setWeekTrend(w.data || []);
    } catch (e) {
      console.warn("week trend failed", e);
    }
    try {
      const m = await api.get("/admin/trend/month");
      setMonthTrend(m.data || []);
    } catch (e) {
      console.warn("month trend failed", e);
    }
  };

  return (
    <DashboardLayout>
      <div className={styles.container}>
        <h2>Admin Reports & Analytics</h2>

        <section className={styles.summary}>
          <h3>Summary</h3>
          {stats ? (
            <div className={styles.cards}>
              <div className={styles.card}>
                <p>Total Issues</p>
                <strong>{stats.totalIssues}</strong>
              </div>
              <div className={styles.card}>
                <p>Pending</p>
                <strong>{stats.pending}</strong>
              </div>
              <div className={styles.card}>
                <p>In Progress</p>
                <strong>{stats.inProgress}</strong>
              </div>
              <div className={styles.card}>
                <p>Resolved</p>
                <strong>{stats.resolved}</strong>
              </div>
              <div className={styles.card}>
                <p>Open Assignments</p>
                <strong>{stats.openAssignments}</strong>
              </div>
            </div>
          ) : (
            <p>Loading summary...</p>
          )}
        </section>

        <section className={styles.analytics}>
          <h3>Trends</h3>

          <div className={styles.chartsRow}>
            <div className={styles.chartBox}>
              <h4>Last 7 Days</h4>
              <TrendChart data={weekTrend} xKey="date" yKey="count" />
            </div>

            <div className={styles.chartBox}>
              <h4>Last 6 Months</h4>
              <TrendChart data={monthTrend} xKey="month" yKey="count" />
            </div>
          </div>
        </section>

        <section className={styles.exportSection}>
          <h3>Export Reports (CSV)</h3>
          <div className={styles.exportControls}>
            <label>
              Type:
              <select value={exportType} onChange={(e) => setExportType(e.target.value)}>
                <option value="monthly">Monthly</option>
                <option value="quarterly">Quarterly</option>
                <option value="yearly">Yearly</option>
              </select>
            </label>

            <label>
              Months:
              <input
                type="number"
                min="1"
                max="24"
                value={months}
                onChange={(e) => setMonths(Number(e.target.value))}
              />
            </label>

            <ExportButton
              endpoint={`/admin/export?type=${exportType}&months=${months}`}
              filename={`report_${exportType}_${months}.csv`}
            />
          </div>

          <p className={styles.hint}>
            The exported CSV contains issue list, assignments and timestamps. Use Excel or Google Sheets to open.
          </p>
        </section>
      </div>
    </DashboardLayout>
  );
};

export default AdminReports;
