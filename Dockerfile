FROM openjdk:23
COPY ./target/Group4-DevOps-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Group4-DevOps-0.1.0.1-jar-with-dependencies.jar"]
