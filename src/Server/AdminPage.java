package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.staticFileLocation;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import spark.Session;

import Server.AdminPageDB;

public class AdminPage {
	public static void rigRoutes() {	

		get("/admin/users", (request, response) -> {
			response.redirect("/admin/usersAdmin.html");
			return null;
        });
		
		post("/deleteUsers", (request, response) -> {
			String ids = request.queryParams("deletedUsers");
			for(String id : ids.split(" ")){
				AdminPageDB.removeUser(id);
				System.out.println("Deleted user with user id=" + id);
			}
			
			response.redirect("/admin/users");
			return null;
		} );
		
		post("/saveUsers", (request, response) -> {
			String users = request.queryParams("saveList");
			saveUsers(users);
			response.redirect("/admin/users");
			return null;
		} );
		
		post("/allUsers", (request, response) -> {
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
			String userInfo[] = usersList[i].split("\t");
			if(userInfo.length > 0){
				
				String id = userInfo[0].trim();
				String userName = userInfo[1].trim();
				String password = userInfo[2].trim();
				String role = userInfo[3].trim();
				String email = userInfo[4].trim();
				String name = userInfo[5].trim();
			
				s += "<tr>\n" +
				 "<td class='col1'> <input type='checkbox' class='deleteCheck' id='" + id + "_delete'> </td>\n" +
				 "<td class='col2' id='"+id +"_id'>" + id + "</td>\n" +
				 "<td class='col3'> <input type='text' id='"+id +"_userName' value='" + userName + "' onChange=updateSave('"+ id +"')></td>\n" +
				 "<td class='col4'> <input type='text' id='"+id +"_password' value='" + password + "' onChange=updateSave('"+ id + "')></td>\n" +
				 "<td class='col5'> <input type='text' id='"+id +"_role' value='" + role + "' onChange=updateSave('"+ id +"')></td>\n" +
				 "<td class='col6'> <input type='text' id='"+id +"_email' value='" + email + "' onChange=updateSave('"+ id +"')></td>\n" +
				 "<td class='col7'> <input type='text' id='"+id +"_name' value='" + name + "' onChange=updateSave('"+ id +"')></td>\n" +
				 "</tr>\n";
			}
		}
		return s;
	}

	//format {"userId":"7","userName":"user1","password":"passwd","role":"admin","email":"null","name":"null"} 
	static void saveUsers(String userList){
		String users[] = userList.split("\n");
		for(String u : users)
		{
			String userId = "";
			String userName = "";
			String password = "";
			String role = "";
			String email = "";
			String name = "";
			
			String userInfo[] = u.split(",");
			for(String info : userInfo) // cycle through variables;
			{
				String val = info.split(":\"")[1];
				val = val.substring(0, val.length() - 1);
				if(info.contains("userId"))
					userId = val;
				else if(info.contains("userName"))
					userName = val;
				else if(info.contains("password"))
					password = val;
				else if(info.contains("role"))
					role = val;
				else if(info.contains("email"))
					email = val;
				else if(info.contains("name"))
					name = val;
			}
			AdminPageDB.editUser(userId, userName, password, role, email, name);
		}
	}
}

