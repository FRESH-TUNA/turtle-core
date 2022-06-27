FROM openjdk:17

ARG JAR_FILE=./build/libs/core-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /remember/app.jar

CMD java -Dspring.profiles.active=prod -Dserver.port=$PORT $JAVA_OPTS -jar /remember/app.jar
