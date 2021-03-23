# **Shopstick BE**

## Description

I am building an application by using Springboot web services (RESTful API) &amp;
HTML (Thymeleaf) to act as a simple e-commerce shop used by both Customer &amp; Shop
Owner. I have used **Spring Boot**, **Spring Data JPA** with **H2 database**, for views i have used **Thymeleaf** template engine and **Bootstrap** CSS framework. Moreover I included  **Springfox** to generate the swagger to document the APIs endpoints.

## Screenshots



## Installation

You can clone this repository and use it locally:
```sh
$ git clone https://github.com/Frallallero/shopstick.git
```

**Using Maven plugin**

First you should do clean installation:
```sh
$ mvn clean install
```
You can start application using Spring Boot command:
```sh
$ mvn spring-boot:run
```

**Using Maven plugin and running JAR**

You can create JAR file using:
```sh
$ mvn clean package
```
and then run it with:
```sh
$ java -jar target/shopstick-core-x.x.x.jar
```

## Login

To login need to run the web component and connect to:

Login page: ```http://localhost:8082/login```

Initially there are 2 shop users stored:

Login: ```admin``` Password: ```admin``` with **ADMIN** role.

Login: ```user``` Password: ```user``` with **CUSTOMER** role.

## Roles

**ADMIN** can add and display products.

**USER** can add products to shopping cart, update the single items quantity, delete from shopping cart and complete a purchase.

## Payment

One payment method is hard-coded to simulate a valid payment:

Card number: ```1234``` CVV: ```000``` .

## Tests


You can run tests using:
```sh
$ mvn test
```

## License

Project is based on **MIT License**. You can read about the license <a href="LICENSE">here</a>.
