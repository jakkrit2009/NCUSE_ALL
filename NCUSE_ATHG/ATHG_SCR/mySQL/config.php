<?php
//เชื่อมต่อฐานข้อมูล
$DB_HOST = 'localhost';
$DB_USER = 'b531610005_game';
$DB_PASS = 'jakkrit2009';
$DB_DATABASE ='b531610005_athg';

mysql_connect($DB_HOST,$DB_USER,$DB_PASS) or die(mysql_error());
mysql_select_db($DB_DATABASE) or die(mysql_error());
mysql_query('SET NAMES "UTF8"');

?>