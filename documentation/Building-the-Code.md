### Building the code

Test Service uses Maven as the build management tool. In order to build the whole project and generate the jar file (test-service-x.x.x-SNAPSHOT.jar), you must run the maven command below from the ```src``` directory.

```
mvn clean install
```

Running the above maven command generates the war file under the directory presented below:

```
src/target/test-service-x.x.x-SNAPSHOT.jar
```
#### Error on building the code

If running ```mvn clean install``` generates the error presented below, there are two possible solutions.

```
[ERROR] Failed to execute goal on project test-service: Could not resolve dependencies for project com.pkrete.xrd4j.tools:test-service:war:0.0.1-SNAPSHOT: Failed to collect dependencies at com.pkrete.xrd4j:common:jar:0.0.6: Failed to read artifact descriptor for com.pkrete.xrd4j:common:jar:0.0.6: Could not transfer artifact com.pkrete.xrd4j:common:pom:0.0.6 from/to csc-repo (https://maven.csc.fi/repository/internal/): sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target -> [Help 1]
```

##### Solution 1

Skip certificate validation:

```
mvn install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
```

##### Solution 2

Import CSC's Maven repository's certificate as a trusted certificate into ```cacerts``` keystore. See full [instructions](https://github.com/petkivim/x-road-test-service/wiki/Import-a-Certificate-as-a-Trusted-Certificate). CSC's Maven repository's URL is ```https://maven.csc.fi```.
