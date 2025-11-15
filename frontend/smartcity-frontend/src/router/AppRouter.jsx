import { BrowserRouter, Routes, Route } from "react-router-dom";
import ProtectedRoute from "../routes/ProtectedRoute";

// 🔹 AUTH
import Login from "../pages/Auth/Login";
import Register from "../pages/Auth/Register";

// 🔹 USER
import UserDashboard from "../pages/UserDashboard/UserDashboard";
import ReportIssue from "../pages/UserReportIssue/UserReportIssue";
import MyIssues from "../pages/MyIssues/MyIssues";

// 🔹 ADMIN
import AdminDashboard from "../pages/AdminDashboard/AdminDashboard";
import AdminIssues from "../pages/AdminIssues/AdminIssues";
import Assignments from "../pages/Assignments/Assignments";
import TopDepartments from "../pages/Reports/TopDepartments";
import SlaBreaches from "../pages/Reports/SlaBreaches";
import AverageTimes from "../pages/Reports/AverageTimes";
import ExportReports from "../pages/Reports/ExportReports";

// 🔹 COMMON
import MapView from "../pages/MapView/MapView";
import Notifications from "../pages/Notifications/Notifications";
import NotFound from "../pages/NotFound/NotFound";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* PUBLIC */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* USER ROUTES */}
        <Route
          path="/dashboard"
          element={<ProtectedRoute roles={["USER"]}><UserDashboard /></ProtectedRoute>}
        />
        <Route
          path="/user/report"
          element={<ProtectedRoute roles={["USER"]}><ReportIssue /></ProtectedRoute>}
        />
        <Route
          path="/user/my-issues"
          element={<ProtectedRoute roles={["USER"]}><MyIssues /></ProtectedRoute>}
        />

        {/* ADMIN ROUTES */}
        <Route
          path="/admin/dashboard"
          element={<ProtectedRoute roles={["ADMIN"]}><AdminDashboard /></ProtectedRoute>}
        />
        <Route
          path="/admin/issues"
          element={<ProtectedRoute roles={["ADMIN"]}><AdminIssues /></ProtectedRoute>}
        />
        <Route
          path="/admin/assignments"
          element={<ProtectedRoute roles={["ADMIN"]}><Assignments /></ProtectedRoute>}
        />
        <Route
          path="/admin/top-departments"
          element={<ProtectedRoute roles={["ADMIN"]}><TopDepartments /></ProtectedRoute>}
        />
        <Route
          path="/admin/sla-breaches"
          element={<ProtectedRoute roles={["ADMIN"]}><SlaBreaches /></ProtectedRoute>}
        />
        <Route
          path="/admin/avg-times"
          element={<ProtectedRoute roles={["ADMIN"]}><AverageTimes /></ProtectedRoute>}
        />
        <Route
          path="/admin/export"
          element={<ProtectedRoute roles={["ADMIN"]}><ExportReports /></ProtectedRoute>}
        />

        {/* COMMON ROUTES */}
        <Route path="/map" element={<ProtectedRoute><MapView /></ProtectedRoute>} />
        <Route path="/notifications" element={<ProtectedRoute><Notifications /></ProtectedRoute>} />

        {/* 404 */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}
