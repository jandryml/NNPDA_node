FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=build/libs/NNPDA_node-0.0.1-SNAPSHOT.jar
ARG CONF_FILE=local.yml

# cp spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar
COPY ${CONF_FILE} local.yml


# java -jar /opt/app/app.jar --spring.config.location=file:local.yml
#ENTRYPOINT ["java","-jar","app.jar", "--spring.config.location=file:local.yml"]