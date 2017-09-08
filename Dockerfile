FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD admin.jar app.jar
RUN sh -c 'touch /app.jar'
ENV SERVER_BASEPATH http://35.158.247.216:9002
ENV JAVA_OPTS="-Dmailgun.adapter.send.url=http://ec2-35-156-185-71.eu-central-1.compute.amazonaws.com:9005/send -Dspring.profiles.active=prd -Dserver.frontend.basepath=${SERVER_BASEPATH} \
-Dserver.redirecturl.basepath=${SERVER_BASEPATH} -Dserver.appsystem.basepath=http://35.158.247.216:9001 -Djwt.secret=1#233z366s_>2e-!2ef? -Dserver.port=9002 -Xms500m -Xmx1g -Ddbname=sdp -Ddbusername=applications -Ddbpassword=4%vq<-{(IRt@[27:ejEmd.oT -Ddbhost=codecool-central.cafzixbagnyd.eu-central-1.rds.amazonaws.com -Ddbport=5432"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
EXPOSE 9002