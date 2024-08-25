FROM liquibase/liquibase:4.15.0 as liquibase
FROM tomcat:10.1.28-jdk17-temurin-jammy
COPY --from=liquibase /liquibase /opt/liquibase
RUN ln -s /opt/liquibase/liquibase /usr/local/bin/liquibase
ADD src/main/resources/db/changelog/ liquibase/changelog
COPY src/main/resources/database.properties /usr/local/tomcat/conf/database.properties
COPY src/main/resources/db /usr/local/tomcat/webapps/db
ADD build/libs/attraction_library.war /usr/local/tomcat/webapps/


