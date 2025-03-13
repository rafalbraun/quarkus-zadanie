# GitHub Repository API - Quarkus Reactive

## 📌 Opis

Ten projekt to API oparte na **Quarkus**, które pozwala na pobieranie listy repozytoriów użytkownika z GitHub, filtrowanie ich oraz zwracanie informacji o branchach i ostatnich commitach w sposób **reaktywny**.

## 🚀 Funkcjonalności

✅ Pobieranie listy **publicznych repozytoriów** użytkownika GitHub, które **nie są forkami**  
✅ Pobieranie dla każdego repozytorium listy **branchy oraz SHA ostatnich commitów**  
✅ Obsługa błędów: zwracanie **404** dla nieistniejących użytkowników  
✅ **Reaktywne API** dzięki `Mutiny` i `Rest Client`

---

## ⚙️ Instalacja i uruchomienie

### 🔧 Wymagania
- Java 17+
- Maven

### 💻 Uruchomienie aplikacji w trybie deweloperskim

```sh
./mvnw quarkus:dev
```

Aplikacja będzie dostępna pod adresem: [http://localhost:8080](http://localhost:8080)

---

## 📡 Endpointy API

### 📝 Pobranie repozytoriów użytkownika

**Request:**
```http
GET /github/repos/{username}
```

**Response (200 OK):**
```json
[
  {
    "name": "example-repo",
    "owner": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc1234"
      }
    ]
  }
]
```

**Response (404 NOT FOUND - użytkownik nie istnieje)**
```json
{
  "status": 404,
  "message": "User {username} not found"
}
```

---

## 🛠️ Technologia
- **Quarkus** (Reaktywny framework Java)
- **SmallRye Mutiny** (Reaktywne programowanie)
- **REST Client** (Integracja z GitHub API)
- **JUnit + RestAssured** (Testowanie API)

---

## ✅ Testowanie

Aby uruchomić testy integracyjne:
```sh
./mvnw test
```

---

## 📜 Licencja

Projekt dostępny na licencji **MIT**.

---

## Dokumentacja Quarkus

https://quarkus.io/guides/

