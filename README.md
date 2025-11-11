# 🧩 Smart Energy Management System — Microservices Project

Acest proiect reprezintă o aplicație distribuită bazată pe **microservicii Spring Boot**, care gestionează utilizatori, autentificare și dispozitive energetice.
Frontend-ul ReactJS comunică cu aceste servicii printr-un **API Gateway Traefik**, oferind o interfață centralizată pentru administrare și vizualizare.

---

## 🚀 Arhitectura generală

+-------------------+
| React Frontend |
| (localhost:3000) |
+--------+----------+
|
v
+-------------------+
| Traefik Proxy |
| (router / gateway)|
+----+------+-------+
| |
v v
+----------+-----------+
| Auth Service |--> DB Auth (PostgreSQL:5435)
| http://auth.localhost|

+----------------------+
| User Management |--> DB User (PostgreSQL:5433)
| http://user.localhost|

+----------------------+
| Device Service |--> DB Device (PostgreSQL:5434)
| http://device.localhost|

+----------------------+


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
docker compose build
docker compose up -d

### 2️⃣ Verifică containerele
docker ps


#### Ar trebui să vezi:

authentication

user-management

device

frontend

traefik

db_auth, db_user, db_device

### 3️⃣ Accesează serviciile:
Serviciu	URL
Frontend	http://localhost:3000

Auth Service	http://auth.localhost/swagger-ui.html

User Service	http://user.localhost/swagger-ui.html

Device Service	http://device.localhost/swagger-ui.html

Traefik Dashboard	http://localhost:8080/dashboard

## 🧠 Tehnologii folosite
Componentă	Tehnologie
Frontend	ReactJS, TailwindCSS, Axios
API Gateway	Traefik 3.2
Backend (Microservicii)	Spring Boot 3.3.4
Baze de date	PostgreSQL 15
Build tools	Maven, Docker
Documentație API	SpringDoc OpenAPI / Swagger UI
Securitate	JWT, Spring Security
🔄 Fluxuri principale
🔐 Autentificare

Utilizatorul se înregistrează prin auth.localhost/auth/register

Primește JWT prin auth.localhost/auth/login

Tokenul este folosit pentru autentificare pe celelalte servicii

👥 Administrare utilizatori

Adminul vizualizează utilizatori în frontend

Poate edita sau șterge un utilizator

La ștergere → se șterg și dispozitivele asociate (device-service + auth-service)

⚙️ Gestionare dispozitive

Fiecare dispozitiv are userId

Dispozitivele pot fi adăugate, modificate sau șterse

La ștergerea unui user → device-service șterge automat dispozitivele aferente

🧾 Documentație API (Swagger)
Serviciu	Path	Metodă	Descriere scurtă	Răspuns
Auth	/auth/register	POST	Creează utilizator nou	UserDetailsDTO
Auth	/auth/login	POST	Autentificare + JWT	LoginResponseDTO
Auth	/auth/me	GET	Returnează utilizatorul curent	UserDTO
User	/user/all	GET	Lista tuturor utilizatorilor	List<UserDetailsDTO>
User	/user/{id}	GET	Obține detaliile unui utilizator	UserDetailsDTO
User	/user/{id}	PUT	Actualizează datele utilizatorului	UserDetailsDTO
User	/user/{id}	DELETE	Șterge utilizator + device-uri	204 No Content
Device	/device/create	POST	Creează dispozitiv	DeviceDTO
Device	/device/all	GET	Lista dispozitivelor	List<DeviceDTO>
Device	/device/user/{id}	GET	Dispozitivele unui utilizator	List<DeviceDTO>
Device	/device/{id}	PUT	Actualizează dispozitiv	DeviceDTO
Device	/device/{id}	DELETE	Șterge dispozitiv	204 No Content