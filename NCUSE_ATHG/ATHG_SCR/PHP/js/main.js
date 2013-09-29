//ตรวจสอบว่าต้องการลบข้อมูลหรือไม่
	function delLoca(idLoca){
		
		$('#dialog').html("คุณต้องการลบข้อมูลนี้ใช่หรือไม่");
		$("#dialog").dialog({ 'title':'ลบข้อมูล',
			buttons: {
						'ตกลง': function() {
								$.get("delLoca.php",{'idLoca':idLoca},function(data)
									{
										window.location.reload();
									});
								$(this).dialog('close');
								
							},
						'ยกเลิก': function() {
								$(this).dialog('close');
							}
					}
			});
	}
	
	//ตรวจสอบข้อมูลที่ส่งมาจารค่าSubmit
	function ChkSubmit()
		{
			var chkLatLong = /^[-+]?[0-9]+\.[0-9]+$/;
		var lati = $('#latitude').val();
		var long = $('#longitude').val();
			if($('#nameLoca').val()=='' || $('#latitude').val()=='' || $('#longitude').val()=='' || $('#historyLoca').val()==''
		|| $('#latitude').val()=='' || $('#score').val()=='' || $('#eagle').val()=='' || $('#landgon').val()=='')
			{
				$('#dialog').html('กรุณาใส่ข้อมูลให้ครบทุกช่อง');
		$('#dialog').dialog({
		buttons: [ { text: "ตกลง", click: function() { $( this ).dialog( "close" ); } } ] ,
		'title':'ผิดพลาด',
		'draggable' : false
		});
		event.preventDefault();
				return false;
			}
			
			return true;
		}

		
	//ตรวจสอบการใส่ตัวแรก
	function CheckNum(){
		if (event.keyCode < 48 || event.keyCode > 57 ){
			$('#dialog').html('ใส่ตัวเลขเท่านั้น');
		$('#dialog').dialog({
		buttons: [ { text: "ตกลง", click: function() { $( this ).dialog( "close" ); } } ] ,
		'title':'ผิดพลาด',
		'draggable' : false
		});
		      event.returnValue = false;
	    	}
	}