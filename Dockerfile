# Para Java Spring Boot, utilizo maven para generarme el package en java
FROM maven AS build
COPY src /home/app/src
# paso el archivo que contiene las dependencias del proyecto
COPY pom.xml /home/app
#limpio
RUN mvn -f /home/app/pom.xml clean
#realizo el paquete pero para este caso utilizo -DskipTests para que al momento de realizar el paquete
#no haga comprobacion de tests. Esto me es util por que un servicio externo tambien es una imagen propia que no quiero
#montar para hacer el paquete java
RUN mvn -f /home/app/pom.xml package -DskipTests

# reutilizo el build para montarlo
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/scrapping-0.0.1-SNAPSHOT.jar /usr/local/lib/buscador-api.jar
# expongo esta imagen por medio del puerto 8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/buscador-api.jar"]
