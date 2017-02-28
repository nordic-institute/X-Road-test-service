# Base tomcat:alpine
FROM tomcat:alpine

# Add Test Service under Tomcat's webapps folder
ADD src/target/test-service-*.war /usr/local/tomcat/webapps/
