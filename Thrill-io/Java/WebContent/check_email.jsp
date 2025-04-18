<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ page import="java.sql.*" %>
  <%@ page import="java.io.*" %>
  <%@ page import="java.util.*" %>
  <%@page import="thrillio.controllers.EmailValidator"%>
<html>
<head>
<title>Email/Username Availability</title>
<style type="text/css">
.flable {
    color: gray;
}

.status {
    font-family: verdana;
    font-size: 12px;
}

.uname {
    color: blue;
}
</style>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
        $("#signup_email").change(function(){
            var email = $(this).val();
            //if(email.length >= 3){
            $(".status").html("<img src='images/loading.gif'><font color=gray> Checking availability...</font>");
             $.ajax({
                method: "POST",
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


</head>
<body>
    <div>
        <label class="flable">User Name :</label> <input type="email" name = "signup_email" id="signup_email" />&nbsp;<span class="status"></span>
    </div>

</body>
</html>