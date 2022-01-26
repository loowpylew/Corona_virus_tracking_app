package io.javabrains.coronavirus_tracking_app.services;

import io.javabrains.coronavirus_tracking_app.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/* So when the application starts, we need to activate spring services
   to run the method sitting within the class "CoronaVirusDataService".
 */
@Service // Creates instance of class
public class CoronaVirusDataService {
    // makes call to URL and then fetches data.

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    /* Private list of location stats
       (Allows me to populate the list everytime I request records from
       the Github repo)
     */
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats(){
        return allStats;
    }
    //How to update the data given coronavirus cases are continuous uns fetchVirusData on a regular basis)
    @Scheduled(cron = "* * 1 2 * *" ) // Second, minute, hour, day, month, year (first hour of everyday)

    // Once you have constructed instance of this service, execute method
    @PostConstruct()
    public void fetchVirusData() throws IOException, InterruptedException { // Exception error implemented when request fails.
        List<LocationStats> newStats = new ArrayList<>(); /* This has been re-instantiated because of con-currency issues.
                                                             As there are multiple users using the service, we don't want there to be
                                                             error exceptions when constructing the table. This is to prevent when
                                                             a user requests a page, then another user requests a page with the updated
                                                             records. It allows user1 to view the un-updated page until he/she decides
                                                             to refresh. Then allStats will be populated with newStats thus prevent issues
                                                             with the viewing coronaviruses cases*/

        HttpClient client = HttpClient.newHttpClient(); // Creating instance of class (Creating a new client)
        HttpRequest request = HttpRequest.newBuilder() /* Allows us to use builder pattern
                                    (builder pattern is implemented by a class which has several methods to configure the product)
                                    calling methods directly after each other. */
                .uri(URI.create(VIRUS_DATA_URL)) // converts string VIRUS_DATA_URL to a URI(Uniform Resource Locator)
                .build();
        HttpResponse<String> httpsResponse =  client.send(request, HttpResponse.BodyHandlers.ofString()); // takes the body URI and returns as a String.
       /* System.out.println(httpsResponse.body());    Prints out response as a result of converting the body of the request into a string.
                                                     where the body consists of a single file and is defined by two header files:
                                                     - Content - Type
                                                     - Content - Length
                                                     */
        /* Some CSV files define header names (i.e. location, longitude/latitude, cases on a particular) in their first record.
           If configured, Apache Commons CSV can parse the header names from the first record.
         */

        StringReader csvBodyReader = new StringReader(httpsResponse.body()); // Instance of reader that will parse the string

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        // for loop is read as for each element record in records.
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1))); /* Needs to be converted to an integer as input is string
                                                                                         '-1' used to account for indexing begins at 0*/
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int previousDayCases =  Integer.parseInt(record.get(record.size() - 2)); // -2 takes away from current day to find out no. of cases the day before.
            locationStats.setDiffFromPrevDay(latestCases - previousDayCases);
            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats = newStats; // Allocates all instances of newStats to allStats
    }
}
