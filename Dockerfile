# Use a imagem base do OpenJDK para Java 17
FROM openjdk:17-alpine

# Copie o arquivo JAR da sua aplicação para dentro do contêiner
COPY target/pedidos-0.0.1-SNAPSHOT.jar /app/pedidos-0.0.1-SNAPSHOT.jar

# Defina o diretório de trabalho como /app
WORKDIR /app

# Exponha a porta 8080 (ou a porta que sua aplicação Spring Boot está usando)
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "pedidos-0.0.1-SNAPSHOT.jar"]