import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.externalStaticFileLocation;
import spark.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class ServerEntry {

    private static String HTML_HEAD =
        "<!DOCTYPE html>" +
        "<html>\n" +
        "<head>\n" +
        "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n" +
        "</head>\n" +
        "<body>\n";

    private static String HTML_TAIL = 
        "</body>\n" +
        "</html>\n";

    private static String CALENDAR_FORM =
        "<html>\n" + 
        "<body>\n" + 
        "<form action='/index' method='POST'>\n" +
        "<label>Year: <input type='text' name='calYear'></label>\n" +
        "<label>Month: <input type='text' name='calMonth'></label>\n" +
        "<input type='submit'>\n" +
        "</body>\n" + 
        "</html>\n";

    private static String NOTE_FORM = 
        HTML_HEAD +
        "<p>%s</p>\n" +
        "<form action='/day-note' method='POST'>\n" +
        "<label>Message: <input type='text' name='noteMessage'></label>\n" +
        "<input type='submit'>\n" +
        HTML_TAIL;

    public static void main(String[] args) {
        externalStaticFileLocation("static");

        get("/index", (request, response) -> CALENDAR_FORM );

        post("/index", (request, response) -> {
            Session sess = request.session(true);

            int calYear = Integer.parseInt( request.queryParams("calYear") );
            int calMonth = Integer.parseInt( request.queryParams("calMonth") );
            String style = request.queryParams("style");

            sess.attribute("calYear", calYear );
            sess.attribute("calMonth", calMonth );

            response.redirect("/month");
            return null;
        });

        get("/month", (request, response) -> {
            //Parse inputs
            int year = request.session().attribute("calYear");
            int month = request.session().attribute("calMonth"); 

            //Setup calendar object, with day = 1st
            Calendar calendar = new GregorianCalendar(year, month-1, 1);

            String body = "<table>\n";

            body += ServerEntry.createCalendarHead(calendar);
            body += ServerEntry.createCalendarBody(calendar, request.session());
            body += "</table>\n";

            return HTML_HEAD + body + HTML_TAIL;
        });

        get("/day-note/:day/:month/:year", (request,response) -> {
            int year = Integer.parseInt( request.params(":year") );
            int month = Integer.parseInt( request.params(":month") );
            int day = Integer.parseInt( request.params(":day") );

            request.session().attribute("noteYear", year );
            request.session().attribute("noteMonth", month );
            request.session().attribute("noteDay", day );

            response.redirect("/day-note");
            return null;
        });

        get("/day-note", (request,response) -> {
            int year = request.session().attribute("noteYear");
            int month = request.session().attribute("noteMonth");
            int day = request.session().attribute("noteDay");

            String key = "/" + year + "/" + month + "/" + day;
            String prevMessage = request.session().attribute(key);
            String body;
            if (prevMessage == null) {
                body = "There are no previous notes! Post one!";
            } else {
                body = prevMessage;
            }

            return String.format( NOTE_FORM, body );
        });

        post("/day-note", (request,response) -> {
            int year = request.session().attribute("noteYear");
            int month = request.session().attribute("noteMonth");
            int day = request.session().attribute("noteDay");

            String key = "/" + year + "/" + month + "/" + day;
            String message = request.queryParams("noteMessage") ;
            request.session().attribute(key, message);

            response.redirect("/month");
            return null;
        });
    }

    public static String createCalendarHead(Calendar calendar){
            //Make a table with mon, tues, etc, and the month name at the top
            String monthString = new SimpleDateFormat("MMMM").format(calendar.getTime());
            return 
                "<thead>\n"+
                "<tr>\n"+
                "<th colspan=\"7\">" + monthString + "</th>\n" +
                "</tr>\n"+
                "<tr>\n"+
                "<th>Sun</th>\n"+
                "<th>Mon</th>\n"+
                "<th>Tue</th>\n"+
                "<th>Wed</th>\n"+
                "<th>Thu</th>\n"+
                "<th>Fri</th>\n"+
                "<th>Sat</th>\n"+
                "</tr>\n"+
                "</thead>\n";
    }

    public static String createCalendarBody(Calendar calendar, Session session){
        String body = 
            "<tbody>\n";

        //Find what day the 1st falls on, fill in the rest
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        if (dayOfWeek < 1) 
            body += "<tr>\n";
        
        for(int i = 1; i < dayOfWeek; i++){
            body += "<td></td>\n";
        }

        //Until you aren't in the same month:
        //Increment the date by a day in the calendar object
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        while(calendar.get(Calendar.MONTH) == currentMonth){
            //If day of the week == 1 (sunday), make a new line
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
                body +="<tr>\n";

            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            String link = 
                "<a href='/day-note/"
                + currentDay + "/" 
                + (currentMonth+1) + "/" 
                + currentYear + "'>";

            body += "<td>" + link + currentDay + "</a>";
            String key = "/" + currentYear + "/" + (currentMonth+1) + "/" + currentDay;
            String prevMessage = session.attribute(key);
            if (prevMessage != null) {
                body += "<p>" + prevMessage + "</p>" ;
            }
            body += "</td>\n";

            //If day of the week == 7, cap off old line
            if (calendar.get(Calendar.DAY_OF_WEEK) == 7)
                body += "</tr>\n";
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        //Fill in the rest of the table
        if (calendar.get(Calendar.DAY_OF_WEEK) != 1){
            while (calendar.get(Calendar.DAY_OF_WEEK) != 1){
                body += "<td></td>\n";
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            body += 
                "</tr>\n";
        }
        body += "</tbody>\n";
        return body;   
    }
}
