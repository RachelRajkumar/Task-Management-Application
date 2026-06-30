FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x backend/mvnw
RUN cd backend && ./mvnw clean package -DskipTests

WORKDIR /app/backend

EXPOSE 8080

CMD ["sh","-c","java -jar target/*.jar"]
