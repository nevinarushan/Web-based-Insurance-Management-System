# Insurance Company Full-Stack Application

A full-stack insurance management application (Spring Boot backend + React frontend) used for policy, claims and user management.

## Contents
- Backend: Spring Boot (Maven) — located at project root
- Frontend: React — in `insurance-frontend/`
- Sample data and uploaded images: `data/policy_images/`

## Requirements
- Java 11+ (or the version configured in pom.xml)
- Maven 3.6+
- Node 16+ and npm (for frontend)

## Tools & libraries used
- Backend: Java, Spring Boot, Maven (wrapper: mvnw/mvnw.cmd)
- Frontend: React, Node.js, npm
- Key frontend libraries (see insurance-frontend/package.json): axios, bootstrap, react-router-dom, react-scripts, jwt-decode, concurrently (dev), web-vitals, @testing-library/*
- Dev helper: concurrently is used in the frontend dev script to run the React app and the Spring Boot backend together (see `dev` script in insurance-frontend/package.json)
- Proxy: frontend configured to proxy API requests to http://localhost:8080 (see `proxy` in package.json)

## Run (development)
Backend (from project root):

- Build and run with Maven:

  mvnw.cmd spring-boot:run    (on Windows)
  or
  ./mvnw spring-boot:run     (on macOS/Linux)

- Or build the jar and run:

  mvnw.cmd clean package
  java -jar target/Insurance-Company-0.0.1-SNAPSHOT.jar

Frontend (from `insurance-frontend/`):

- Install and start:

  cd insurance-frontend
  npm install
  npm start

The frontend defaults to running on http://localhost:3000 and expects the backend API at the addresses configured in the frontend environment or source files.

## Build for production
- Backend: `mvnw.cmd clean package` -> produces jar in `target/`
- Frontend: `cd insurance-frontend && npm run build` -> produces a production build in `insurance-frontend/build/`

## Project structure (high level)
- src/main/java/ — Spring Boot application and controllers/services
- src/main/resources/ — application.properties and static resources
- insurance-frontend/ — React application
- data/policy_images/ — image assets used by the application

## Notes & Troubleshooting
- Ensure the backend port and frontend proxy (if used) are aligned.
- If static files or images fail to load, verify the paths under `data/policy_images/` and application properties.

## License
This repository does not contain a license file. Add a LICENSE if you need to define usage rights.
