# Base openjdk:11
FROM openjdk:11

# Add X-Road Test Service war to container
ADD src/target/test-service-*.war test-service.war

# Entry with exec
ENTRYPOINT exec java $JAVA_OPTS -jar /test-service.war

# Expose default port
EXPOSE 8080