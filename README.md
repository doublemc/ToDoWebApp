ToDoWebApp is a **REST API** that lets you add, manage and complete your own ToDos.

It was created as a learning experience for Spring and Hibernate. It's a backend app that should be 'consumed' by a frontend REST client eg. Angular JS.
It supports basic CRUD operations via HTTP requests.
Stack used:
* **Spring Boot** 
* **Spring Security** for Basic Auth implementation and hashing passwords 
* **Hibernate** for ORM
* **H2 in-memory database** to store registered users and their ToDos
* **JUnit & Mockito** for unit and integration testing

Usage example:

POST @ localhost:8080/users with JSON:
`{
	"username":"doublemc",
	"password":"password",
	"email":"doublemc@example.com"
}`

registers new user and responses with:
HTTP.Status: 201 Created `{
  "status": "User created."
}`


 
