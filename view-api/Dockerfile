FROM maven:3.8.6-eclipse-temurin-11-alpine AS build


WORKDIR /comments-app

ADD ./commons ./commons
ADD ./pom.xml .
ADD ./mongodb-app/pom.xml ./mongodb-app/pom.xml
ADD ./write-api/pom.xml ./write-api/pom.xml
ADD ./view-api/pom.xml ./view-api/pom.xml
ADD ./view-api ./view-api
#RUN mvn clean install -Dmaven.test.skip=true -DexcludeArtifactIds=mongodb-app,write-api
RUN mvn clean install -N -Dmaven.test.skip=true
RUN mvn clean install -pl commons -Dmaven.test.skip=true


# COPY view-api/src /view-app/view-api/src
RUN mvn clean install -pl view-api -Dmaven.test.skip=true -e
#
# Package stage
#
FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY --from=build /root/.m2 /root/.m2
COPY --from=build /comments-app/view-api/target/view-api-1.0-SNAPSHOT.jar /usr/local/lib/view-api-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/view-api-1.0-SNAPSHOT.jar","--spring.profiles.active=docker"]