## Cute LS 🦄

this is a minimal `ls` implementation that showcases Spring Shell with GraalVM Native Image.

## Build and run

```shell
mvn -Pnative native:compile -DskipTests
./target/native-spring-shell
>ls
```