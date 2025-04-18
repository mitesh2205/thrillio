# Thrill-io

Thrill-io is a Java bookmarking app that I developed as part of the Udemy course: **Java In Depth: Become A Complete Java Engineer**.

My app is now on **Heroku** and can be accessed via the following link: [**Thrill.io**](https://thrill-io.herokuapp.com/bookmark)


**Technologies Used**

* Java
* JSP
* MySQL
* Apache Tomcat
* [**Jsoup**](https://jsoup.org/), a Java-based web-scraping library

### Project Summary
During the course, I implemented features such as the *Browse* and *My Books* pages, where the *Browse* page shows the list of all books that a user can bookmark, while the *My Books* page displays all the books that a user has bookmarked.

I also developed a login page in addition to the *Browse* and *My Books* pages.

### Post-Course
Since completing this course, I have extended this project for fun. Some *new features* that I added to the project since the end of the course include:

* Adding a sign-up page

* Enabling users to add books to the overall marketplace
  * i.e. The list of books, which all users can see
  * Given a book URL from [**Goodreads.com**](https://www.goodreads.com/)
  * I have utilized the Jsoup library (not covered in the course)
  
* Adding login & signup field validation
   * Client-Side: Based on the specific HTML **pattern** attribute value
   * Server-Side: Whether a user exists
     * By verifying their email with the database
  
