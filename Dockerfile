FROM openjdk:8-alpine

COPY target/uberjar/user-auth.jar /user-auth/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/user-auth/app.jar"]
