package org.example;

import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;

public class Main {
        public String getGreeting() {
        return "Purdue Academic Calendar Scraper";
    }
    public static void main(String[] args) {
        CalendarPull pull = new CalendarPull();
        try {
            ArrayList<String> lines = pull.parseCalendarData();
            
            //Prompt user to write to file
            if (IO.readln("\nWrite to file? (y/n): ").equalsIgnoreCase("y")) {
                Files.write(Path.of("Holiday.txt"), lines, StandardCharsets.UTF_8);
                System.out.println("\nwrote " + lines.size() + " entries to Holiday.txt");
            } else {
                System.out.println("\nNo file written.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
