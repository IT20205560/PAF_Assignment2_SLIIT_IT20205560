<%@page import="model.PowercutSchedule"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>PowercutSchedule Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.6.0.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Powercut Schedule Management</h1>  <br>
				
				<form id="formApprovement" name="formApprovement" method="post" action="PowercutSchedule.jsp"> 
				<div class="p-3 mb-2 bg-light text-dark">
					<b>Description: </b> 
					<input id="description" name="description" type="text" class="form-control form-control-sm" placeholder="Enter description">  
					
					<br> 
					<b>Area:</b>  
					<input id="area" name="area" type="text" class="form-control form-control-sm" placeholder="Enter area">  
					
					<br>
					 <b>Date: </b> 
					 <input id="date" name="date" type="text" class="form-control form-control-sm" placeholder="Enter date">  
					 
					 <br> 
					 <b>Time:</b>  
					 <input id="time" name="time" type="text" class="form-control form-control-sm" placeholder="Enter time">  
					 
					 <br> 
					
					 
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidApp_IDSave" name="hidApp_IDSave" value=""> 
					
					</div> 
				</form> 
				<br>
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%
					PowercutSchedule appObj = new PowercutSchedule();
   									out.print(appObj.readPowercutSchedule());
   					%>  
					
				</div> 
				<br><br><br>
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>