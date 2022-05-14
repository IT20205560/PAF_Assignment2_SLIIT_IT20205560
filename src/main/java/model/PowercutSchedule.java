package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PowercutSchedule {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/egonline_frontend", "root", "root");
				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readPowercutSchedule() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'>"
						+ "<tr><th>Description</th>"
						+ "<th>Area</th>"
						+ "<th>Date</th>"
						+ "<th>Time</th>"
						+ "<th>Update</th><th>Remove</th></tr>";


				  String query = "select * from powercutschedule";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  	String mcode = Integer.toString(rs.getInt("mcode"));
						String description = rs.getString("description");
						String area = rs.getString("area");
						String date = rs.getString("date");
						String time = rs.getString("time");
						
					  // Add into the html table    

					  output += "<tr><td><input id='hidApp_IDUpdate' name='hidApp_IDUpdate' type='hidden' value='" + mcode + "'>" + description + "</td>"; 

					  output += "<td>" + area + "</td>";
						output += "<td>" + date + "</td>";
						output += "<td>" + time + "</td>";
						
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-mcode='"+ mcode +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the HTML tables
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the PowercutSchedule.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert appointment
		public String insertPowercutSchedule(String description, String area, String date, String time) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into powercutschedule (`mcode`,`description`,`area`,`date`,`time`)"+" values (?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, description);
				preparedStmt.setString(3, area);
				preparedStmt.setString(4, date);
				preparedStmt.setString(5,time);
				
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newPowercutSchedule = readPowercutSchedule();
				output = "{\"status\":\"success\", \"data\": \"" + newPowercutSchedule + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting PowercutSchedule.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update appointment
		public String updatePowercutSchedule(String mcode, String description, String area, String date, String time )  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE powercutschedule SET description=?,area=?,date=?,time=?WHERE mcode=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		   preparedStmt.setString(1, description);
			preparedStmt.setString(2,area);
			preparedStmt.setString(3, date);
			preparedStmt.setString(4,time);
			preparedStmt.setInt(5, Integer.parseInt(mcode));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newPowercutSchedule = readPowercutSchedule();
		   output = "{\"status\":\"success\", \"data\": \"" + newPowercutSchedule + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating PowercutSchedule Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deletePowercutSchedule(String mcode) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM powercutschedule WHERE mcode=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(mcode));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newPowercutSchedule = readPowercutSchedule();
		  output = "{\"status\":\"success\", \"data\": \"" + newPowercutSchedule + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting PowercutSchedule.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
