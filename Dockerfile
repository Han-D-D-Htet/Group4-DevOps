FROM openjdk:22
COPY ./target/devopsethods.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devopsethods.jar", "db:3306", "30000"]