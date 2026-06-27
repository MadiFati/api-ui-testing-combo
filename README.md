# api-ui-testing-combo

![Tests](https://github.com/MadiFati/api-ui-testing-combo/actions/workflows/tests.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![RestAssured](https://img.shields.io/badge/RestAssured-5.4-green)
![Selenium](https://img.shields.io/badge/Selenium-4.18-blue)
![JUnit5](https://img.shields.io/badge/JUnit-5.10-red)

Framework de test combinant **API (RestAssured)** et **UI (Selenium)** dans un même projet JUnit 5.

---

## Le pattern clé : API setup → UI verification

```
┌─────────────────┐      ┌──────────────────┐
│   REST API      │      │   Browser (UI)   │
│  (RestAssured)  │  →   │   (Selenium 4)   │
│                 │      │                  │
│ 1. Fetch user   │      │ 2. Open profile  │
│    via GET /    │      │    page in       │
│    users/1      │      │    browser       │
│                 │      │ 3. Verify name   │
│                 │      │    and email     │
└─────────────────┘      └──────────────────┘
```

Ce pattern montre que tu penses **"qualité bout en bout"** — les données créées ou lues via API sont validées dans l'interface réelle.

---

## Structure

```
src/
├── main/java/com/example/
│   ├── api/
│   │   ├── clients/   ← UserApiClient (RestAssured)
│   │   └── models/    ← User POJO (Jackson)
│   └── ui/
│       ├── pages/     ← Page Objects Selenium
│       └── utils/     ← DriverManager thread-safe
└── test/java/com/example/
    ├── api/           ← Tests API purs (rapides, sans browser)
    ├── ui/            ← Tests UI isolés
    └── e2e/           ← Tests combinés API + UI ⭐
docker-compose.yml     ← Selenium Grid local
```

---

## Lancer les tests

```bash
# Tests API uniquement (rapide, ~10s)
mvn test -Dgroups="api"

# Tests E2E complets (API + UI)
mvn test -Dgroups="e2e" -DHEADLESS=true

# Avec Selenium Grid Docker
docker compose up -d
mvn test -DHEADLESS=true
docker compose down
```

---

## Stack

| Layer | Outil |
|---|---|
| API testing | RestAssured 5.4 |
| UI testing | Selenium 4.18 |
| Test framework | JUnit 5.10 |
| JSON mapping | Jackson 2.17 |
| Reporting | Allure 2.25 |
| CI/CD | GitHub Actions |
| Grid | Docker + Selenium Grid 4 |

---

## Auteur

**Fatima El Madini** — Senior Test Automation Engineer  
[LinkedIn](https://www.linkedin.com/in/fatimazahra-el-madini-58352210b/)· [Malt](https://malt.fr/profile/yourprofile)  
Portfolio : [Projet 1](https://github.com/MadiFati/selenium-e2e-framework) · [Projet 3](https://github.com/MadiFati/selenium-audit-toolkit)
