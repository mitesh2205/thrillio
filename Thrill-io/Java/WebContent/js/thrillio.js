
//Checks if the login fields are empty
function validateLoginForm(){
	
	var email = document.getElementById("email");
	var password = document.getElementById("password");
	
	
	if (email.value == "" || password.value == ""){
		alert("Please enter your email address and password!");
	}
	
	else{
		if(!email.checkValidity())
	    {
	       //document.getElementById('email_error').innerHTML="Please enter a valid email!";
	       alert("Please enter a valid email!");
	    }
	    
	}

}


function validateSignUpForm(){
	
	var email = document.getElementById("signup_email");
	var password = document.getElementById("signup_password");
	var firstName = document.getElementById("signup_firstName");
	var lastName = document.getElementById("signup_lastName");
	
	if(email.value === "" || password.value === "" || firstName.value === "" || lastName.value === ""){
		
		/*email.value = "";
		password.value = "";
		firstName.value = "";
		lastName.value = "";*/
		
		alert("Please enter your information in the required fields!");
	}
	
	
	else{
		
		if(!email.checkValidity() || !firstName.checkValidity() || !lastName.checkValidity()){	//!email.checkValidity() || 
			
			/*
			email.value = "";
			password.value = "";
			firstName.value = "";
			lastName.value = "";
			*/
			
			alert("You have entered invalid information in one or more required fields!" +
					" Please double-check them.");
		}
		
		else if(email === false){
			
			/*
			email.value = "";
			password.value = "";
			firstName.value = "";
			lastName.value = "";
			*/
			
			alert("An account with this email already exists! Please proceed to the login page.");
		}
		
		else{
			
			/*alert("You have successfully created your account. " +
			"Please proceed to the login page to start using your account.");*/
		}
		
	}
	
}

function printLoginEmailErrorOnWebpage(){
	var email = document.getElementById("email");
	
	if(!email.checkValidity() && email.value != "")
    {
       var emailFormatError = document.getElementsByClassName('emailformat_error')
       emailFormatError[0].innerHTML="Please enter a valid email!";
       
    }
	
	else{
		var emailFormatError = document.getElementsByClassName('emailformat_error')
		emailFormatError[0].innerHTML= "";
	}
	
}

function printSignupEmailErrorOnWebpage(){
	var email = document.getElementById("signup_email");
	
	if(!email.checkValidity() && email.value != "")
    {
       var emailFormatError = document.getElementsByClassName('emailformat_error')
       emailFormatError[0].innerHTML="Please enter a valid email!";
       
    }
	
	else{
		var emailFormatError = document.getElementsByClassName('emailformat_error')
		emailFormatError[0].innerHTML= "";
	}
	
}




/*
function checkExists(){
    var xmlhttp = new XMLHttpRequest();
    var signup_email = document.getElementById("signup_email");
    
    if(signup_email.value != ""){
    	var url = "exists.jsp?signup_email=" + email.value;
        xmlhttp.onreadystatechange = function(){
            if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
                if(xmlhttp.responseText == "\n\n\n\n\nUser already exists")
                    document.getElementById("isE").style.color = "red";
                else
                    document.getElementById("isE").style.color = "green";
                document.getElementById("isE").innerHTML = xmlhttp.responseText;
            }
            
        };
        try{
        xmlhttp.open("GET",url,true);
        xmlhttp.send();
    }catch(e){alert("unable to connect to server");}
    }
 
}
 */
/*
function checkExists(){
    //var xmlhttp = new XMLHttpRequest();
    var signup_email = document.getElementById("signup_email");
    
    if(signup_email.value != "" && signup_email.value.checkValidity()){
    	
    	var emailFormatError = document.getElementById('isE');
        emailFormatError.innerHTML="The email already exists!";
        
        /*
    	var url = "exists.jsp?signup_email=" + email.value;
        xmlhttp.onreadystatechange = function(){
            if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
                if(xmlhttp.responseText == "\n\n\n\n\nUser already exists")
                    document.getElementById("isE").style.color = "red";
                else
                    document.getElementById("isE").style.color = "green";
                document.getElementById("isE").innerHTML = xmlhttp.responseText;
            }
            
        };
        try{
        xmlhttp.open("GET",url,true);
        xmlhttp.send();
    }catch(e){alert("unable to connect to server");}
    }
        
        else{
        	
        }
 
}
*/

/*
$("#signUpButton_signUpPage").click(function(){
	location.reload();
}); 
*/





/*
function signUpButton_LoginPage(){
	jQuery("#signUpButton_LoginPage").click(function(){
		//document.getElementById("#signup_error").style.display = "none";
		document.location.assign('signup.jsp');
		location.reload(true);
	}); 
}

*/




	
	//Insert code to check & print whether a user already has an account
	//Check the user's email
	
	/*
	else{
		alert("You have successfully created your account. " +
				"Please proceed to the login page to start utilizing your account.");
		return true;
	}
	}*/