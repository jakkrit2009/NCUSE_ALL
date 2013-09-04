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
				 $data_array['success'] = 1;
				//$dd=$arr->date+strtotime('+1 days ');
				if($arr->date<date('Y-m-d H:i:s')){
					mysql_query('INSERT INTO checkins(idUsers, idLoca, date) VALUES("'.$_GET['idUsers'].'","'.$_GET['idLoca'].'","'.date('Y-m-d H:i:s',strtotime('+1 days')) .'")')or die(mysql_error());
					echo json_encode($data_array);
				}
				else {
					$data_array["success"] = 0;
            $data_array["message"] = "No user found";
 
            // echo no users JSON
            echo json_encode($data_array);
	}
				$i++;
			}
		}
		else {
			mysql_query('INSERT INTO checkins(idUsers, idLoca, date) VALUES("'.$_GET['idUsers'].'","'.$_GET['idLoca'].'","'.date('Y-m-d H:i:s',strtotime('+1 days')) .'")')or die(mysql_error());
		$data_array["success"] = 1;
		echo json_encode($data_array);
	}
}





?>