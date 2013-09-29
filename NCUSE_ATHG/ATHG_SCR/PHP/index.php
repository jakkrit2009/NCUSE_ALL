<?php
//เรียกใช้sessionและตรวจสอบsessionว่ามีหรือไม่
	session_start();
	include_once("admin.php");
	$admin = new admin();
	if ($admin->get_session())
	{
   		header("location: location.php");
	}
	
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.3.custom.min.css"/>
<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	
	<title>จัดการสถานที่</title>
	
	
<link rel="stylesheet" type="text/css" href="css/styleLogin.css" />
</head>

<body>
    <div id="dialog"></div>
	<form id="login" action="#" method="post" class="Login-form">
		<fieldset>
		
			<legend>Log in</legend>
			
			<label for="login">Username</label>
			<input type="text" id="username" name="uname"placeholder="ชื่อผู้ใช้" />
			<div class="clear"></div>
			
			<label for="password">Password</label>
			<input type="password" id="password" name="passwd" placeholder="รหัสผ่าน" />
			<div class="clear"></div>

			
			<br />
			
			<input type="submit" style="margin: -20px 0 0 287px;" class="button big" name="submitBtm" value="Log in" 
            onclick="validateLogin();" />	
		</fieldset>
	</form>

<?php
		 //ตรวจสอบการLlgin
	 
	if ($_SERVER["REQUEST_METHOD"] == "POST") {
		$login = $admin->login($_POST["uname"], $_POST["passwd"]);
		if ($login) {
			
		   header("location: location.php");
		   
		} 	
		else {
	
			header("location: index.php");
		}
	}
?>

<script type="text/javascript" src="js/validate.js"></script> 
</body>

</html>