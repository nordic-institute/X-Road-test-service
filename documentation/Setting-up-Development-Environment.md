## Setting up Development Environment

This document describes how a developer's workstation can be setup.

### Software Requirements

* Linux / Windows / MacOS
* Java 8 or Java 11
* Maven 3.x
* Docker (*optional*)

### Getting the code

There are several of ways to get code, e.g. download it as a [zip](https://github.com/nordic-institute/x-road-test-service/archive/master.zip) file or clone the git repository.

```
git clone https://github.com/nordic-institute/x-road-test-service.git
```

The code is located in the ```src``` folder.

### Building the code

X-Road Test Service uses Maven as the build management tool. In order to build the whole project and generate the war  file (`test-service-x.x.x-SNAPSHOT.war`), you must run the maven command below from the ```src``` directory.

```
mvn clean install
```

Running the above maven command generates the war file under the directory presented below:

```
src/target/test-service-x.x.x-SNAPSHOT.war
```
