<?php

include'config.php';
//อัพเดทข้อมูลของผู้ใช้
mysql_query('UPDATE users SET sumExp = "'.$_GET['sumExp'].'", arrogate = "'.$_GET['arrogate'].'", idStatus = "'.$_GET['idStatus'].'" WHERE idUsers = "'.$_GET['idUsers'].'" LIMIT 1')or die(mysql_error());


?>