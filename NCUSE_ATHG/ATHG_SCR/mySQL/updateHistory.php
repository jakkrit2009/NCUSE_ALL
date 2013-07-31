<?php

include'config.php';
//อัพเดทคะแนนของทีม
mysql_query('UPDATE location SET eagle = "'.$_GET['eagle'].'", landgon = "'.$_GET['landgon'].'"  WHERE idLoca = "'.$_GET['idLoca'].'" LIMIT 1')or die(mysql_error());


?>