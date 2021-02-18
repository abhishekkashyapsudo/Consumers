FROM java:8-jdk
RUN mkdir /app
WORKDIR /app
COPY target/Consumers-0.0.1-SNAPSHOT.jar /app
EXPOSE 8110
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "Consumers-0.0.1-SNAPSHOT.jar"]