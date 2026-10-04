FROM maven:3.9.6-eclipse-temurin-21
RUN apt-get update && apt-get install -y --no-install-recommends libgtk-3-0 libxtst6 libxxf86vm1 libgl1 libx11-6 fonts-dejavu && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY pom.xml .
COPY . /app
RUN mvn package
ENV DISPLAY=host.docker.internal:0.0
ENV DB_URL=jdbc:h2:/app/data/tempdb
VOLUME /app/data
CMD ["java", "-Dprism.order=sw", "-jar", "target/OTP1_inclass1_assignment_TK-1.0-SNAPSHOT.jar"]