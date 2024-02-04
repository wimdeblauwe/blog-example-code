# TamingThymeleaf

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.properties` file to override settings for development.

In addition to the Spring Boot application, the DevServer must also be started. [Node.js](https://nodejs.org/) has to be
available on the system - the latest LTS version is recommended. On first usage and after updates the dependencies have to be installed:

```
npm install
```

The DevServer can now be started as follows:

```
npm run devserver
```

Using a proxy the whole application is now accessible under `localhost:8081`. All changes to the templates and JS/CSS
files are immediately visible in the browser.

## Testing requirements

Testcontainers is used for running the integration tests. Due
to the reuse flag, the container will not shut down after the tests. It can be stopped manually if needed.

## Build

The application can be tested and built using the following command:

```
mvnw clean package
```

Node.js is automatically downloaded using the `frontend-maven-plugin` and the final JS/CSS files are integrated into the jar.

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/taming-thymeleaf-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=io.bootify/taming-thymeleaf
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
* [Thymeleaf docs](https://www.thymeleaf.org/documentation.html)  
* [Webpack concepts](https://webpack.js.org/concepts/)  
* [npm docs](https://docs.npmjs.com/)  
* [Tailwind CSS](https://tailwindcss.com/)  
* [Htmx in a nutshell](https://htmx.org/docs/)  
* [Learn Spring Boot with Thymeleaf](https://www.wimdeblauwe.com/books/taming-thymeleaf/)  
