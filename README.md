Проект "Автомаг" job4j_cars
---

>Это веб приложение реализует CRUD-систему с использованием MVC шаблона.

## Содержание

- [Обшая инофмация](#Общая-информация)
- [Технологии](#Технологии)
- [Запуск проекта](#Запуск-проекта)
- [Скриншоты](#Скриншоты)
- [Контакты](#Контакты)

## Общая информация


Проект представляет собой сайт для продажи машин.
Основная страница: таблица со всеми объявлениям машин на продажу.
Все пользователи могут просматривать опубликованные объявления о продаже.
Только зарегистрированные пользователи могут выкладывать объявления.
Изменить статус может только тот пользавать, который создал это объяление.

## Технологии

- Java 17
- Database:
    - PostgreSQL 14
    - H2
- JDBC
- Liquibase
- Hibernate
- Spring boot
- Apache TomCat
- HTML, Thymeleaf, Bootstrap
- Junit, Mockito
- Maven

## Запуск проекта

```
create database cars;
```
```
mvn compile;
```

## Скриншоты

#### Главная страница (Список объявлений)
![ScreenShot](images/list.png)
#### Страница объявления
![ScreenShot](images/post.png)
#### Страница своего объявления с возможностью редактировать статус 
![ScreenShot](images/my_post.png)
#### Страница объявления без фото
![ScreenShot](images/post_without_photo.png)
#### Страница создания объявления
![ScreenShot](images/create.png)
#### Страница Регистрации
![ScreenShot](images/reg.png)
#### Страница Авторизации
![ScreenShot](images/login.png)


## Контакты

https://github.com/ADIKIAA




