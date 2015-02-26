package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.staticFileLocation;


import spark.Request;
import spark.Response;
import spark.Session;

import Server.AdminPageDB;

public class AdminPage {
	public static void rigRoutes() {	

		get("/admin/users", (request, response) -> {
			response.redirect("/admin/usersAdmin.html");
			return null;
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
		
		get("/allUsers", (request, response) -> {
			return getUsers();
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
						  
			
			Session sess = request.session(true);
			if ( sess == null ) {
                response.redirect("/login.html");
			}
			else {
				sess.attribute("role", "admin");
			}
			return form;
		} );
		
		
	}
	
	static String getUsers(){
		String usersList[] = AdminPageDB.getUsersInfo().split("\n");
		String s = "";
		for(int i = 0; i < usersList.length; i++) {
			String userInfo[] = usersList[i].split(" ");
			String name = userInfo[0].trim();
			s += "<tr>\n" +
				 "<td class='col1'> <input type='checkbox' class='deleteCheck' id='"+name+"_delete'> </td>\n" +
				 "<td class='col2'> <input type='text' id='"+name +"_userName' value='" +name+ "' onChange=updateSave('"+name+"')></td>\n" +
				 "<td class='col3'> <input type='text' id='"+name +"_password' value='" + userInfo[1].trim() + "' onChange=updateSave('"+name+"')></td>\n" +
				 "<td class='col4'> <input type='text' id='"+name +"_role' value='" + userInfo[2].trim() + "' onChange=updateSave('"+name+"')></td>\n" +
				 "</tr>\n";
		}
		return s;
	}

}

