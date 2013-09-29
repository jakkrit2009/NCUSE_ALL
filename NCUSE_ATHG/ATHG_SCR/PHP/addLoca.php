<?php
	//เริ่มsessionและตรวจสอบsession
	session_start();
	include_once("configPHP.php");
	include_once("admin.php");
	
	$admin = new admin();
	$dbss= new config();
	
	
	$_SESSION["stat"] = $status;
		if (!$admin->get_session())
		{
			header('location:index.php');
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
	
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script src="js/main.js" type="text/javascript"></script>
</head>

<body >
    
     <div id="dialog"></div>
	<form id="location" name="frmMain" method="post" action="add.php" enctype="multipart/form-data" class="location-form" 
    onSubmit="return ChkSubmit();">
	<iframe id="iframe_target" name="iframe_target" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>
 
      <table width="200" border="0" align="center">
        <tr>
          <td align="left" valign="top"><label>Name:</label></td>
          <td colspan="2" align="left" valign="top"><input name="nameLoca" type="text" id="nameLoca" placeholder="ชื่อสถานที่" 
          size="100" maxlength="100"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Latitude:</label></td>
          <td colspan="2" align="left" valign="top"><input type="text" name="latitude" id="latitude" placeholder="ละติจูด"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Longitude:</label></td>
          <td colspan="2" align="left" valign="top"><input type="text" name="longitude" id="longitude" placeholder="ลองจิจูด"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>History:</label></td>
          <td colspan="2" align="left" valign="top"><textarea name="historyLoca" id="historyLoca" cols="50" rows="5" 
          placeholder="ความเป็นมาของสถานที่"></textarea></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Image:</label></td>
          <td colspan="2" align="left" valign="top"><input type="file" name="imageLoca" size="30" value="เลือกรูปภาพ"></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Score:</label></td>
          <td colspan="2" align="left" valign="top"><input name="score" type="text" id="score" placeholder="คะแนนสถานที่" 
          onkeypress="CheckNum()" size="3" maxlength="3"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Eagle:</label></td>
          <td colspan="2" align="left" valign="top"><input name="eagle" type="text" id="eagle" placeholder="คะแนนทีม Eagle" 
          onKeyPress="CheckNum()" size="5" maxlength="5"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"><label>Landg:</label></td>
          <td colspan="2" align="left" valign="top"><input name="landgon" type="text" id="landgon" placeholder="คะแนนทีม Landgon" 
          onKeyPress="CheckNum()" size="5"/></td>
        </tr>
        <tr>
          <td align="left" valign="top"></td>
          <td align="right" valign="top"><input type="submit" name="10" id="10" value="บันทึก" style="width:65px"/></td>
          <td align="left" valign="top"><input type="button" name="12" id="12" value="กลับ" style="width:65px" 
          onclick="parent.location = 'location.php'"/></td>
      </table>
</form>
 
</body>

</html>