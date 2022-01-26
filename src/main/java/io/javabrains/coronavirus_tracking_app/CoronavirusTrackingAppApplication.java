package io.javabrains.coronavirus_tracking_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication //deals with various configurations within spring
@EnableScheduling /* Tells spring to grab methods that have the schedule within a proxy
                     (provides resources for the retrieval of information from client/server
                     interactions) to call the specified frequency of the update, in our case every 2 days */
public class CoronavirusTrackingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirusTrackingAppApplication.class, args);

	}

}
