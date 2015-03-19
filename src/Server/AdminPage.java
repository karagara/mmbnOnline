package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.staticFileLocation;
import static spark.Spark.halt;
import spark.Session;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import Server.AdminPageDB;

public class AdminPage {
	public static void rigRoutes() {	

		get("/admin/users", (request, response) -> {
			response.redirect("/admin/usersAdmin.html");
			return null;
        });
		
		post("/deleteUsers", (request, response) -> {
			String ids = request.body();
			for(String id : ids.split(" "))
				AdminPageDB.removeUser(id);
			return "";
		} );
		
		post("/saveUsers", (request, response) -> {
			saveUsers(request.body());
			return "";
		} );
		
		post("/allUsers", (request, response) -> {
			return getUsers();
		} );		
	}

	static String getUsers(){
		String users = AdminPageDB.getUsersInfo();
		if(users == null || users.length() == 0)
			return "";
		
		String usersList[] = users.split("\n");
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
		 try {
             Gson gson = new Gson();
             String users[] = userList.split("\n");
             // attempt to convert JSON to UserObject
             for(String user : users) {
             	UserObject obj = gson.fromJson(user, UserObject.class);
             	AdminPageDB.editUser(obj.id, obj.userName, obj.password, obj.role, obj.email, obj.name);
             }
         }
         catch ( JsonParseException ex ) {
             System.out.println("/saveUsers: malformed values");
             halt(400, "malformed values");
         }
	}
}

class UserObject{
	public String id;
	public String userName;
	public String password;
	public String role;
	public String email;
	public String name;
}