FROM maven:3.9.4-eclipse-temurin-21 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar o arquivo JAR da camada de construção
COPY --from=builder /app/target/*.jar /app/auth-transaction.jar

# Expor a porta que a aplicação vai rodar
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "/app/auth-transaction.jar"]