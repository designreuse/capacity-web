# Capacity planning tools
The Capacity planning tools provide a simple web UI to for organizing the work
for the employees in a large software development project or company.

## Supported and tested servlet containers

Capacity web needs Java 8 and any servlet 3.0 compatible servlet container. The
following containers were tested and work

* Tomcat 8: This container is used for development and likely to be supported
  best.

* Tomcat 7: The *capacity planning tools* don't require any Tomcat 8 specific
  features. It is testes from time to time.

### Tomcat 7 or 8

To run the *capacity planning tools* on Tomcat 7 or 8 you need to create a
context.xml for it. During development you can create the file as
src/main/webapp/META-INF/context.xml, when in production it might be something
like /etc/tomcat7/Catalina/localhost/capacity.xml. See the Tomcat documentation
for further details. An example context.xml looks like this:

    <Context docBase="/var/lib/tomcat/capacity.war" path="/capacity">
        <Resource
                name="jdbc/capacityDS"
                auth="Container"
                type="javax.sql.DataSource"
                maxActive="10"
                maxIdle="5"
                maxWait="10000"
                username="sa"
                password=""
                driverClassName="org.hsqldb.jdbc.JDBCDriver"
                url="jdbc:hsqldb:mem:testdb" />
    </Context>

## Supported Databases

The project is using hibernate for data access. This means that theoretically
every database supported by hibernate is usable. You have to keep in mind that
we are using flyway to initialize and migrate the databases. The flyway scripts
are only available and tested on on the following databases right now:

* MySQL 5.5+
* MariaDB 5.5+
* hsqldb 2.3+
* PostgreSQL 9.4+

## Build status
[![travis-ci.org](https://travis-ci.org/egore/capacity-web.svg "Build status")](https://travis-ci.org/egore/capacity-web)
[![codecov](https://codecov.io/gh/egore/capacity-web/branch/master/graph/badge.svg)](https://codecov.io/gh/egore/capacity-web)

