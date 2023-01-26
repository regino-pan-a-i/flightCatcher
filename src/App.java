import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {
        //String search = flightSetter();
        Scanner inputScanner = new Scanner(System.in);

        searchFilter(inputScanner);
        

        inputScanner.close();
        
    
    }

    public static void searchFilter(Scanner scanner) throws Exception{
        System.out.println("\nThis program will provide you with flight information\n");
        System.out.println("You can choose to search by:\n" + 
            "1. Flight\n" +
            "2. Ariline\n" +
            "3. City");

        
        System.out.print("Type your search parameter: ");
        String answer = scanner.next();
        
        switch(answer){
            
            case "Flight":
            flightSetter(scanner);

            
            break;

            case "Airline":
            airlineSearch(scanner);
            
            break;

            case "City":
            //updatedLink = citySetter(scanner);

            break;
        }

    }
        
    public static String limitSetter(Scanner scanner){
        System.out.println("This program will provide flight information");
            
        System.out.print("Type a numer for your search: ");
        
        String limit = scanner.next();
        
        
        return "&limit=" + limit;
    }
    
    public static void flightSetter(Scanner scanner) throws Exception{

        System.out.print("\n Type the flight number you woud like to check: ");
        String flightNum = scanner.next();

        String url = linkModifier("&flightIata=" + flightNum);

        URL link = makeConnection(url);

        JSONArray array = readURL(link);

        printFlight(array);



    }

    public static void airlineSearch(Scanner scanner) throws Exception{
            
        System.out.print("\nType the airline you woud like to check: ");
        
        String airline = scanner.next();

        String url = linkModifier("&airlineIata=" + airline);

        URL link = makeConnection(url);

        JSONArray array = readURL(link);

        printAirline(array);
        
    }
    //public static String citySetter(Scanner scanner){}


    public static String linkModifier(String mod){
        String link = "https://app.goflightlabs.com/flights?access_key=";
        String accessKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiNTMxZGMwZWYwNzg4ZDVmOWY3NzkyNmIxYjA0OTQ2MWNjNmM2ZmMzZjIxOGY0YzgxOTcwZDI2OWE5MWMyOTM5ZDE1NTY1NDE4YmQ1N2VlMGIiLCJpYXQiOjE2NjkxNTIzODcsIm5iZiI6MTY2OTE1MjM4NywiZXhwIjoxNzAwNjg4Mzg3LCJzdWIiOiIxODk2OSIsInNjb3BlcyI6W119.aDT-WtwLcaeCCDOopMiIfU-03_IEX3pp6gM_NqlgGVS8VDcznt1LjyKcJkbnwBIuCrXd2Ls3MP31owD9kLGZUw";
        String newLink = link + accessKey + mod;
        
        return(newLink);
    }

    public static URL makeConnection(String givenURL) throws Exception {
        URL url = new URL(givenURL);
        try {
            
            
            // System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } 
            else{
                
                return url;
                
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }

        return url;
    }
    
    public static JSONArray readURL(URL url ){
        JSONArray dataObject = new JSONArray();

        try {

            StringBuilder stringInfo = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            
            while (scanner.hasNext()){
                stringInfo.append(scanner.nextLine());
            }
            
            scanner.close();
            
            //System.out.println(stringInfo);
            
            String formatted = "[" + stringInfo + "]";
            
            JSONParser parser = new JSONParser();
            
            dataObject = (JSONArray) parser.parse(String.valueOf(formatted));
            
            System.out.println(dataObject);

            return dataObject;

            
        } catch(Exception e){
            e.printStackTrace();
        }
        return dataObject;
    }

    public static void printFlight(JSONArray array){
        for (Object obj : array) {
            JSONObject flight = (JSONObject) obj;
            String flightNum = (String) flight.get("flight");
            String airline = (String) flight.get("airline");
            String arrival = (String) flight.get("arrival");
            String departure = (String) flight.get("departure");
            String status = (String) flight.get("status");
    
            System.out.println("Flight Number: " + flightNum);
            System.out.println("Airline: " + airline);
            System.out.println("Arrival: " + arrival);
            System.out.println("Departure: " + departure);
            System.out.println("Status: " + status);
        
        }


    }

    public static void printAirline(JSONArray array){

    }
    
}