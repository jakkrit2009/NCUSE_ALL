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
<link rel="stylesheet" href="css/jquery.fancybox.css" />
<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	
	<title>จัดการข้อมูลสถานที่</title>
	
	
	<link rel="stylesheet" type="text/css" href="css/style.css" />
    <script src="js/main.js" type="text/javascript"></script>
</head>

<body>
<?php
	//รับค่าที่เราจะแก้ไขออกมา
	$editId = $_GET["idLoca"];
	$admin->setIdLoca($editId);
	$show = $admin->getEditLoca();
	$test = $dbss->fetch_array($show);
	
?>
	<div id="dialog"></div>
    <form id="location" name="frmMain" method="post" action="update.php" enctype="multipart/form-data" class="location-form" 
    onSubmit="ChkSubmit();">
	<iframe id="iframe_target" name="iframe_target" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>


          <table width="200" border="0" align="center">
            <tr>
              <td align="right" valign="top"><label>
                <input type="hidden" name="idLoca" id="idLoca" value="<?php echo $test['idLoca'] ?>" />
              Name:</label></td>
              <td colspan="2"><input name="nameLoca" id="nameLoca" type="text" value=" <?php echo $test['nameLoca'] ?>"  /></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Latitude:</label></td>
              <td colspan="2"><input name="latitude" id="latitude" type="text" value=" <?php echo $test['latitude'] ?>"/></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Longitude:</label></td>
              <td colspan="2"><input name="longitude" id="longitude" type="text" value=" <?php echo $test['longitude'] ?>" /></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>History:</label></td>
              <td colspan="2"><textarea name="historyLoca"  cols="45" rows="3"  id="historyLoca" style="width:300px">
			  <?php echo $test['historyLoca'] ?></textarea></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Image:</label></td>
              <td colspan="2"><a href="<?php echo $test['imageLoca'] ?>" class="popup"><img src="<?php echo $test['imageLoca'] ?>"  
              style="width:100px" > </a><input type="file" name="imageLoca" id="imageLoca" size="30" value="เลือกรูปภาพ">
              </td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Score:</label></td>
              <td colspan="2"><input name="score" id="score" type="text" value=" <?php echo $test['score'] ?>" onkeypress="CheckNum()" 
              size="3" maxlength="3"  /></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Eagle:</label></td>
              <td colspan="2"><input name="eagle" id="eagle" type="text"  value=" <?php echo $test['eagle'] ?>" onKeyPress="CheckNum()" 
              size="5" maxlength="5"/></td>
            </tr>
            <tr>
              <td align="right" valign="top"><label>Landg:</label></td>
              <td colspan="2"><input name="landgon" id="landgon" type="text"  value=" <?php echo $test['landgon'] ?>" 
              onKeyPress="CheckNum()" size="5" maxlength="5"/></td>
            </tr>
            <tr>
              <td align="right" valign="top">&nbsp;</td>
              <td align="right" valign="top"><input type="submit" name="button" id="button" value="บันทึก"  style="width:65px"/></td>
              <td align="left" valign="top"><input type="button" name="12" id="12" value="กลับ" style="width:65px" 
              onclick="parent.location = 'location.php'"/></td>
            </tr>
          </table>
	</form></center>

	<script src="js/jquery.mousewheel-3.0.6.pack.js"></script>
     <script src="js/jquery.fancybox.pack.js"></script>
       <script>
           $(function(){
               $('.popup').fancybox();
            })
       </script>
</body>
</html>