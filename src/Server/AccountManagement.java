/**
* 	AccountManagement.java
*	Handles the responses corresponding to the account management page
*
*	Creator: Colten Normore
*/
 
package Server;

public class AccountManagement{
	private static String CALENDAR_FORM =
        "<html>\n" + 
        "<body>\n" + 
        "<form action='/index' method='POST'>\n" +
        "<label>Year: <input type='text' name='calYear'></label>\n" +
        "<label>Month: <input type='text' name='calMonth'></label>\n" +
        "<input type='submit'>\n" +
        "</body>\n" + 
        "</html>\n";

    public static String getResponse(){
    	return CALENDAR_FORM;
    }
}

