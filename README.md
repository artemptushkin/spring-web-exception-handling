### Spring Web / WebFlux exception handling demo repository

* [The corresponded medium article](https://art-ptushkin.medium.com/spring-web-and-webflux-exception-handling-best-practices-b2c3cd7e3acf)

This repository contains well-tested clean code examples of how to run your Spring expection hanlder as in Web and WebFlux stack

#### Reference links:

* https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
* https://www.baeldung.com/spring-webflux-filters
* https://docs.spring.io/spring-security/reference/reactive/configuration/webflux.html
* https://www.baeldung.com/spring-webflux-errors
* https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-exception-handler

### How to run tests?

Web:
```shell
./gradlew :test --tests "io.github.artemptushkin.demo.springwebexceptionhandling.ServletControllerTest"
```
or use [Intellij Idea run configuration](.run)

WebFlux
```shell
:test -PwebProfile=reactive --tests "io.github.artemptushkin.demo.springwebexceptionhandling.ReactiveControllerTest"
```
