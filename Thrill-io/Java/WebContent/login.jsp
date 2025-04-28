<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/thrillio.css" />
			<script src="${pageContext.request.contextPath}/js/thrillio.js"></script>
			<title>ShelfIt - Login</title>
		</head>

		<body>
			<div class="login-container">
				<div class="image-section">
					<div class="image-content">
						<h1>Hello World.</h1>
						<p>Organize your reading collection in one beautiful place</p>
					</div>
				</div>
				<div class="form-section">
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

					<div class="register-header">
						<h2>Log In</h2>
					</div>

					<form id="login-form" name="loginForm" method="POST"
						action="${pageContext.request.contextPath}/auth">
						<c:if test="${not empty login_error}">
							<div class="error-message">${login_error}</div>
						</c:if>

						<div class="input-group">
							<label for="email">EMAIL ADDRESS <span class="required-field">*</span></label>
							<input type="email" name="email" id="email" value="" pattern=".+\.[A-Za-z]{2,}($|\n)"
								required>
							<span id="login-email-error" class="emailformat_error"></span>
						</div>

						<div class="input-group">
							<label for="password">PASSWORD <span class="required-field">*</span></label>
							<input type="password" name="password" id="password" value="" required>
						</div>

						<button type="submit" name="submitLoginForm" class="login-btn">LOGIN</button>
					</form>

					<div class="signup-section">
						<p>Don't have an account?</p>
						<form method="POST" action="${pageContext.request.contextPath}/signup.jsp">
							<button type="submit" name="submitRegistrationForm" value="Sign Up"
								id="signUpButton_LoginPage" class="signup-btn">SIGN UP</button>
						</form>
					</div>
				</div>
			</div>
		</body>

		</html>