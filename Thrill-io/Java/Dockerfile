# Use Tomcat 9 with JDK 11 as base image
FROM tomcat:9-jdk11-openjdk

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the entire WebContent directory
COPY WebContent/ /usr/local/tomcat/webapps/ROOT/

# Copy compiled classes
COPY build/WEB-INF/classes/ /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Copy MySQL JDBC driver and other libraries
COPY WebContent/WEB-INF/lib/ /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/

# Add Tomcat users for management access
RUN echo '<?xml version="1.0" encoding="UTF-8"?>\n\
<tomcat-users xmlns="http://tomcat.apache.org/xml"\n\
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n\
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"\n\
              version="1.0">\n\
  <role rolename="manager-gui"/>\n\
  <role rolename="manager-script"/>\n\
  <user username="admin" password="admin" roles="manager-gui,manager-script"/>\n\
</tomcat-users>' > /usr/local/tomcat/conf/tomcat-users.xml

# Set environment variables
ENV CATALINA_OPTS="-Dfile.encoding=UTF-8"

# Expose port 8080
EXPOSE 8080