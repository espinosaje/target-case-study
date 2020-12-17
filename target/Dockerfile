FROM openjdk:8
Expose 8080
# locally it needs to have the ADD, to run on Jenkins it shouldn't have the ADD
ADD target/target-case-study.jar target-case-study.jar
ENTRYPOINT ["java","-jar","target-case-study.jar"]