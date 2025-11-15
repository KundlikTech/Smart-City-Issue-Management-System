# 🏙️ Smart City Issue Management System

An end-to-end, full-stack platform designed to facilitate real-time issue reporting by citizens and provide a robust administrative dashboard for efficient tracking, assignment, and resolution.



---

## 🚀 Features

### 👤 User Features (Citizen Portal)
* **Secure Access:** **Register & Login** using JWT Authentication.
* **Issue Reporting:** **Report Issues with GPS Location** and detailed descriptions.
* **Tracking:** **View Issue History** and follow its progress.
* **Real-time Updates:** Get **Live Status Updates** and **Real-time Notifications** via WebSockets.

### 🛠 Admin Features (Management Dashboard)
* **Analytics:** Comprehensive **Dashboard with Analytics** (stats and charts).
* **Workflow:** **Auto Department Assignment** based on issue type.
* **Management:** Manually **Assign issues to departments** and **Update issue status**.
* **Reporting:** **CSV Report Export** of all issues.
* **Monitoring:** View **active and completed assignments**.
* **Trend Analysis:** **Monthly / Weekly trend charts** for data-driven decisions.

### 🌍 Map Features
* **Visualization:** **Show issues on map** using location markers.
* **Dynamic View:** **Real-time map events** for newly reported issues.
* **Location Services:** Integrated **Geolocation support** and **Distance-based issue search**.

---

## 🧰 Tech Stack

The project is built upon a modern and reliable stack, ensuring performance, security, and scalability.

### Backend (Spring Boot)
| Component | Purpose |
| :--- | :--- |
| **Java Spring Boot** | Core application framework. |
| **Spring Security (JWT Auth)** | Secure REST endpoints and user authentication. |
| **Spring WebSocket (STOMP)** | Real-time communication for live updates. |
| **MySQL** | Persistent data storage. |
| **JPA/Hibernate** | Object-Relational Mapping (ORM). |
| **Lombok** | Boilerplate code reduction. |

### Frontend (React)
| Component | Purpose |
| :--- | :--- |
| **React + Vite** | Fast and modern user interface development. |
| **Axios** | HTTP client for API interaction. |
| **React Router** | Client-side routing. |
| **CSS Modules** | Scoped and modular styling. |
| **Chart.js** | Rendering beautiful trend and statistics charts. |
| **Leaflet.js (Map)** | Interactive mapping for displaying issue locations. |

---

## 📦 Installation Guide

### ⚙ Database Setup (MySQL)

1.  Ensure your MySQL server is running.
2.  Connect to your server and **create the database**:
    ```sql
    CREATE DATABASE smartcity_db;
    ```
3.  Verify or update the connection details in the `backend/src/main/resources/application.properties`:
    ```properties
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.hibernate.ddl-auto=update
    ```

### 🔹 Backend Setup (Spring Boot)
1.  Navigate to the `backend` directory:
    ```bash
    cd backend
    ```
2.  Clean, install dependencies, and run the application:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    The backend will start, typically running on `http://localhost:8080`.

### 🔹 Frontend Setup (React)
1.  Navigate to the `frontend` directory:
    ```bash
    cd frontend
    ```
2.  Install all required Node modules:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm run dev
    ```
    The frontend will start, typically running on `http://localhost:5173`.

---

## 🔑 API Authentication (JWT)

This project strictly enforces JWT (JSON Web Token) authentication for protected routes.

Every API request to a protected endpoint **must** include the token in the `Authorization` header:

| Header Key | Value Format |
| :--- | :--- |
| `Authorization` | `Bearer <token>` |

---

## 📊 Admin Dashboard APIs

The following REST endpoints are available for administrative operations:

| Endpoint | Description | Authentication |
| :--- | :--- | :--- |
| `/api/admin/stats` | Retrieve aggregate issue statistics for the dashboard. | Required |
| `/api/admin/trend/week` | Get issue submission trend data for the **last 7 days**. | Required |
| `/api/admin/trend/month` | Get issue submission trend data for the **last 6 months**. | Required |
| `/api/admin/recent-issues` | Fetch a **paginated list** of recent issues for the management table. | Required |
| `/api/admin/export` | Trigger a **CSV download** of all recorded issues. | Required |

---

## 🔔 Real-time Features (WebSockets)

The system uses WebSockets (via STOMP) to provide instant communication for critical updates.

**WebSocket endpoint:** `ws://localhost:8080/ws`

### Subscribed Channels

| Channel | Description | Communication Type |
| :--- | :--- | :--- |
| `/topic/status` | Broadcasts real-time **issue status updates** globally. | Public (Topic) |
| `/topic/map` | Broadcasts **map events** (e.g., new issue reported) for map visualization. | Public (Topic) |
| `/user/queue/assignments` | Sends **direct notifications** (e.g., assignment alerts) to the logged-in user. | Private (User Queue) |

---

## 🧑‍💻 Project Structure

| Directory | Description |
| :--- | :--- |
| `smartcity-backend/` | Contains the Java Spring Boot application. |
| `smartcity-backend/src/main/java/com/smartcity/...` | Core Java source code (Controllers, Services, Models, Repositories). |
| `smartcity-frontend/` | Contains the React + Vite web application source code. |
| `smartcity-frontend/src/pages/` | Top-level components and views (e.g., `AdminDashboard.jsx`, `ReportIssue.jsx`). |
| `smartcity-frontend/src/components/` | Reusable UI components (e.g., `IssueCard.jsx`, `MapMarker.jsx`). |
| `smartcity-frontend/src/utils/` | Utility functions, configuration, and API interaction helpers (e.g., `apiClient.js`). |
