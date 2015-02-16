package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

import Server.AccountManagement;

public class ServerEntry {

    public static void main(String[] args) {
        externalStaticFileLocation("static");

        get("/account", (request, response) -> {
            return AccountManagement.getResponse();
        });
    }
}
