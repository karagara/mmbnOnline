import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.

public class ServerEntry {
	private static final String ACCRPAGE = 
		"<html>"+
		"<body>"+
		"<form action ='/form' method='POST'>"+
		"UserName <input type='text' name='name'><br>"+
		"Password <input type='password' name='password'><br>"+
		"Confirm Passowrd <input type='password' name='passwordCon'><br>"+
		"You have read and agree with terms of use"+
		"Yes <input type='radio' name='terms' value='yes'><br>"+
		"No <input type='radio' name='terms' value='no'><br>"+
		"</form>"+
		"</body>"+
		"<html>";

	 public static void main(String[] args) {
		externalStaticFileLocation("static");
		
		get("/accrPage", (request, response) -> /accrPage.html);
	}
}

