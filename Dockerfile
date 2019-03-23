FROM openjdk:8-jdk-alpine

MAINTAINER Vashistha Kumar<vasihstha.kumar@gmail.com>

COPY target/cool-emoji-api-1.0.0-SNAPSHOT.jar .
COPY target/properties/heroku.yml .

CMD java -jar cool-emoji-api-1.0.0-SNAPSHOT.jar server heroku.yml