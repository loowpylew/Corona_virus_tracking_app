"# Corona_virus_tracking_app" 

![image](https://user-images.githubusercontent.com/65728188/151201953-449c4b69-eda5-46f0-a686-14fa09ce250a.png)

This application uses a framework known as spring controller that provides configurations for modern java-based enterprise applications.

To run the spring framework application on your computer, run the main program and then type in search bar: http://localhost:8080/ to simulate the client-server architecture.
So lets move on to the actual application 
Step one: Obtain data about coronavirus cases – Web application that provides information. 
Source: https://github.com/CSSEGISandData/COVID-19

The raw data initially provides locations in respects to longitude/latitude cases on a particular date within a given country/state and province. 
Effectively, when the spring application loads, it will make a request to the csv file URL found within GitHub; parsing all the raw data into the spring life cycle (A “Spring bean” is just a Spring-managed instantiation of a Java class.) to a page which renders the file into web format which makes the data more accessible to its user (Effectively a UI skin for the data). 
All dependencies will be loaded via the zip file download from the spring framework initializer using maven which is an external model that handles project builds provided by intellij for projects written in java. 

Series of spring annotations

From my short experience of using the spring framework, it seems that the annotations automate multiple processes such as recursive calls i.e. when I run the @schedule annotation to recursively fetch data from the GitHub repository every hour of each second day and or passing fields of type class to constructors which are processed by the spring framework (dependency injection).
So it reduces the complexity in respects to providing objects that return a particular functionality from one class to another and so allowed me to make the application in relatively short space of time (coronavirusDataService)  

Structure of application 	

So we have (sum up structure of the application):
All the packages and imports you will see are all dependencies for the most part which were automatically recommended by intellj during the process of writing the application so in the case where I want give the controller the ability to access various functionalities within the ‘coronavirusDataService’ class, it would automatically recommend the import from some directory within the zip file I downloaded provided by the spring framework.

CoronavirusTrackingApplication class:

•	This runs the application within main 
•	Uses @EnableSchedule to grab methods within the spring framework and provides resources to safely retrieve information from the Github repository (proxy acts a intermediary between clients and the server. 
LocationStats class: 
•	Here I have made a series of getters and setters which initialize the field variables and returns the desired values. In this case, the state, country, latest number of cases , difference from the previous the day and so forth. This has been done so that when the controller uses the class to assign a data type to the variable ‘allStats’ within the controller,  .getAllStats returns the data that populates the list every time a record is requested. 

•	The ‘toString’ method formats the output of the results so that we can view the data in the terminal for testing purposes.

ConronaVirusDataService class: 

•	First, we initialize a static variable as it will be unchanging throughout the runtime of the application. The URL provided is that of the GitHub repository which gives us the address of the csv file. 

•	We then create a private list named ‘allstats’ which will be populated every time we run the application. Here we are creating an instance of “ArrayList<>() rather than using a built in array as it allows us to modify what can be stored in the array. And as this will be run multiple times, it makes more sense to do this, than to create a new array, re-initializing it with data, then creating multiple instances of ‘locationStats’ using different variable names as no one variable of the same type can be the same. This would dramatically increase the running time of the program due to multiple users accessing the webpage and would render every instance redundant after every iteration of @PostConstruct. 

•	We then create the function that returns all instances of ‘newStates’ to ‘allStats’. 


•	‘newStats’ is a re-instantiation of the array class which allows multiple users to view the webpage at the same time in the case where coronavirus cases are continuously changing, one user may of accessed the webpage where the results are now no longer true, whilst another user is viewing the exact data at the present moment. This constant updating is done under the under the @postConstruct annotation. In my case, this won’t be as much of an issue as it will update every two days. But if I were to set to update every minute, it would make sense to do so. 
•	We then initialize a variable of type HttpClient which calls the method ‘newHttpClient()’ to create a new client to ping a request to the server. Where a URL allows you to identify the correct protocol. 

•	We then initialize a variable of type HttpRequest which calls the method ‘newBuilder’ where we will convert URL to URI to access the csv file using Builder pattern which allows us to call methods directly after each other, in this case ‘.uri(URI.create(VIRUS_DATA_URL))’ and .build() which is comprised of multiple classes to develop what is known as the product. From my research, I also believe the builder pattern  also undergoes chaining of getters and setters or even exception errors. But I am still yet to fully understand the inner workings. 


•	We then create variable called ‘http response’ which will convert the body of the csv file into a string. 

•	Next, we will create an instance of the StringReader class so that we can pass the body of the csv file to wrap the string response which will allow us to pass it to the parse method below.


•	This is done below by initializing a variable of type ‘CSVrecord’ where the interface ‘Iterable’ will target a specific object within the class via a for loop. The methods used to pass the ‘csvBodyReader’ variable are for formatting purposes of the csv file. 

•	Next, a for loop is created to iterate through each character of the string 


•	We then create a new instance of ‘locationstats’ where we set the fields within its class to specified column headings found within the csv file.

•	We also convert the values into integers where we take into consideration that index’s begin 0 hence we minus 1 for the first two integer conversions. The variable latestcases was made to minus the previous day cases from todays to calculate the difference from the previous day in reported covid cases. 


•	We then add this instance of locationsStats to newstats. 

•	Then as previously mention all stats will get assigned the of newstats for con-currency reasons. 

HomeController class(): 	

•	We extend the class ‘coronavirusDataService; so that all the functionalities previously describes are passed onto the controller where the controller will handle all the other annotations used for us.  
•	We will then initialize ‘all stats’ with allstats that is return from the method found with the coronavirusDataService class.

•	Below this, we create a variable that calculates the sum of total reported cases which will be used to display on the grey banner in the webpage. 


•	The model interface allows us to map the values parsed to a HTML file called ‘home’ 


Improvements
Find a repository that has a real time response to recording coronavirus cases so that users can get a more accurate description of current cases. In the instance of using the @scheduled spring annotation used to run the fetchVirusData method. I have set it to update every two days given the current raw data I am parsing is updated every 2 days. As it is a private repository and is solely dependent on the good will of owners of the repository to update coronavirus cases, using an official institutions repository would be more ideal for more accurate results. 
Remove the console log i.e. System.out.println(locationStats); within the coronaVirusDataService Class to reduce the running time of the program. This was simply used to test whether each new instance of the class returned the values from the raw data. I would have not been able to test for such output before I had setup relevant process to connect to localhost:8080. 
