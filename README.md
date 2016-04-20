# X-Road Test Service

X-Road Test Service is a testing tool for X-Road v6.4 and above. The implementation is based on [XRd4J](https://github.com/petkivim/xrd4j) library. 

Test Service application provides a single service that takes as parameters the size of the response body and the size of the response attachment part. The size defines the number of characters in the response. For example, the below request generates a response which SOAP body contains 25 characters (see full [request](https://github.com/petkivim/x-road-test-service/blob/master/examples/request-1.xml)).

```
<request>
  <responseBodySize>25</responseBodySize>
  <responseAttachmentSize>0</responseAttachmentSize>
</request>
```

In addition to the characters specified in the request, the response alsp contains the time that was used for generating the requested string  (see full [response](https://github.com/petkivim/x-road-test-service/blob/master/examples/response-1.xml)).

```
<response>
  <data>ibWHohGndjaJUJvyOBwmfqIZb</data>
  <processingTime>1</processingTime>
</response>
```

### Try It Out

The fastest and easiest way to try out the application is to [download](https://github.com/petkivim/x-road-test-service/releases/download/v0.0.2/test-service-0.0.2.jar) the executable jar version (```test-service-0.0.2.jar```) and run it: ```java -jar test-service-0.0.2.jar```. The application is accessible at:

```
http://localhost:8080/test-service-0.0.2/Endpoint
```

The WSDL description is accessible at:

```
http://localhost:8080/test-service-0.0.2/Endpoint?wsdl
```

### Software Requirements

* Java 6 or later
* Tomcat 6 or later

### Development Environment

Setting up development environment is explained in [wiki](https://github.com/petkivim/x-road-test-service/wiki/Setting-up-Development-Environment).

### Installation

See [instructions](https://github.com/petkivim/x-road-test-service/wiki/Building-the-Code) for building the code.

#### JAR

* Build the project and produce ```test-service-0.0.3-SNAPSHOT.jar``` file.
* Run the application: ```$ java -jar test-service-0.0.3-SNAPSHOT.jar```.

#### WAR

* Build the project and produce ```test-service-0.0.3-SNAPSHOT.war``` file.
* Copy the file ```tomcat.home/webapps``` folder.
* Start/restart Tomcat.

### Access the application

After installation (both JAR and WAR) the application is accessible at:

```
http://localhost:8080/test-service-0.0.3-SNAPSHOT/Endpoint
```

The WSDL description is accessible at:

```
http://localhost:8080/test-service-0.0.3-SNAPSHOT/Endpoint?wsdl
```

**N.B.** If you want to connect the services to X-Road Security Server you must update your server's IP address / host name to the WSDL file. Look for the below line and replace the default URL with your server's IP / host name.

```
<soap:address location="http://localhost:8080/test-service-0.0.3-SNAPSHOT/Endpoint" />
```

### Usage

Calling the service after installation when Tomcat is running.

#### testService

SOAP [request](https://github.com/petkivim/x-road-test-service/blob/master/examples/request-1.xml) can be found in the ```examples``` folder.

```
curl -d @request-1.xml --header "Content-Type: text/xml" -X POST http://localhost:8080/test-service-0.0.3-SNAPSHOT/Endpoint
```

Example SOAP [response](https://github.com/petkivim/x-road-test-service/blob/master/examples/response-1.xml) can be found in the ```examples``` folder.
