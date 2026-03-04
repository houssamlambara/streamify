# 🎬 Streamify — Plateforme de Streaming en Microservices

Projet Spring Boot multi-modules basé sur une architecture microservices avec Spring Cloud.

---

## 🛠️ Stack technique

| Technologie | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Cloud | 2023.0.1 |
| Base de données | PostgreSQL |
| Build | Maven |

---

## 📦 Modules

Le projet est un **Maven multi-module** composé de 5 services :

```
streamify/
├── config-service      → Serveur de configuration centralisée (port 8888)
├── discovery-service   → Registre de services Eureka (port 8761)
├── gateway-service     → Point d'entrée unique (API Gateway)
├── user-service        → Gestion des utilisateurs (port 8081)
└── video-service       → Gestion des vidéos
```

---

## 🔎 Description de chaque service

### ⚙️ config-service
- Fournit la configuration centralisée à tous les autres services.
- Dépendances : `spring-boot-starter-web`, `spring-cloud-starter-netflix-eureka-server`

### 🗺️ discovery-service
- Registre Eureka : tous les services s'y enregistrent et le consultent pour se localiser entre eux.
- Dépendances : `spring-cloud-starter-netflix-eureka-server`

### 🚪 gateway-service
- Point d'entrée unique pour toutes les requêtes externes.
- Route les requêtes vers les bons services via Eureka.
- Se connecte au `config-service` pour sa configuration.
- Dépendances : `spring-cloud-starter-gateway`, `eureka-client`, `config-client`

### 👤 user-service
- Gère les utilisateurs (inscription, authentification, profils).
- Se connecte au `config-service` via `http://localhost:8888`.
- Dépendances : `spring-web`, `spring-data-jpa`, `spring-security`, `validation`, `postgresql`, `eureka-client`, `config-client`, `openfeign`, `lombok`

### 🎥 video-service
- Gère les vidéos (upload, métadonnées, streaming).
- Dépendances : `spring-web`, `spring-data-jpa`, `postgresql`, `eureka-client`, `config-client`, `validation`

---

## 🚀 Ordre de démarrage

Les services doivent être lancés dans cet ordre :

```
1. config-service      (les autres ont besoin de lui pour démarrer)
2. discovery-service   (Eureka doit être disponible avant les clients)
3. gateway-service
4. user-service
5. video-service
```

---

## ⚙️ Configuration actuelle

| Service | Port | Config Server | Eureka |
|---|---|---|---|
| config-service | 8888 (par défaut) | — | Serveur |
| discovery-service | 8761 (par défaut) | — | Serveur |
| gateway-service | — | `http://localhost:8888` | Client |
| user-service | **8081** | `http://localhost:8888` | Client |
| video-service | — | `http://localhost:8888` | Client |

---

## ▶️ Lancer le projet

L'ordre de démarrage est strict et chaque service doit logiquement être lancé dans un terminal dédié.

### 1. Config Service
```bash
cd config-service
mvnw.cmd spring-boot:run
```

### 2. Discovery Service (Eureka)
```bash
cd discovery-service
mvnw.cmd spring-boot:run
```

### 3. Gateway Service
```bash
cd gateway-service
mvnw.cmd spring-boot:run
```

### 4. User & Video Services
```bash
cd user-service
mvnw.cmd spring-boot:run
```

```bash
cd video-service
mvnw.cmd spring-boot:run
```

---

## 📁 Structure interne des services métier

Les services `user-service` et `video-service` suivent la même architecture en couches :

```
src/main/java/com/houssam/<service>/
├── config/         → Beans de configuration Spring
├── controller/     → Endpoints REST
├── dto/            → Objets de transfert de données
├── exception/      → Gestion des erreurs
├── model/          → Entités JPA
├── repository/     → Interfaces Spring Data
└── service/        → Logique métier
```

