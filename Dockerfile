
FROM openjdk:17

WORKDIR /app

COPY . .

EXPOSE 5555

CMD ["java", "app/Chatfinal.java"]