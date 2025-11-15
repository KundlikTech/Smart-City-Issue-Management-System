import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Auth/Login/Login";
import Register from "./pages/Auth/Register/Register";

import AdminDashboard from "./pages/AdminDashboard/AdminDashboard";
import AdminIssues from "./pages/AdminIssues/AdminIssues";
import AdminReports from "./pages/AdminReports/AdminReports";
import IssueAssign from "./pages/IssueAssign/AssignIssue";
import IssueList from "./pages/IssuesList/IssuesList";

import UserDashboard from "./pages/UserDashboard/UserDashboard";
import UserReportIssue from "./pages/UserReportIssue/UserReportIssue";
import MyIssues from "./pages/MyIssues/MyIssues";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* default */}
        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* User */}
        <Route path="/user/dashboard" element={<UserDashboard />} />
        <Route path="/user/report" element={<UserReportIssue />} />
        <Route path="/user/my-issues" element={<MyIssues />} />

        {/* Admin */}
        <Route path="/admin/dashboard" element={<AdminDashboard />} />
        <Route path="/admin/issues" element={<AdminIssues />} />
        <Route path="/admin/issues/assign/:issueId" element={<IssueAssign />} />
        {/* If you have a separate issue list page */}
        <Route path="/admin/issues/list" element={<IssueList />} />

        <Route path="/admin/reports" element={<AdminReports />} />


        {/* fallback */}
        <Route path="*" element={<Navigate to="/login" replace />} />


      </Routes>
    </BrowserRouter>
  );
}

export default App;
