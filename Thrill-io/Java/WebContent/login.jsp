<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/ThrillioWeb_Heroku/css/thrillio.css"/>
<script src="/ThrillioWeb_Heroku/js/thrillio.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<title>Login</title>
</head>

<body>
	<div style="height:65px;align: center;background: #2787db;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
	</div>
	<br><br>
	
	
	<form name = "loginForm" method="POST" action="<%=request.getContextPath()%>/auth">
		
		<c:if test="${not empty login_error}">
	    	<div id="login_error">${login_error}</div>
	    	<br>
	    </c:if>
	    
      <fieldset>
	    <legend>Log In</legend>	    
	    <table>
	    	<tr>
	    		<td><label>Email:</label></td>
        		<td>
        			
        			<input type="email" name="email" id = "email" value="" pattern = ".+\.[A-Za-z]{2,}($|\n)" oninput="printLoginEmailErrorOnWebpage()" required><span class="emailformat_error"></span> <br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Password:</label></td>
        		<td>
        			<input type="password" name="password" id = "password" value="" required><br>
        		</td>        
        	</tr>
        	<tr>
        		<td>&nbsp;</td>
        		<td><a href="<%=request.getContextPath()%>/auth"><input type="submit" name="submitLoginForm" value="Log In" onclick="validateLoginForm()"></a></td>
        	</tr>
        	
        	
        	
        	<!--
        	<tr>
	        	<td><label>Don't have an account?</label></td>
        	</tr>
        	
        	<tr>
        		<td><a href="<%=request.getContextPath()%>/auth/signup"><input type="submit" name="submitRegistrationForm" value="Sign Up" onclick = "<%=request.getContextPath()%>/auth/signup"></a></td>
        	</tr>-->
        	
        </table>
	  </fieldset>      
    </form>
    
    <form method="POST" action="<%=request.getContextPath()%>/signup.jsp">
      <table>
       	<tr>
        	<td><label>Don't have an account?</label></td>
       	</tr>
       	
       	<tr>
       		<td><a href="<%=request.getContextPath()%>/auth/signup"><input type="submit" name="submitRegistrationForm" value="Sign Up" id ="signUpButton_LoginPage"> <!--onclick="window.location.href='/signup.jsp'"--></a></td>
       	</tr>   
        	
        </table>    
    </form>
    
    
</body>
</html>