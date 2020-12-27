FROM adoptopenjdk:14-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/image-upload-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8082

CMD ["java","-jar","image-upload-api-1.0.0-SNAPSHOT.jar"]

