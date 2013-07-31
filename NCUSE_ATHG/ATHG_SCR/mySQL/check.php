<?php
include'config.php';

//ตรวจสอบว่าเคยกดยึดครองสถานที่นี้ในวันนี้แล้วหรือไม่

 $result = mysql_query('SELECT * FROM checkins WHERE idUsers ="'.$_GET['idUsers'].'" AND idLoca ="'.$_GET['idLoca'].'" ') or die(mysql_error());

if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
			$data_array = array();
			$i=0;
			while($arr = mysql_fetch_object($result))
			{
				$data_array[$i]['id'] = $arr->id;
				$data_array[$i]['idUsers'] = $arr->idUsers;
				$data_array[$i]['idLoca'] = $arr->idLoca;
				$data_array[$i]['date'] = $arr->date;
				
				//$dd=$arr->date+strtotime('+1 days ');
				if($arr->date<date('Y-m-d H:i:s')){
					mysql_query('UPDATE checkins SET  date = "'.date('Y-m-d H:i:s',strtotime('+1 days')) .'"  WHERE idUsers ="'.$_GET['idUsers'].'" AND idLoca ="'.$_GET['idLoca'].'" LIMIT 1')or die(mysql_error());
				}
				else {
					$erro = 1;
		echo $erro;
	}
				$i++;
			}
		}
		else {
			mysql_query('INSERT INTO checkins(idUsers, idLoca, date) VALUES("'.$_GET['idUsers'].'","'.$_GET['idLoca'].'","'.date('Y-m-d H:i:s',strtotime('+1 days')) .'")')or die(mysql_error());
		echo "BBB";
	}
}





?>