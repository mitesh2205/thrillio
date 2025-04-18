<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<div style="height:65px;align: center;background: #2787db;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/auth">
      <fieldset>
	    <legend>Log In</legend>	    
	    <table>
	    	<tr>
	    		<td><label>Email:</label></td>
        		<td>
        			<input type="text" name="email"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Password:</label></td>
        		<td>
        			<input type="password" name="password"><br>
        		</td>        
        	</tr>
        	<tr>
        		<td>&nbsp;</td>
        		<td><a href="<%=request.getContextPath()%>/auth"><input type="submit" name="submitLoginForm" value="Log In"></a></td>
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
    
    <form method="POST" action="<%=request.getContextPath()%>/auth/signup">
      <table>
       	<tr>
        	<td><label>Don't have an account?</label></td>
       	</tr>
       	
       	<tr>
       		<td><a href="<%=request.getContextPath()%>/auth/signup"><input type="submit" name="submitRegistrationForm" value="Sign Up" onclick="window.location.href='/signup.jsp'"></a></td>
       	</tr>   
        	
        </table>    
    </form>
    
    
</body>
</html>