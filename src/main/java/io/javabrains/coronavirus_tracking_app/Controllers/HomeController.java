package io.javabrains.coronavirus_tracking_app.Controllers;


import io.javabrains.coronavirus_tracking_app.models.LocationStats;
import io.javabrains.coronavirus_tracking_app.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller //allows all the annotations to be handled by the spring framework
public class HomeController {

    @Autowired // gives access to controller to service
    CoronaVirusDataService coronaVirusDataService; /* extends object that will have all the relevant processes
                                                      required to run the relevant functionalities */
    @GetMapping("/") // Base directory (In this case, Home). Allow us to map HTTP request to handlers managed by spring
    public String Home(Model model){ // Allows to add attributes to model
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        /*Takes list of objects, converting into a stream, then mapping to an integer, adds it up
          where each object matches to each value returned by getLatestTotalValue() then returns the sum().*/
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        model.addAttribute("LocationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);

        return "Home";
    }

    /*@GetMapping("/static.styles/js/functions.js")
    public String getStyledPage(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        /*Takes list of objects, converting into a stream, then mapping to an integer, adds it up
          where each object matches to each value returned by getLatestTotalValue() then returns the sum().*;
        model.addAttribute("ll", allStats);
        return "functions.js";
    }
    */



}
