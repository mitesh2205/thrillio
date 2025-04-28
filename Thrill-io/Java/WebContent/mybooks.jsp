<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/thrillio.css" />
		<script src="${pageContext.request.contextPath}/js/thrillio.js"></script>
		<title>ShelfIt - My Books</title>
	</head>

	<body>
		<div class="app-container">
			<header>
				<div class="header-content">
					<div class="logo">
						<svg viewBox="0 0 400 200" xmlns="http://www.w3.org/2000/svg" width="120" height="60">
							<circle cx="200" cy="95" r="75" fill="#f0e8dd" />
							<rect x="125" y="110" width="150" height="4" fill="#3d3d3d" rx="1" ry="1" />
							<rect x="125" y="114" width="150" height="2" fill="#5a5a5a" rx="1" ry="1" />
							<path d="M125 110 L125 95 A5,5 0 0 1 135,95 L135 110" fill="#5a5a5a" />
							<path d="M275 110 L275 95 A5,5 0 0 0 265,95 L265 110" fill="#5a5a5a" />
							<rect x="140" y="70" width="12" height="40" fill="#c2e7d9" rx="1" ry="1" />
							<rect x="155" y="75" width="14" height="35" fill="#f6b3c5" rx="1" ry="1" />
							<rect x="172" y="80" width="10" height="30" fill="#f0cc8b" rx="1" ry="1" />
							<rect x="185" y="65" width="15" height="45" fill="#a9c7e8" rx="1" ry="1" />
							<rect x="203" y="75" width="11" height="35" fill="#d9bbde" rx="1" ry="1" />
							<rect x="217" y="82" width="13" height="28" fill="#b8e0d2" rx="1" ry="1" />
							<rect x="233" y="70" width="9" height="40" fill="#f9d2b0" rx="1" ry="1" />
							<rect x="245" y="78" width="14" height="32" fill="#c3e2dd" rx="1" ry="1" />
							<path d="M190 45 L210 45 L210 65 L200 60 L190 65 Z" fill="#e6cbb3" stroke="#d9bda3"
								stroke-width="1" />
							<text x="200" y="155" font-family="Helvetica, Arial, sans-serif" font-size="30"
								font-weight="500" text-anchor="middle" fill="#3d3d3d">ShelfIt</text>
						</svg>
					</div>
					<nav class="main-nav">
						<a href="${pageContext.request.contextPath}/bookmark" class="nav-link">Browse</a>
						<a href="${pageContext.request.contextPath}/bookmark/mybooks" class="nav-link active">My
							Books</a>
						<a href="${pageContext.request.contextPath}/auth/logout" class="nav-link">Logout</a>
					</nav>
				</div>
			</header>

			<main class="main-content">
				<div class="user-info">
					You are logged in as: <strong>${user.firstName} ${user.lastName}</strong>
				</div>

				<div class="section-header">
					<h1>My Books</h1>
				</div>

				<div class="books-container">
					<c:choose>
						<c:when test="${!empty(books)}">
							<div class="book-list">
								<c:forEach var="book" items="${books}">
									<div class="book-list-item">
										<div class="book-cover">
											<a href="${book.bookUrl}">
												<img src="${book.imageUrl}" alt="${book.title}">
											</a>
										</div>
										<div class="book-details">
											<div class="book-title">Title: ${book.title}</div>
											<div class="book-author">By ${book.authors[0]}</div>
											<div class="book-rating" data-rating="${book.goodreadsRating}">
												<span class="rating-stars">
													<c:forEach begin="1" end="5" var="i">
														<span
															class="${i <= book.goodreadsRating ? 'star-filled' : 'star-empty'}">★</span>
													</c:forEach>
												</span>
												<span class="rating-text">${book.goodreadsRating} out of 5</span>
											</div>
											<div class="book-year">Publication Year: ${book.publicationYear}</div>
											<div class="book-actions">
												<a href="${pageContext.request.contextPath}/bookmark/remove?bid=${book.id}"
													class="btn btn-secondary">Remove</a>
												<a href="${book.bookUrl}" class="btn btn-primary">View on Goodreads</a>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:when>
						<c:otherwise>
							<div class="empty-state">
								<div class="empty-state-icon">📚</div>
								<h2>Your Collection is Empty</h2>
								<p>Start adding books to build your personal library!</p>
								<a href="${pageContext.request.contextPath}/bookmark" class="btn btn-primary">Browse
									Books</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</main>
		</div>
	</body>

	</html>