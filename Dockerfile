FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o wrapper e dá permissão
COPY mvnw .
RUN chmod +x ./mvnw

# Copia arquivos e pastas de configuração do Maven
COPY .mvn/ .mvn
COPY pom.xml .

# Baixa dependências sem rodar testes
RUN ./mvnw dependency:go-offline

# Copia o restante do código
COPY src ./src

# Constrói a aplicação sem rodar testes
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/gestao-0.0.1-SNAPSHOT.jar"]