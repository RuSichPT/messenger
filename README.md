# Messenger

## Обзор

Тестовый проект **RESTfull API service** представляет собой сервис, позволяющий пользователям
отправлять сообщения друг другу (аналог мессенджера).

## Использованные технологии
- Java 17
- Spring Boot
- Spring (Web, Data, Security, Test)
- JWT
- Maven
- Lombok
- PostgresSQL

## Дополнительно реализовано
- подтверждение почты через ссылку в письме
- PostgresSQL
- Spring Security JWT
- написание тестов

## Запуск
Создать базу данных

    localhost:5432/messenger_db  

Заполнить в application.yml или создать переменные среды:

    ${POSTGRES_USERNAME}  
    ${POSTGRES_PASS}  
    ${GMAIL}
    ${GMAIL_APP_PASS}

# Messenger API:

http://localhost:8081/swagger-ui/index.html

## Примеры:

### Authentication API

##### Регистрация нового пользователя:

    curl -X 'POST' \
    'http://localhost:8081/api/v1/auth/signup' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "user3",
    "password": "123",
    "email": "user3@mail.ru",
    "name": "Name",
    "surname": "Surname"
    }'

##### Авторизация пользователя:

    curl -X 'POST' \
    'http://localhost:8081/api/v1/auth/signin' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "user3",
    "password": "123"
    }'

##### Выход из сервиса:
    curl -X 'GET' \
    'http://localhost:8081/api/v1/auth/signout' \
    -H 'accept: */*' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjk3Mzg5MjYzLCJleHAiOjE2OTc0NzU2NjN9.dz8WL8wvL4Rts0nDh6vEkvOTBSWgjosKICaoLqiYOkM38e_bJGCRCg1_EuyLD18C'

##### Обновление профиля пользователя:

    curl -X 'PUT' \
    'http://localhost:8081/api/v1/users/update/profile' \
    -H 'accept: application/json' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjk3Mzg5NTA1LCJleHAiOjE2OTc0NzU5MDV9.N8QwANaGFP-aEu1vmAPDBE4ahn_H3sYP3t-T3jpZi4LDOd82bqNMlJTcnhGWYKFU' \
    -H 'Content-Type: application/json' \
    -d '{
    "username": "user31",
    "email": "usser31@mail.ru",
    "name": "Name1",
    "surname": "Surname1"
    }'

##### Обновление пароля пользователя:

    curl -X 'PATCH' \
    'http://localhost:8081/api/v1/users/update/password' \
    -H 'accept: */*' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjk3Mzg5NTA1LCJleHAiOjE2OTc0NzU5MDV9.N8QwANaGFP-aEu1vmAPDBE4ahn_H3sYP3t-T3jpZi4LDOd82bqNMlJTcnhGWYKFU' \
    -H 'Content-Type: application/json' \
    -d '"4444"'

##### Удаление пользователя:

    curl -X 'DELETE' \
    'http://localhost:8081/api/v1/users/delete' \
    -H 'accept: */*' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjk3Mzg5NTA1LCJleHAiOjE2OTc0NzU5MDV9.N8QwANaGFP-aEu1vmAPDBE4ahn_H3sYP3t-T3jpZi4LDOd82bqNMlJTcnhGWYKFU'

### Message API

##### Отправка сообщения пользователю:

    curl -X 'POST' \
    'http://localhost:8081/api/v1/message/send' \
    -H 'accept: */*' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjk3MzkyMTQzLCJleHAiOjE2OTc0Nzg1NDN9.XvRDbhQwKr7c5FnLAdTVTF2KKN5ItyrGM3vF2z5FhhyT6z5M6lc0Ii1UEbHWK5KI' \
    -H 'Content-Type: application/json' \
    -d '{
    "usernameTo": "user2",
    "content": "Привет"
    }'

##### Получение истории общения:

    curl -X 'POST' \
    'http://localhost:8081/api/v1/message/story' \
    -H 'accept: */*' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjk3MzkyMTQzLCJleHAiOjE2OTc0Nzg1NDN9.XvRDbhQwKr7c5FnLAdTVTF2KKN5ItyrGM3vF2z5FhhyT6z5M6lc0Ii1UEbHWK5KI' \
    -H 'Content-Type: application/json' \
    -d '{
    "usernameFrom": "user2",
    "pageNumber": 0,
    "pageSize": 10
    }'

## Временная почта для регистрации

https://temp-mail.org/ru/