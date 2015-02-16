package Server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

public class AccountCreation {

    public static void main(String[] args) {
        externalStaticFileLocation("static");

        get("/accountCreation", (request, response) ->"/accrPage.html";
    }
}
