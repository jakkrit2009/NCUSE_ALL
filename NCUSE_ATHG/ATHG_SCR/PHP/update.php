<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.3.custom.min.css"/>
<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<div id="dialog"></div>
<?php
	include_once("config.php");
	
	if($_POST['nameLoca']==""){
		
		header('location:addLoca.php');
		
	}
	//ตรวจสอบนามสกุลของไฟล์และขนาดของไฟล์
	///////////////////////////////////////////////////////////////////////////////////////////////
	$allowedExts = array("gif", "jpeg", "jpg", "png");
	$temp = explode(".", $_FILES["imageLoca"]["name"]);
	$extension = end($temp);
	
	if ((($_FILES["imageLoca"]["type"] == "image/gif")|| ($_FILES["imageLoca"]["type"] == "image/GIF")
	|| ($_FILES["imageLoca"]["type"] == "image/jpeg")|| ($_FILES["imageLoca"]["type"] == "image/JPEG")
	|| ($_FILES["imageLoca"]["type"] == "image/jpg")|| ($_FILES["imageLoca"]["type"] == "image/JPG")
	|| ($_FILES["imageLoca"]["type"] == "image/png")|| ($_FILES["imageLoca"]["type"] == "image/PNG"))
	&& ($_FILES["imageLoca"]["size"] < 2000000)&& in_array($extension, $allowedExts)){
	  //ตรวจสอบค่าที่เข้ามาเว่าป็นerrorหรือไม่
	  if ($_FILES["imageLoca"]["error"] > 0){
		  
			echo"<script> $('#dialog').html('Error: " . $_FILES["'imageLoca'"]["'error'"] ."');
				$('#dialog').dialog({
					buttons: [ { text: 'ตกลง', click: function() { 
						window.location='editLoca.php?idLoca=".$_POST['idLoca']."';
						$( this ).dialog( 'close' ); } } ] ,
				'title':'ผิดพลาด',
				'draggable' : false
				
				});
				
				event.preventDefault();</script>";
				
		}
		
		//ตรวจสอบตำแหน่งไฟล์และที่อยู่ของไฟล์
	  else if(move_uploaded_file($_FILES["imageLoca"]["tmp_name"],"image/".$_FILES["imageLoca"]["name"])){
		  
		mysql_query('UPDATE location SET nameLoca="'.$_POST['nameLoca'] .'", latitude="'.$_POST['latitude'] .'",
		 longitude="'.$_POST['longitude'] .'", historyLoca="'.$_POST['historyLoca'] .'", 	
		 imageLoca="http://110.164.78.161/~b531610005/image/'.$_FILES['imageLoca']['name'].'",
		 score="'.$_POST['score'] .'", eagle ="'.$_POST['eagle'] .'", landgon="'.$_POST['landgon'] .'" WHERE idLoca="'.$_POST['idLoca'] .'"
		 ');
		
		echo "<script> $('#dialog').html('บันทึกข้อมูลเแล้ว');
			$('#dialog').dialog({
				buttons: [ { text: 'ตกลง', click: function() { 
				window.location='location.php';
				$( this ).dialog( 'close' ); } } ] ,
			'title':'เสร็จสิ้น',
			'draggable' : false
			});
			
			event.preventDefault();</script>";
			
		}
	  }
	  //ตรวจสอบว่าไฟล์ที่รับเป็นค่าว่างหรือไม่
	else if ($_FILES["imageLoca"]["name"] == "")
	  {
		  mysql_query('UPDATE location SET nameLoca="'.$_POST['nameLoca'] .'", latitude="'.$_POST['latitude'] .'", 
		  longitude="'.$_POST['longitude'] .'", historyLoca="'.$_POST['historyLoca'] .'", 
		  score="'.$_POST['score'] .'", eagle ="'.$_POST['eagle'] .'", landgon="'.$_POST['landgon'] .'" 
		  WHERE idLoca="'.$_POST['idLoca'] .'"');
		  
		echo "<script> $('#dialog').html('บันทึกข้อมูลเแล้ว');
			$('#dialog').dialog({
				buttons: [ { text: 'ตกลง', click: function() { 
				window.location='location.php';
				$( this ).dialog( 'close' ); } } ] ,
			'title':'เสร็จสิ้น',
			'draggable' : false
			});
			
			event.preventDefault();</script>";
			
	  }
	  else{
		  
	   echo"<script> $('#dialog').html('กรุณาตรวจสอบไฟล์ภาพอีกครั้ง');
				$('#dialog').dialog({
					buttons: [ { text: 'ตกลง', click: function() { 
					window.location='editLoca.php?idLoca=".$_POST['idLoca']."';
				$( this ).dialog( 'close' ); } } ] ,
			'title':'เกิดข้อผิดพลาดในการอัพโหลด',
			'draggable' : false
			});
			
			event.preventDefault();</script>";
	  }
  /////////////////////////////////////////////////////////////////////////////////////////////////////
?>
