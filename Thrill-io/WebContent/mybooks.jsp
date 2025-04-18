<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/css/thrillio.css"/>
<title>thrill.io</title>
</head>
<body style="font-family:Arial;font-size:20px;">
	<div style="height:65px;align: center;background: #2787db;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
		<div style="height:25px;background: #2787db;font-family: Arial;color: white;">
			<b>
			<span class = "universal_links">
			    <a href="/bookmark" class = "universal_link">Browse</a>
			    <a href="/bookmark/mybooks" class = "universal_link">My-Books</a>
			    <a href="/auth/logout" class = "universal_link">Logout</a>
		    </span>
			</b>
		</div> 
	</div>
	<br><br>
	
	<p>You are logged in as: ${user.firstName} ${user.lastName}</p>
	
	<div style="font-size: 24px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">My Books</div>
	<br><br>
	    
    <c:choose>
		<c:when test="${!empty(books)}">
			<table>
			   <c:forEach var = "book" items="${books}">
			     <tr>
				   <td>
				     <a href = "${book.bookUrl}"><img src="${book.imageUrl}" width="175" height="250"></a>
				   </td>
					    
				   <td style="color:gray;">
				   	 Title: <span class = "author_info">${book.title}</span>
			 		 <br><br>
					 By: <span class = "author_info">${book.authors[0]}</span>
					 <br><br>
					 Average Rating: <span class = "author_info">${book.goodreadsRating} stars out of 5</span>
					 <br><br>
					 Publication Year: <span class = "author_info">${book.publicationYear}</span>
					 <br><br>
					 <a href = "/bookmark/remove?bid=${book.id}" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Remove</a>
					</td>
				  </tr>
				  <tr>
		     	    <td>&nbsp;</td>
		  		  </tr>
		  		 
			   </c:forEach>
			   
			</table>
     	</c:when>
     <c:otherwise>
		<br><br>
       	<span style="font-size: 24px;color: #333333;margin:400px;">You do not have any saved items!</span>
     </c:otherwise>
    </c:choose>
</body>
</html>