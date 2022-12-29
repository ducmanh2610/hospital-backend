FROM openjdk:11
EXPOSE 8080
ADD target/hospital-backend-docker.jar hospital-backend-docker.jar
ENTRYPOINT ["java", "-jar", "hospital-backend-docker.jar"]