#gcloud builds submit --tag eu.gcr.io/buc-personal-banking/payment .
FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/payment-0.1.0.jar
ADD ${JAR_FILE} payment.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/payment.jar"]