#Utilizar la imagen oficial de OpenJDK 21

FROM openjdk:21-jdk-slim
#Establecer el directorio de trabajo

WORKDIR /app
#Copiar el archivo JAR de la aplicación al contenedor
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml ./
COPY src ./src

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
#Exponer el puerto en el que la aplicación se ejecuta

EXPOSE 8081
#Comando para ejecutar la aplicación

ENTRYPOINT ["java", "-jar", "app.jar"]