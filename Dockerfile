FROM maven:3.6.0-jdk-8-alpine
ADD . /app
WORKDIR /app
RUN mvn install:install-file -Dfile=dependencies/jpHash-1.0-SNAPSHOT.jar -DgroupId=com.pragone -DartifactId=jpHash -Dpackaging=jar -Dversion=1.0-SNAPSHOT
RUN mvn install:install-file -Dfile=dependencies/sa-jdi.jar -DgroupId=sun.jvm.hotspot -DartifactId=sa-jdi -Dpackaging=jar -Dversion=1.0
RUN mvn clean compile
CMD ["mvn", "test"]
