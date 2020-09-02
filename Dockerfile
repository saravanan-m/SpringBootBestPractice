From openjdk:8
copy ./build/libs/Loyalty-0.0.1-SNAPSHOT.jar Loyalty-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Loyalty-0.0.1-SNAPSHOT.jar"]