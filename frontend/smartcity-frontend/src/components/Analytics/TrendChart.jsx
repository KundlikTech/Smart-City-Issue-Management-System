import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";
import styles from "./TrendChart.module.css";

const TrendChart = ({ data = [], xKey = "date", yKey = "count" }) => {
  // Ensure data in required format; if strings -> try to transform
  const safeData = (data || []).map((d) => {
    const copy = { ...d };
    // sometimes mysql returns date objects or string - keep as-is
    return copy;
  });

  return (
    <div className={styles.wrapper}>
      <ResponsiveContainer width="100%" height={260}>
        <LineChart data={safeData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey={xKey} />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey={yKey} stroke="#0b74de" strokeWidth={2} dot={{ r: 3 }} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default TrendChart;
