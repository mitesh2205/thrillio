<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" type="text/css" href="css/thrillio.css" />
		<script src="${pageContext.request.contextPath}/js/thrillio.js"></script>
		<title>thrill.io</title>
	</head>

	<body style="font-family:Arial;font-size:20px;">
		<div style="height:65px;align:center;background: #2787db;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style=" font-family:garamond;font-size:34px;margin:0px 0px 0px 10px;color:white;text-decoration: none;">
			thrill.io</a></b>

			<div style="height:25px;background: #2787db;font-family: Arial;color: white;">
				<b>
					<!--<a style="font-size:16px;color:white;margin-left:100px;text-decoration:none;">You are logged in as: ${firstName}, ${lastName}</a>-->
					<a href="<%=request.getContextPath()%>/bookmark"
						style="font-size:16px;color:white;margin-left:1200px;text-decoration:none;">Browse</a>
					<a href="<%=request.getContextPath()%>/bookmark/mybooks"
						style="font-size:16px;color:white;margin-right: -900px;text-decoration:none;">My Books</a>
					<a href="<%=request.getContextPath()%>/auth/logout"
						style="font-size:16px;color:white;margin-left:950px;text-decoration:none;">Logout</a>
				</b>
			</div>
		</div>
		<br><br>
		<p>You are logged in as: ${user.firstName} ${user.lastName}</p>

		<h2>Books</h2>

		<table>
			<c:forEach var="book" items="${books}">
				<tr>
					<td>
						<a href="${book.bookUrl}"><img src="${book.imageUrl}" width="175" height="250"></a>
					</td>

					<td style="color:gray;">
						<%--Title: <span style="color: #B13100;">${book.title}</span>
							<br><br>--%>
							Title: <span style="color: #B13100;">${book.title}</span>
							<br><br>
							By: <span style="color: #B13100;">${book.authors[0]}</span>
							<br><br>
							Average Rating: <span style="color: #B13100;">${book.goodreadsRating} stars out of 5</span>
							<br><br>
							Publication Year: <span style="color: #B13100;">${book.publicationYear}</span>
							<br><br>
							<!--Genre: <span style="color: #B13100;">${book.genre}</span>
			 <br><br>-->
							<!--Book URL: <span style="color: #B13100;">${book.bookUrl}</span>
			 <br><br>
			 URL: <span style="color: #B13100;"><a href="<c:url value = "${book.bookUrl}"/>">Goodreads</a></span>
			 <br><br>-->
							<a href="<%=request.getContextPath()%>/bookmark/save?bid=${book.id}"
								style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Save</a>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>

			</c:forEach>

		</table>


		<form method="POST" action="<%=request.getContextPath()%>/bookmark/addbook">
			<table>

				<!--<tr>
	    		<td><label>Image URL:</label></td>
        		<td>
        			<input type="text" name="image_url"><br>        			
        		</td>
        	</tr>-->
				<tr>
					<td><label>Book URL:</label></td>
					<td>
						<input type="text" name="book_url"><br>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><a href="<%=request.getContextPath()%>/bookmark"><input type="submit" name="submitNewBook"
								value="Add Book"></a></td>
				</tr>
			</table>
		</form>

	</body>

	</html>