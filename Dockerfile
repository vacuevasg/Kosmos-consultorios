FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ENV TZ=America/Mexico_City
ENV LANG=C.UTF-8
ENV JAVA_OPTIONS="-Dfile.encoding=UTF-8"

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo "$TZ" > /etc/timezone && \
    apk del tzdata

COPY target/consultorios-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "-Djdk.tls.client.protocols=TLSv1.2", "-Djava.awt.headless=true"]
