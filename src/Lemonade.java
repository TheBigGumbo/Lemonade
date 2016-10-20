import java.io.*;
//Doodle doodle doodle
// This class is used to play a simplified game of Lemonade. A Lemonade object
// keeps track of all you need to: your money, amount of sugar, lemons, and
// the price you charge for a cup. This object is instantiated in main, and
// the appropriate method calls are made from main (both static and non-static)
// to create the application.
public class Lemonade {

    final static double PRICE_SUGAR = 1.75; // price of a bag of sugar
    final static double PRICE_LEMON = 4.00; // price of a bag of lemons
    final static double LEMON_PER_CUP = 0.02; // part of bag of lemons in cup
                                              // of lemonade
    final static double SUGAR_PER_CUP = 0.03; // part of bag of sugar in cup
                                              // of lemonade

    private String name; // Store's name of lemonade stand owner
    private double sugar; // bags of sugar owner has
    private double lemons; // bags of lemons owner has
    private BankAccount capital; // keeps track of owner's finances
    private double price; // price of cup of lemonade to be sold

    // Constructs Lemonade object with name passed in as a parameter. The owner
    // is given $50.00 in his/her checking account to start with, and no raw
    // supplies.
    public Lemonade(String name) {
	this.name = name;
	capital = new BankAccount(name,0.0,50.0,0.05);
	sugar = 0.0;
	lemons = 0.0;
	price = .10;
    }

    // This method buys bags number of bags of sugar. If the owner has 
    // insufficient funds to do so, nothing is bought, and false is returned.
    // Otherwise the transaction is carried out, automatically taking money
    // from your Bank Account and adding the bags of sugar. Then true is
    // returned signifying a successful transaction.
    public boolean buySugar(int bags) {

	double totalspent = bags*PRICE_SUGAR;
	// Process case where user has sufficient funds.
	if (totalspent <= capital.totalMoney()) {
	    if (capital.noBounce(totalspent))
		capital.withdraw_chk(totalspent);
	    else {
		// Withdraw money from both checking and savings
		double check = capital.getChecking();
		capital.withdraw_chk(check);
		capital.withdraw_sav(totalspent - check);
	    }
	    // Get sugar...
	    sugar += (double)bags;
	}
	else
	    return false;
	return true;
    }

    
    // Analogous to buying sugar.
    public boolean buyLemon(int bags) {

	double totalspent = bags*PRICE_LEMON;
	if (totalspent <= capital.totalMoney()) {
	    if (capital.noBounce(totalspent))
		capital.withdraw_chk(totalspent);
	    else {
		double check = capital.getChecking();
		capital.withdraw_chk(check);
		capital.withdraw_sav(totalspent - check);
	    }
	    lemons += (double)bags;
	}
	else
	    return false;
	return true;
    }

    // Sets the price of the lemonade to sell.
    public void setPrice(double p) {
	price = p;
    }

    // Executes selling cups number of cups of lemonade. No error checking is
    // done here. Your revenue is increased appropriately, and your sugar
    // and lemons are used accordingly. If you call this method with an garbage
    // parameter, you are not guaranteed a meaningful Lemonade object.
    public void sellCups(int cups) {
	double profit = cups*price;
	// Adds money into checking, and any money over $50 total into savings.
	if (profit + capital.getChecking() <= 50)
	    capital.deposit_chk(profit);
	else {
	    double checking = 50 - capital.getChecking();
	    capital.deposit_chk(checking);
	    capital.deposit_sav(profit - checking);
	}
	// Takes away supplies.
	sugar -= cups*SUGAR_PER_CUP;
	lemons -= cups*LEMON_PER_CUP;
    }

    // Returns the maximum number of cups that could be sold, based on supplies.
    public int maxSell() {
	int lemoncups = (int)(lemons/LEMON_PER_CUP);
	int sugarcups = (int)(sugar/SUGAR_PER_CUP);
	return Math.min(lemoncups,sugarcups);
    }

    // Prints out status of Lemonade stand.
    public void printReport() {
	System.out.println("Status of " + name + "\'s Lemonade Stand.");
	System.out.println("Current Revenue: $" + capital.totalMoney());
	System.out.println("Bags of sugar left:  " + sugar);
	System.out.println("Bags of lemons left: " + lemons);
    }

    // Collects interest on amount stored in savings account.
    public void getInterest() {
	capital.matureAccount();
    }

    public static void main(String args[]) throws IOException {

	BufferedReader stdin = new BufferedReader
	    (new InputStreamReader(System.in));

	// Gets name of player.
	System.out.println("Welcome to the game of Lemonide.");
	System.out.println("Enter your name.");
	String name = stdin.readLine();

	// Creates Lemonade object.
	Lemonade game = new Lemonade(name);
	// Creates Weather object - to be used for producing daily forecasts.
	Weather w = new Weather();
	int day = 1;
	
	// Loops till user wants to stop.
	boolean quit = false;
	while (!quit) {
	    
	    // Let's player buy supplies till they do not want to any more.
	    char ans;
	    do {
		buysupplies(game);
		System.out.println("Are you ready to sell today?");
		ans = stdin.readLine().toLowerCase().charAt(0);
	    } while (ans != 'y');

	    // Calls appropriate weather methods to get and print next day's
	    // weather.
	    System.out.println("Now you are ready to sell your lemonade.");
	    w.nextDay();
	    w.printWeather();
	    
	    // Reads in how much user wants to charge per cup in cents.
	    int cents;
	    do {
		System.out.println("What price would you like to sell today int cents?");
		cents = Integer.parseInt(stdin.readLine());
		if (cents <= 0)
		    System.out.println("Sorry, that is an invalid value, try again.");
	    } while (cents <= 0);

	    // Sets the price of the lemonade accordingly.
	    double price = (double)cents/100.0;
	    game.setPrice(price);

	    // Calculates sales based on static numsales method and supplies,
	    // then executes the sale.
	    int sales = numsales(price,w);
	    sales = Math.min(sales,game.maxSell());
	    game.sellCups(sales);
	    System.out.println("You have sold " + sales + " cups of lemonade.");
	    
	    // Prints out daily statistics and asks player to continue.
	    System.out.println("Statistics Day " + day);
	    game.printReport();
	    System.out.println("Would you like to play another day?");
	    ans = stdin.readLine().toLowerCase().charAt(0);
	    if (ans != 'y')
		quit = true;
	    else
		day++;
	    // Accrues player's interest every 4th day.
	    if (day%4 == 0) 
		game.getInterest();
	}
    }

    // Takes care of buying supplies. Method's one parameter is a Lemonade
    // reference variable. The user is simply asked how many bags of each
    // item they would like. If they have sufficient funds, the supplies are
    // bought.
    public static void buysupplies(Lemonade stand) throws IOException {

	BufferedReader stdin = new BufferedReader
	    (new InputStreamReader(System.in));

	stand.printReport();
	
	System.out.println("How many bags of lemons would you like to buy?");
	int bags = Integer.parseInt(stdin.readLine());
	if (bags < 0)
	    System.out.println("Sorry can not buy a negative number of bags.");
	else {
	    if (stand.buyLemon(bags)) {
		System.out.print("You have successfully bought " + bags);
		System.out.println(" bags of lemons.");
	    }
	    else
		System.out.println("You do not have sufficient funds for this purcahse.");
	    
	}

	System.out.println("How many bags of sugar would you like to buy?");
	bags = Integer.parseInt(stdin.readLine());
	if (bags < 0)
	    System.out.println("Sorry can not buy a negative number of bags.");
	else {
	    if (stand.buySugar(bags)) {
		System.out.print("You have successfully bought " + bags);
		System.out.println(" bags of sugar.");
	    }
	    else
		System.out.println("You do not have sufficient funds for this purcahse.");
	    
	}
    }

    // Calculates the number of possible sales based on the Weather object
    // which is passed in as a parameter, and the price of a cup of lemonade,
    // also passed in by the user. A scoreWeather() method from the Weather
    // class is used. The sales are simply twice this minus the price in
    // cents. So, the optimal revenue is when you set the price equal to
    // the score of the Weather for the day.
    public static int numsales(double price, Weather day) {
	int score = day.scoreWeather();
	int sales = 2*score - (int)(100*price);
	return Math.max(0,sales);
    }
}