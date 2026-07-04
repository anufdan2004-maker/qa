# Java QA Portfolio

Учебный пет-проект по автоматизации тестирования на Java. Демонстрирует базовые
навыки написания API- и UI-автотестов.

## Стек

- Java 17
- Maven
- JUnit 5 — тестовый фреймворк
- REST Assured — автотесты API
- Selenide (на основе Selenium WebDriver) — автотесты UI
- Hamcrest — матчеры для проверок

## Структура

```
src/test/java/api/PostsApiTest.java   — API-тесты (JSONPlaceholder)
src/test/java/ui/LoginTest.java       — UI-тесты (saucedemo.com)
```

## Что покрыто

**API-тесты (JSONPlaceholder — https://jsonplaceholder.typicode.com):**
- GET одного поста и списка постов
- GET несуществующего ресурса (404)
- POST — создание поста
- PUT — обновление поста
- DELETE — удаление поста

**UI-тесты (saucedemo.com — учебный демо-магазин):**
- Успешный логин
- Логин заблокированным пользователем
- Логин с неверным паролем
- Добавление товара в корзину

## Запуск

Требуется установленный Chrome (Selenide сам скачивает нужный драйвер).

```bash
mvn clean test
```

Запустить только API-тесты:
```bash
mvn test -Dtest=PostsApiTest
```

Запустить только UI-тесты:
```bash
mvn test -Dtest=LoginTest
```

## Автор

Данила Ануфриев — QA Engineer (manual), в процессе перехода в Java AQA.
