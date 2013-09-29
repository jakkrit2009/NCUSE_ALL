<?php
	include_once "configPHP.php";
	class admin{
		private $username;
		private $password;
		
		public function __construct(){
			$db= new config();
		}
		
		//เช็คค่าการloginโดยการนำpasswordมาถอดรหัสmp5
	public function login($username,$password){			
				$p=md5($password);
				$run=mysql_query('SELECT username,password FROM admin WHERE username="'.$username.'" AND password="'.$p.'"');
				mysql_query("SET NAMES UTF8");
				$user_data = mysql_fetch_array($run);
				$row=mysql_num_rows($run);
				if ($row == 1){
					$_SESSION['login'] = true;
					$_SESSION['uname'] = $user_data['username'];
					$_SESSION['pass'] = $user_data['password'];
					
					return TRUE;
				}else{
					return FALSE;
				}
		}	
		
		//ลบsession
		public function logout(){
			$_SESSION['login'] = FALSE;
        	session_destroy();
		}
		
		//เรียกข้อมูลออกมาจากdatabase
		function getLoca()
		{
			
			return mysql_query('SELECT idLoca, nameLoca, latitude, longitude, historyLoca, imageLoca, score, eagle, landgon 
			FROM location ORDER BY idLoca ASC ');
		}
		
		//เริ่มเก็บsession
		public function get_session()
		{
			return $_SESSION['login'];
		}
		
		//รับค่ามาปล้วส่งไป
		function setValues($nameLoca,$latitude,$longitude,$historyLoca,$score,$eagle,$landgon)
		{
			
			 $this->setNameLoca($nameLoca);
			 $this->setLatitude($latitude);
			 $this->setLongitude($longitude);
			 $this->setHistoryLoca($historyLoca);
			 $this->setScore($score);
			 $this->setEagle($eagle);
			 $this->setLandgon($landgon);
		}
		
		
		/*function editLoca($idLoca)
		{
			
			return mysql_query('UPDATE location SET idLoca="'.$this->getLirstName().'",nameLoca="'.$this->getNameLoca().'",
			latitude="'.$this->getLatitude().'" WHERE idLoca="'.$idLoca.'"');
		}
		
		function addLoca()
		{
			 mysql_query('INSERT INTO location (nameLoca, latitude, longitude, historyLoca, imageLoca,  score, eagle, landgon)
			  VALUES ("'.$this->getNameLoca().'",
			 "'.$this->getLatitude().'",
			 "'.$this->getLongitude().'",
			 "'.$this->getHistoryLoca().'",
			 "'.$this->getImageLoca().'",
			 "'.$this->getScore().'",
			 "'.$this->getEagle().'",
			 "'.$this->getLandgon().'")');
			 
			 return true;
		}*/
		
		//ลบค่าในฐานข้อมูล
		function delLoca()
		{
			
			 mysql_query('DELETE FROM location WHERE idLoca="'.$this->idLoca.'"');
			 return true;
		}
		
		//แสดงค่าในฐานข้อมูลจากรกที่รับมา
		function getEditLoca()
		{
			
		return	mysql_query('SELECT * FROM location WHERE idLoca="'.$this->idLoca.'"');
			
		}
		
		//รับค่าและคืนค่าที่รับมา
		//////////////////////////////////////////////////////////////////////////////////
		function getIdLoca()
		{
			return $this->idLoca;
		}
		
		function setIdLoca($idLoca)
		{
			$this->idLoca = $idLoca;
		}
		function getNameLoca()
		{
			return $this->nameLoca;
		}
		
		function setNameLoca($nameLoca)
		{
			$this->nameLoca = $nameLoca;
		}
		function getLatitude()
		{
			return $this->latitude;
		}
		
		function setLatitude($latitude)
		{
			$this->latitude = $latitude;
		}
		function getLongitude()
		{
			return $this->longitude;
		}
		
		function setLongitude($longitude)
		{
			$this->longitude = $longitude;
		}
		function getHistoryLoca()
		{
			return $this->historyLoca;
		}
		
		function setHistoryLoca($historyLoca)
		{
			$this->historyLoca = $historyLoca;
		}
		function getImageLoca()
		{
			return $this->imageLoca;
		}
		
		function setImageLoca($imageLoca)
		{
			$this->imageLoca = $imageLoca;
		}
		function getScore()
		{
			return $this->score;
		}
		
		function setScore($score)
		{
			$this->score = $score;
		}
		function getEagle()
		{
			return $this->eagle;
		}
		
		function setEagle($eagle)
		{
			$this->eagle = $eagle;
		}
		function getLandgon()
		{
			return $this->landgon;
		}
		
		function setLandgon($landgon)
		{
			$this->landgon = $landgon;
		}
		//////////////////////////////////////////////////////////////////////////
	}
?>
