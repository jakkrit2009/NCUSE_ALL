$(function()
	{
		$('#uname').focus();
	});


//ตรวจสอบข้อผิดพลาดของการLOGIN
function validateLogin() {
		
		var uname = document.forms["login"]["username"].value;  
		var pword = document.forms["login"]["password"].value;
		
		if(uname == ""){
			$('#dialog').html('กรุณาใส่username');
		$('#dialog').dialog({
		buttons: [ { text: "ตกลง", click: function() { $( this ).dialog( "close" ); } } ] ,
		'title':'ผิกพลาด',
		'draggable' : false
		});
		event.preventDefault();
		return 1;
		}
		else if(pword == "")
		{
			$('#dialog').html('กรุณาใส่password');
		$('#dialog').dialog({
		buttons: [ { text: "ตกลง", click: function() { $( this ).dialog( "close" ); } } ] ,
		'title':'ผิกพลาด',
		'draggable' : false
		});
		event.preventDefault();
		return 1;
		}
}
