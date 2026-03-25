FROM openjdk:21-ea

WORKDIR /usrapp/bin

ENV PORT=8083

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","com.example.proxyservice.ProxyServiceApplication"]

