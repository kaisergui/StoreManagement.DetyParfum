# Usa imagem base com Java 17
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixa as dependências do projeto sem executar os testes
RUN ./mvnw dependency:go-offline

# Copia o restante do código fonte
COPY src ./src

# Compila o projeto (sem testes para acelerar)
RUN ./mvnw clean package -DskipTests

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/gestao-0.0.1-SNAPSHOT.jar"]