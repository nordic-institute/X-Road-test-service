# X-Road Test Service

[![Go to X-Road Community Slack](https://img.shields.io/badge/Go%20to%20Community%20Slack-grey.svg)](https://jointxroad.slack.com/)
[![Get invited](https://img.shields.io/badge/No%20Slack-Get%20invited-green.svg)](https://x-road.global/community)

X-Road Test Service is a testing tool for X-Road 6 and X-Road 7. The implementation is based on [XRd4J](https://github.com/nordic-institute/xrd4j) library.

Test Service application provides a single service that takes as parameters the size of the response body and the size of the response attachment part. The size defines the number of characters in the response. For example, the below request generates a response which SOAP body contains 25 characters (see full [request](https://github.com/nordic-institute/x-road-test-service/blob/master/examples/request-1.xml)).

```
<request>
  <responseBodySize>25</responseBodySize>
  <responseAttachmentSize>0</responseAttachmentSize>
</request>
```

In addition to the characters specified in the request, the response contains the time that was used for generating the requested string  (see full [response](https://github.com/nordic-institute/x-road-test-service/blob/master/examples/response-1.xml)).

```
<response>
  <data>ibWHohGndjaJUJvyOBwmfqIZb</data>
  <processingTime>1</processingTime>
</response>
```

**N.B.** Starting from version 0.0.4-SNAPSHOT `<request>/<response>` wrappers are no longer used. When using versions < 0.0.4-SNAPSHOT, `<request>/<response>` must be added manually to the example requests in `examples/` folder.

## Try It Out

The fastest and easiest way to try out the application is by using the Spring Boot Maven plugin.
To do this, you need to have a working installation of [Maven](https://maven.apache.org/).

```
cd src
mvn spring-boot:run
```
After that the application is accessible at:

```
http://localhost:8080/test-service/Endpoint
```

The WSDL description is accessible at:

```
http://localhost:8080/test-service/Endpoint?wsdl
```

## Software Requirements

* Java 8 or Java 11
* Maven 3.x

## Development Environment

Setting up development environment is explained in [documentation](documentation/Setting-up-Development-Environment.md).

## Installation

X-Road Test Service can be installed and run in the following ways:

* Deploying `test-service-x.x.x.war` into a web container such as Tomcat.
* Using Docker to run X-Road Test Service.

### Web container

Build the X-Road Test Service and deploy it to a Java application server, e.g., Tomcat.

* Build the project and produce ```test-service-x.x.x.war``` file.
* Copy the file ```tomcat.home/webapps``` folder.
* Start/restart Tomcat.

### Docker

You can create a Docker image to run X-Road Test Service inside a container, using the provided Dockerfile.
Before building the image, build the war file inside `src` directory

```
mvn clean install
```
If you have not built the war, building the Docker image will fail with message
```
Step 2 : ADD src/target/test-service-*.war test-service.war
No source files were specified
```

While you are in the project root directory, build the image using the `docker build` command. The `-t` parameter gives your image a tag, so you can run it more easily later. Don’t forget the `.` command, which tells the `docker build` command to look in the current directory for a file called Dockerfile.

```
docker build -t x-road-test-service .
```

After building the image, you can run X-Road Test Service using it.

```
docker run -p 8080:8080 x-road-test-service
```

## Access the application

After installation the application is accessible at:

```
http://localhost:8080/test-service/Endpoint
```

The WSDL description is accessible at:

```
http://localhost:8080/test-service/Endpoint?wsdl
```

## Usage

Calling the service after installation when the service is running.

### testService

SOAP [request](https://github.com/nordic-institute/x-road-test-service/blob/master/examples/request-1.xml) can be found in the `examples` folder.

```
curl -d @request-1.xml --header "Content-Type: text/xml" -X POST http://localhost:8080/test-service/Endpoint
```

Example SOAP [response](https://github.com/nordic-institute/x-road-test-service/blob/master/examples/response-1.xml) can be found in the `examples` folder.

## Credits

* X-Road Test Service library was originally developed by Petteri Kivimäki (https://github.com/petkivim) during 2015-2018.
* In August 2018 it was agreed that Nordic Institute for Interoperability Solutions (NIIS) takes maintenance responsibility.
