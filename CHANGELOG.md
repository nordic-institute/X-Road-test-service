# Changelog

## 0.0.4-SNAPSHOT - 2023-02-25
- Change into a Spring Boot application.
  - Installation package no longer installs a standalone Tomcat server
  - Instead, the Test Service is run as a self-contained Java application, with an embedded web container
- Change the context path from `/${project.build.finalName}` to `/test-service`
- Update `Dockerfile` and change base image from `tomcat:alpine` to `openjdk:11`
- Add Java 11 support
- Update dependencies

## 0.0.4-SNAPSHOT - 2022-01-06
- Update XRd4J version to 0.4.0
- Update dependencies

## 0.0.4-SNAPSHOT - 2018-09-09
- Update XRd4J dependency from `com.pkrete.xrd4j` to `org.niis.xrd4j`
- Update XRd4J version to 0.3.0
  - `<request>/<response>` wrappers are no longer used by default
- Remove `<request>/<response>` wrappers from request / response examples (`examples/`)
- Add OWASP dependency check Maven plugin
- Add Checkstyle Maven plugin and configuration
- Add `CHANGELOG.md`