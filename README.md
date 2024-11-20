# Currency Converter API

A **Spring Boot-based service** that integrates with the [Currency API](https://currencyapi.com/) to fetch and process currency data. The service allows you to retrieve detailed information about various currencies, including symbols, names, rounding behavior, and more.

---

## Features

- Fetch real-time currency data from a third-party API.
- Automatically map API responses to structured **DTOs** for easy usage.
- Scalable and configurable API key authentication.
- Uses **Spring Boot** for service architecture and **Jackson** for JSON parsing.

---

## Getting Started

### Prerequisites

Before you start, ensure you have the following installed:

- **Java 17** or later
- **Gradle** for dependency management
- A valid API key from [Currency API](https://currencyapi.com/) (written in application.properties)