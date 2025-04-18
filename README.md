# Thrill-io Bookmarking Application

A Java web application for managing and sharing bookmarks, including books and web links.

## Features

- User authentication and authorization
- Bookmark management (Books and Web Links)
- Kid-friendly content filtering
- Social sharing capabilities
- Background jobs for web page downloading

## Technologies Used

- Java
- JSP (JavaServer Pages)
- MySQL Database
- Apache Tomcat
- JSTL (JavaServer Pages Standard Tag Library)
- Maven for dependency management

## Setup Instructions

1. Install Prerequisites:

   - Java JDK 11 or higher
   - MySQL Server
   - Apache Tomcat

2. Database Setup:

   - Run the `init.sql` script to create the database schema

3. Application Deployment:
   - Deploy the WAR file to Tomcat's webapps directory
   - Access the application at: `http://localhost:8080/thrillio`

## Project Structure

- `Java/src/` - Java source files
- `WebContent/` - Web resources (JSP, CSS, JavaScript)
- `init.sql` - Database initialization script
