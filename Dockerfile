FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD run.sh ./run.sh
ADD admin.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "./run.sh" ]
EXPOSE 8080