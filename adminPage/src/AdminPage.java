import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.staticFileLocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AdminPage {
	
	public static void main(String[] args) {	
		staticFileLocation("/public");
		
		before("/admin/*", (request, response) -> {
			//method subject to change
			String role = request.queryParams("userRole");
			if(role == null || role.compareTo("admin") != 0)
				response.redirect("/test");
		} );
		
		get("/admin/users", (request, response) -> {
			String form = "<html>\n" + 
						  "<head>\n" +
						  "<link rel='stylesheet' type='text/css' href='../adminPage.css'>\n" +
						  "</head>\n" +
						  "<body background ='../Chip_icons_exe1.gif'>\n" +
						  "<table id='userTable' title='Users'>\n" +
						  "<thead>\n" +
						  "<tr><th class='col1'></th><th class='col2'>UserName</th><th class='col3' >Password</th><th class='col4'>Role</th></tr>\n" +
						  "</thead>\n" +
						  "<tbody>\n" +
						  "<tr>\n" +
						  "<td colspan='4'>\n" +
						  "<div id='tableArea'>\n" +
						  "<table id='scrollTable'>\n" +
						  getUsers() +
						  "</table>\n" +
						  "</div>\n" +
						  "</td>\n" +
						  "</tr>\n" +
						  "</tbody>\n" +
						  "</table>\n" +
						  "</div>\n" +
						  "<form id='adminForm' method='delete'>\n" +
						  "<input type='button' id='delete' value='Delete' onClick=deleteSelected() >\n" +
						  "<input type='hidden' id='deletedUsers' name='deletedUsers' value=''>\n" +
						  "<input type='submit' id='save' value='Save Changes' onClick=saveUsers() >\n" +
						  "<input type='hidden' id='saveList' name='saveList' value=''>\n" +
						  "</form>\n" +
						  "<script src='../jquery-2.1.3.js'></script>" +
						  "<script src='../adminPage.js'></script>\n" +
						  "</body>\n" + 
						  "</html>";
			return form;
        });
		
		get("/deletedUsers", (request, response) -> {
			String users = request.queryParams("deletedUsers");
			for(String s : users.split(" "))
			{
				//DELETE THINGS
				
			}
			
			//response.redirect("/admin/users/");
			return "<html>DELETED: " + users +"</html>";
		} );
		
		get("/savedUsers", (request, response) -> {
			
			
			return request.queryParams("saveList");
		} );
		
		get("/test", (request, response) -> {
			String form = "<html>\n" + 
						  "<head>\n" +
						  "</head>\n" +
						  "<body>\n" + 
						  "<form method='get' action='/admin/users'>\n"+
						  "Role: <input type='text' name ='userRole' >" + 
						  "<br><input type='submit'>\n" + 
						  "</form> \n" + 
						  "</body>\n" + 
						  "</html>";
			return form;
		
		} );
		
		
	}
	
	//TODO: change later for Database
	static String getUsers(){
		File file = new File("public/users.txt");
		String s = "";
		try{		
			Scanner sc = new Scanner(file);
		    while (sc.hasNextLine()) 
			{
		        String line = sc.nextLine();
				if(!line.contains("#"))
				{
					String userProps[] = line.split(":");
					if(userProps.length == 3)
					{
						String name = userProps[0].trim();
						s += "<tr>\n" +
							 "<td class='col1'> <input type='checkbox' class='deleteCheck' id='"+name+"_delete'> </td>\n" +
							 "<td class='col2'> <input type='text' id='"+name +"_userName' value='" +name+ "' onChange=updateSave('"+name+"')></td>\n" +
							 "<td class='col3'> <input type='text' id='"+name +"_password' value='" + userProps[1].trim() + "' onChange=updateSave('"+name+"')></td>\n" +
							 "<td class='col4'> <input type='text' id='"+name +"_role' value='" + userProps[2].trim() + "' onChange=updateSave('"+name+"')></td>\n" +
							 "</tr>\n";
					}
				}
			}
		    sc.close();
		}catch(IOException e){}
		
		return s;
	
	}

}

