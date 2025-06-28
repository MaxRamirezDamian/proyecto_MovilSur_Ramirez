# Imagen base con Java
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el JAR de tu aplicación al contenedor
COPY target/*.jar app.jar

# Copiar el wallet de tu aplicación al contenedor
COPY src/main/resources/Wallet_SistemaReserva /app/Wallet_SistemaReserva


# Exponer el puerto en el que se ejecuta tu aplicación Spring Boot
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]