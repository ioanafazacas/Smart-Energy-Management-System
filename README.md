# 🧩 Smart Energy Management System — Microservices Project

Acest proiect reprezintă o aplicație distribuită bazată pe **microservicii Spring Boot**, care gestionează utilizatori, autentificare și dispozitive energetice.
Frontend-ul ReactJS comunică cu aceste servicii printr-un **API Gateway Traefik**, oferind o interfață centralizată pentru administrare și vizualizare.

---

## 🧱 Microservicii

### 🔐 **Authentication Service**
Responsabil pentru:
- înregistrarea utilizatorilor noi (`/auth/register`)
- autentificare și generare JWT (`/auth/login`)
- obținerea datelor utilizatorului curent (`/auth/me`)
- ștergerea utilizatorilor (`/auth/{id}`)

📘 **Swagger:** [http://auth.localhost/swagger-ui.html](http://auth.localhost/swagger-ui.html)

---

### 👤 **User Management Service**
Gestionează datele detaliate ale utilizatorilor:
- CRUD utilizatori (`/user`)
- sincronizare cu serviciul de autentificare
- ștergere automată a dispozitivelor unui utilizator

📘 **Swagger:** [http://user.localhost/swagger-ui.html](http://user.localhost/swagger-ui.html)

---

### ⚙️ **Device Management Service**
Gestionează dispozitivele conectate:
- creare, actualizare, ștergere dispozitive (`/device`)
- listare dispozitive după utilizator (`/device/user/{id}`)
- integrare cu User Service

📘 **Swagger:** [http://device.localhost/swagger-ui.html](http://device.localhost/swagger-ui.html)

---

### 💻 **Frontend (ReactJS)**
- Dashboard pentru administrator
- CRUD utilizatori și dispozitive
- Autentificare + logout
- Mesaje de succes / eroare
- Comunicare REST prin Axios


---

## 🐳 Configurare și rulare cu Docker

### 1️⃣ Build & Start
```bash
docker compose build
docker compose up -d
```

### 2️⃣ Verifică containerele
```bash
docker ps
```

#### Ar trebui să vezi:

- authentication
- user-management
- device
- frontend
- traefik
- db_auth, db_user, db_device
