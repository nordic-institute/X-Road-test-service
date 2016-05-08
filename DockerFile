# Base tomcat:7.0
FROM tomcat:7.0

# Add Test Service under Tomcat's webapps folder
ADD src/target/test-service-*.war /usr/local/tomcat/webapps/
