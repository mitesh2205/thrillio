<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page import="java.io.*,java.sql.*" %>

    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<link rel="stylesheet" type="text/css" href="/ThrillioWeb_Heroku/css/thrillio.css"/>
<script src="/ThrillioWeb_Heroku/js/thrillio.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    
    <script type="text/javascript">
    $(document).ready(function(){
        $("#signup_email").change(function(){
            var email = $(this).val();
            //if(email.length >= 3){
            $(".status").html("<img src='images/loading.gif'><font color=gray> Checking availability...</font>");
             $.ajax({
                type: "POST",
                url: "/ThrillioWeb_Heroku/auth/signup",
                data: "signup_email="+ email,
                success: function(msg){
              	  $(".status").html(msg);
                }
            }); 
            //}
            //else{

            //$(".status").html("<font color=red>Username should be <b>3</b> character long.</font>");
            //}

        });
    });
    </script>
    
    



<!--<script>

var signup_error = "<%=request.getAttribute("signup_error")%>)";

if(signup_error != "" || signup_error != null || signup_error != "null"){
	alert(signup_error); 
}
</script>-->

<title>Sign Up</title>
</head>
<body>
	<div style="height:65px;align: center;background: #2787db;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
	</div>
	<br><br>
	<form name="signUpForm" method="POST" action="<%=request.getContextPath()%>/auth/signup">
	
	
		<c:if test="${not empty signup_error}">
	    	<div id="signup_error">${signup_error}</div><br>
	    </c:if>
	    
      <fieldset>
	    <legend>Sign up</legend>	    
	    <table>
	    	<tr>
	    		<td><label><span class="required_field">* </span>Email:</label></td>
        		<td>
        			<input type="email" name="signup_email" id = "signup_email" pattern = ".+\.[A-Za-z]{2,}($|\n)" value="" oninput="printSignupEmailErrorOnWebpage()" required><!--<span><button name = "checkif_emailexists" id = "checkif_emailexists" onclick="location.href = '/ThrillioWeb_Heroku/check_email.jsp'">Check Email Address</button></span>--><span class="emailformat_error"></span> <span class = "status"></span> <br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label><span class="required_field">* </span>Password:</label></td>
        		<td>
        			<input type="password" name="signup_password" id = "signup_password" value="" required><br>
        		</td>        
        	</tr>
        	<tr>
	    		<td><label><span class="required_field">* </span>First Name:</label></td>
        		<td>
        			<input type="text" name="signup_firstName" id = "signup_firstName" pattern="[A-Za-z\s\-']+" value="" required><br>        			
        		</td>
        	</tr>
        	<tr>
	    		<td><label><span class="required_field">* </span>Last Name:</label></td>
        		<td>
        			<input type="text" name="signup_lastName" id = "signup_lastName" pattern="[A-Za-z\s\-']+" value="" required><br>        			
        		</td>
        	</tr>
        	<tr>
	    		<td><label>Gender:</label></td>
        		<td>
        			<select name = "gender_dropdown">
        				<option></option>
				        <option>MALE</option>
				        <option>FEMALE</option>
				        <option>OTHER</option>
			      	</select><br>        			
        		</td>
        	</tr>
        	<!--<tr>
	    		<td><label>User Type:</label></td>
        		<td>
        			<input type="text" name="usertype"><br>        			
        		</td>
        	</tr>-->
        	<tr>
        		<td>&nbsp;</td>
        		<td><a href="<%=request.getContextPath()%>/auth/signup"><input type="submit" name="submitRegistrationForm" id = "signUpPage_signUpButton" value="Sign Up" onclick="validateSignUpForm()"></a></td>
        	</tr>
        	
        	<tr>
        		<td><span class="required_field">*</span> = Required fields</td>
        	</tr>
        		
        </table>
	  </fieldset>      
    </form>
    
    <form method="POST" action="<%=request.getContextPath()%>/login.jsp">
	    <table>
	     	<tr>
	     		<td><label>Already have an account?</label></td>
	    		</tr>
	    	
	     	<tr>
	     		<td><a href="<%=request.getContextPath()%>/auth"><input type="submit" name="submitLoginForm" value="Log In"></a></td>
	     	</tr>   
	      	
	    </table>    
    </form>
    
</body>
</html>

