#FROM maven:3-jdk-8-alpine
#ADD ./ /friend-management
#RUN cd /friend-management && mvn clean package
#RUN cp /friend-management/target/friend-management-1.0.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENV JAVA_OPTS="-Xmx1024m"
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar

FROM openjdk:8-jdk-alpine
ADD target/friend-management-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar