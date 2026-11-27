package org.example;
//Utilities
import java.util.ArrayList;
import java.util.regex.*;
//HTTPClient and HTTPRequest
import java.net.http.*;
import java.net.URI;
import java.time.Duration;
//Jsoup
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * CalendarPull handles pulling and parsing from the Purdue Academic Calendar webpage
 * Fetches then writes the HTML to a file named WebpagePU.html (might be redundant)
 * Prints the dates and events filtered by holiday-related keywords
*/

public class CalendarPull {
    private static HttpResponse<String> response;
    private static final String CALENDAR_URL = "https://www.purdue.edu/registrar/calendars/academic/";

    //Fetch the response from CALENDAR_URL and return the HTML content as a String
    //private method for parseCalendarData() to use
    private String fetchCalendarData() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(CALENDAR_URL))
            .timeout(Duration.ofSeconds(20))
            .GET()
            .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //return the HTML content as a String
        return response.body();
    }

    //Parse the HTML content for only dates whose events have a specific keyword in them
    public ArrayList<String> parseCalendarData() throws Exception {
        //filters out non-holiday dates        
        Pattern KEYWORDS_INC = Pattern.compile("closed|holiday|break|winter term|spring term", Pattern.CASE_INSENSITIVE);
        Pattern KEYWORDS_EXC = Pattern.compile("test|exam", Pattern.CASE_INSENSITIVE);

        //Jsoup parses the HTML content into a Document object for easier manipulation
        Document doc = Jsoup.parse(fetchCalendarData(), CALENDAR_URL);
        ArrayList<String> lines = new ArrayList<>();

        //table of all dates---printed for debugging
        Elements tables = doc.select("table.has-transparent-background-color");

        for (Element table : tables) {

            Elements rows = table.select("tr");
            rows.removeIf(row -> !KEYWORDS_INC.matcher(row.text()).find());
            rows.removeIf(row -> KEYWORDS_EXC.matcher(row.text()).find());

            //cycles through filtered rows and grabs dates
            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.isEmpty()) continue;
                if (cells.size() < 2) continue;

                String date  = cells.get(0).text().trim();
                String event = cells.get(1).text().trim();
                System.out.println(date + " — " + event);
                lines.add(date + " — " + event);
            }
        }
        return lines;
    }
}