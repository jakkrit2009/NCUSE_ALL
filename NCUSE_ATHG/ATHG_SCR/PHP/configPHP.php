<?php
//คลาสติดต่อฐานข้อมูล
class Config{

		public function __construct()
		{
				$con=mysql_connect("localhost","b531610005_game","jakkrit2009") or die("Cannot connect to database server!");
				mysql_query("SET NAMES UTF8");
				mysql_select_db("b531610005_athg",$con);
				
			}
			function fetch_array($command)
		{
			return mysql_fetch_array($command);
		}
}



?>