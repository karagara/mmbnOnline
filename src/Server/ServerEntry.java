package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

import Server.AccountManager;

public class ServerEntry {

    public static void main(String[] args) {
        //setup objects
        AccountManager acctMgmt = new AccountManager();

        //link to static docs
        externalStaticFileLocation("static");

        //route requests
        get("/account", (request, response) -> {
            return acctMgmt.getResponse();
        });
    }
}
