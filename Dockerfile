FROM openjdk:8-jdk

RUN mkdir /app

ADD target/target/datoriistat-webapp-1.0-SNAPSHOT.jar /app/datoriistat.jar

CMD [ "java", "-jar", "/app/datoriistat.jar" ]
