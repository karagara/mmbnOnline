package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

import Server.AccountManager;

public class ServerEntry {

    public static void main(String[] args) {
        //setup objects
        AccountManager acctMgmt = new AccountManager();

        //link to static docs
        externalStaticFileLocation("static");

        //route requests
        get("/account", (request, response) -> {
            return acctMgmt.getResponse(request, response);
        });

        post("/account", (request, response) -> {
            return acctMgmt.postResponse(request, response);
        });
    }
}
