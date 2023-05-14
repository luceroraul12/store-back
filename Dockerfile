# creo el .jar
FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean
RUN mvn -f /home/app/pom.xml package -DskipTests

# reutilizo el build para montarlo
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/scrapping-0.0.1-SNAPSHOT.jar /usr/local/lib/buscador-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/buscador-api.jar"]
