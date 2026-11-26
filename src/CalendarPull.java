package org.example;

import java.util.regex.*;
//HTTPClient and HTTPRequest
import java.net.http.*;
import java.net.URI;
import java.nio.file.*;
import java.time.Duration;
//Jsoup
import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

/**
 * CalendarPull handles pulling and parsing from the Purdue Academic Calendar webpage
 * Fetches then writes the HTML to a file named WebpagePU.html (might be redundant)
 * Prints the dates and events filtered by holiday-related keywords
*/

public class CalendarPull {
    private static HttpResponse<String> response;
    private static final String CALENDAR_URL = "https://www.purdue.edu/registrar/calendars/academic/";
    private static String htmlContent;
    private static Document doc;

    //Fetch the response from CALENDAR_URL and write it to a file named Webpage.html
    //Then read the file and store it in a String for parsing
    public void FetchCalendarData() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(CALENDAR_URL))
            .timeout(Duration.ofSeconds(20))
            .GET()
            .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("status: " + response.statusCode());
        
        //Write the response body to a file named Webpage.html
        Files.writeString(
            Path.of("WebpagePU.html"), response.body(),
            StandardCharsets.UTF_8);
    }

    //Parse the HTML content for only dates whose events have a specific keyword in them
    public void parseCalendarData() throws Exception {
        //filters out non-holiday dates        
        Pattern KEYWORDS_INC = Pattern.compile("closed|holiday|break|winter term|spring term", Pattern.CASE_INSENSITIVE);
        Pattern KEYWORDS_EXC = Pattern.compile("test|exam", Pattern.CASE_INSENSITIVE);

        //Write to String for Jsoup
        htmlContent = Files
            .readString(Path.of("WebpagePU.html"), 
            StandardCharsets.UTF_8);
        try {
            doc = Jsoup.parse(htmlContent, CALENDAR_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //table of all dates---printed for debugging
        Elements tables = doc.select("table.has-transparent-background-color");

        for (Element table : tables) {
            if (table == null) return;

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
            }

            System.out.println("\n");
        }
    }
}
