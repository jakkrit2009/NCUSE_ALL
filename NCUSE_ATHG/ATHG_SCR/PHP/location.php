<?php
	//เริ่มsessionและตรวจสอบการlogin
	session_start();
	include_once("configPHP.php");
	include_once("admin.php");
	
	$admin = new admin();
	$dbss= new config();
	
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

<body >
    <div id="dialog"></div>
   <div align="right"> <h2> <input type="button" name="logout" id="logout" value="ออกจากระบบ" onclick="parent.location = 'logout.php'" >
   </h2></div>
    <center><form id="location" action="#" method="post" class="location-form" >
   <h2> <input type="button" name="add" id="add" value="เพิ่มสถานที่" onclick="parent.location = 'addLoca.php'" ></h2>
    <table align="center" class="ui-widget-header" id="users">
      <thead>
        <tr class="ui-widget-header ">
          <th>ID </th>
          <th>Name</th>
          <th>Latitude</th>
          <th>Longitude</th>
          <th>History</th>
          <th>Image</th>
          <th>Score</th>
          <th>Eagle</th>
          <th>Landgon</th>
          <th></th>
          <th></th>
        </tr>
      </thead>
      <tbody>
<?php
	//ทำการวนลูปเพื่อแสดงค่าที่มีออกมา
	/////////////////////////////////////////////////////////////////////////////////////////////////
	$arr = $admin->getLoca();
	$no=1;
	while($res = $dbss->fetch_array($arr)){
?>
        <tr>
          <td id="idLoca"<?php $no ?>><input name="idLoca" type="text" disabled="disabled"  value=" <?php echo $res['idLoca'] ?>"  
          style="width:35px"/></td>
          <td id="idLoca"<?php $no ?>><input name="nameLoca" type="text" disabled="disabled"  value=" <?php echo $res['nameLoca'] ?>"  
          style="width:90px"/></td>
          <td id="idLoca"<?php $no ?>><input name="latitude" type="text" disabled="disabled"  value=" <?php echo $res['latitude'] ?>"  
          style="width:90px"/></td>
          <td id="idLoca"<?php $no ?>><input name="longitude" type="text" disabled="disabled"  value=" <?php echo $res['longitude'] ?>"  
          style="width:90px"/></td>
          <td id="idLoca"<?php $no ?>><textarea name="historyLoca" cols="45" rows="3" disabled="disabled" id="textarea" 
          style="width:300px"><?php echo $res['historyLoca'] ?></textarea></td>
          <td id="idLoca"<?php $no ?>><a href="<?php echo $res['imageLoca'] ?>" class="popup"><img src="<?php echo $res['imageLoca'] ?>" 
          style="width:60px"></a></td>
          <td id="idLoca"<?php $no ?>><input name="score" type="text" disabled="disabled"  value=" <?php echo $res['score'] ?>"  
          style="width:35px"/></td>
          <td id="idLoca"<?php $no ?>><input name="eagle" type="text" disabled="disabled"  value=" <?php echo $res['eagle'] ?>"  
          style="width:35px"/></td>
          <td id="idLoca"<?php $no ?>><input name="landgon" type="text" disabled="disabled"  value=" <?php echo $res['landgon'] ?>"  
          style="width:65px"/></td>
          <td id="idLoca"<?php $no ?>><input type="button" name="edit" id="edit" value="แก้ไข" onClick="parent.location = 
          'editLoca.php?idLoca=<?php echo $res['idLoca'] ?>'" style="width:65px"></td>
          <td id="idLoca"<?php $no ?>><input type="button" name="del" id="del" value="ลบ" onClick="delLoca(<?php echo $res['idLoca'] ?>);" 
          style="width:45px"></td>
          
          </tr>		
 <?php    
		$no++;
	}
	/////////////////////////////////////////////////////////////////////////////////////////
?>
  	</tbody>
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