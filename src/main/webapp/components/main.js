$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validatePowercutForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidApp_IDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PowercutScheduleAPI",
		type : t,
		data : $("#formApprovement").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onPowercutSaveComplete(response.responseText, status);
		}
	});
}); 

function onPowercutSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidApp_IDSave").val("");
	$("#formApprovement")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidApp_IDSave").val($(this).closest("tr").find('#hidApp_IDUpdate').val());     
	$("#description").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#area").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#date").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#time").val($(this).closest("tr").find('td:eq(3)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "PowercutScheduleAPI",
		type : "DELETE",
		data : "mcode=" + $(this).data("mcode"),
		dataType : "text",
		complete : function(response, status)
		{
			onPowercutDeletedComplete(response.responseText, status);
		}
	});
});

function onPowercutDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validatePowercutForm() {  
	// description  
	if ($("#description").val().trim() == "")  {   
		return "Insert description.";  
		
	} 
	
	 // area  
	if ($("#area").val().trim() == "")  {   
		return "Insert area.";  
		
	} 
	 
	
	 
	 // date 
	if ($("#date").val().trim() == "")  {   
		return "Insert date.";  
		
	} 
	
	// time 
	if ($("#time").val().trim() == "")  {   
		return "Insert time.";  
		
	} 
	 
	 
	
 
	 
	 return true; 
	 
}