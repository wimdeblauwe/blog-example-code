# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.wimdeblauwe.examples.viewcomponent-demo' is invalid and this project uses 'com.wimdeblauwe.examples.viewcomponentdemo' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

# Live reload setup

This project uses NPM to have live reloading.

Use the following steps to get it working:

1. Run the Spring Boot application with the `local` profile
2. From a terminal, run `npm run build && npm run watch` (You can also run `npm run --silent build && npm run --silent watch` if you want less output in the terminal)
3. Your default browser will open at http://localhost:3000

You should now be able to change any HTML or CSS and have the browser reload upon saving the file.

NOTE: If you use a separate authentication server (e.g. social logins, or Keycloak) then after login,
you might get redirected to http://localhost:8080 as opposed to http://localhost:3000.
Be sure to set the port back to `3000` in your browser to have live reload.