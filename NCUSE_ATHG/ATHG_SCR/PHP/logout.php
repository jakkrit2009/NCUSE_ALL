<?php 
//ลบsessionที่มีอยู่
	session_start();
	include_once("admin.php");
	$admin = new admin();
	$admin->logout();
	header("location:index.php");
 ?>