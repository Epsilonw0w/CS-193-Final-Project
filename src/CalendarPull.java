import java.net.http.*;
import java.net.URI;
import java.time.Duration;
/**
 * CalendarPull handles pulling and parsing from the Purdue Academic Calendar webpage
 * Fetches then writes the HTML to a file named WebpagePU.html
*/

public class CalendarPull {
    private static HttpResponse<String> response;
    private static final String CALENDAR_URL = "https://www.purdue.edu/registrar/calendars/academic/";
    //private static String htmlContent;

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
        java.nio.file.Files.writeString(
            java.nio.file.Path.of("WebpagePU.html"), response.body(),
            java.nio.charset.StandardCharsets.UTF_8);
        
        //String for ease of reference(?)
        //I should parse the specific entrypoints I need for this String
        /*
        htmlContent = java.nio.file.Files
            .readString(java.nio.file.Path.of("Webpage.html"), 
            java.nio.charset.StandardCharsets.UTF_8);
        */

    }
}