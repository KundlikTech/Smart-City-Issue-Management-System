🌆 Smart City Issue Management System

A full-stack Smart City Issue Management Platform built with Spring Boot, React + Vite, MySQL, and WebSockets.
Citizens can report issues with GPS locations, while admins track, assign, and resolve them with real-time updates.

🚀 Features
👤 User Features

Register & Login (JWT Authentication)

Report issues with GPS location

View issue history

Live issue status updates

Real-time notifications

🛠 Admin Features

Interactive dashboard with analytics

Automatic department assignment

Assign issues to departments

Update issue status

Manage active & completed assignments

CSV report export

Monthly & weekly trend charts

🌍 Map Features

Display issues on map

Real-time map updates

Distance-based issue search

User geolocation support

🧰 Tech Stack
🔹 Backend

Java Spring Boot

Spring Security (JWT Auth)

Spring WebSocket (STOMP)

MySQL

JPA / Hibernate

Lombok

🔹 Frontend

React + Vite

Axios

React Router

CSS Modules

Chart.js

Leaflet.js (Map Library)

📦 Installation & Setup
🔧 Backend Setup
cd backend
mvn clean install
mvn spring-boot:run

💻 Frontend Setup
cd frontend
npm install
npm run dev

⚙️ Database Setup (MySQL)

Create the database:

CREATE DATABASE smartcity_db;


Update application.properties if needed:

spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

🔑 API Authentication (JWT)

Every API call must include:

Authorization: Bearer <token>

📊 Admin Dashboard APIs
Endpoint	Description
/api/admin/stats	Overall issue statistics
/api/admin/trend/week	Last 7-day trends
/api/admin/trend/month	Last 6-month trends
/api/admin/recent-issues	Paginated recent issues
/api/admin/export	Download CSV report
🔔 Real-time Updates (WebSockets)

Endpoint

ws://localhost:8080/ws


Subscribed Channels

/topic/status — issue status updates

/topic/map — live map events

/user/queue/assignments — direct admin notifications

🧑‍💻 Project Structure
backend/
 └─ src/main/java/com/smartcity/...

frontend/
 └─ src/pages/
 └─ src/components/
 └─ src/utils/
