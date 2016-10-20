import java.util.Random;
public class Weather {

    private int temperature; // Temperature in Fahrenheit
    private String kind; // One of three things: "Rainy", "Cloudy", or "Sunny"
    private Random r; // Used to create Random weather conditions
    
    // Instantiate Random data member.
    public Weather() {
	r = new Random();
    }

    // Uses Random data member to set other data members to next day's weather.
    public void nextDay() {
	temperature = Math.abs(r.nextInt()%26 + 75);
	int rannum = Math.abs(r.nextInt()%3);
	switch (rannum) {
	case 0: kind = "Sunny";
	        break;
	case 1: kind = "Cloudy";
	        break;
	case 2: kind = "Rainy";
	        break;
	}
    }

    // Prints current weather conditions.
    public void printWeather() {
	System.out.println("Temperature = " + temperature + ", " + kind);
    }

    // Returns a score of the current weather, ranging from 10 to 40.
    public int scoreWeather() {
	int score = 0;
	if (kind.equals("Cloudy"))
	    score = 10;
	else if (kind.equals("Sunny"))
	    score = 20;
	score += temperature/5;
	return score;
    }
}