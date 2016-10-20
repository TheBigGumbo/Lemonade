//Fribble!
public class BankAccount {

    private String name; // Stores bank account owner's name
    private double savings; // stores savings balance
    private double checking; // stores checking balance
    private double interestrate; // stores interest rate as a decimal
    
    // Creates default Bank Acount object - balances are set to 0, 
    // Interest rate set to 5%
    public BankAccount() {
		name = "JohnDoe";
		savings = 0;
		checking = 0;
		interestrate = 0.05;
    }
    
    // Creates Bank Account object with owner's name set to the parameter 
    // passed to the method. Other values are default values.
    public BankAccount(String n) {
		name = n;
		savings = 0;
		checking = 0;
		interestrate = 0.05;
    }
    
    // Creates Bank Account object based on parameters passed to the method.
    public BankAccount(String n, double sav, double check, double ir) {
		name = n;
		savings = sav;
		checking = check;
		interestrate = ir;
    }


    // Withdraws the amount of the parameter from checking account. Next three
    // methods work similarly, either withdrawing or depositing from the
    // checking or savings account.
    public void withdraw_chk(double amount_sub) {
		checking -= amount_sub;
    }
    
    public void deposit_chk(double amount_add) {
		checking += amount_add;
    }
    
    public void withdraw_sav(double amount_sub) {
		savings -= amount_sub;
    }
    
    public void deposit_sav(double amount_add) {
		savings += amount_add;
    }

    // Return's money in savings account.
    public double getSavings() {
		return savings;
    }

    // Return's money in checking account.
    public double getChecking() {
		return checking;
    }

    // Returns the interest rate
    public double getIrate() {
		return interestrate;
    }

    // Changes the interest rate to parameter passed to the method.
    public void changeRate(double rate) {
		interestrate = rate;
    }
    
    // Matures the account 1 year based on current savings and interest rate.
    public void matureAccount() {
		savings = savings*(1+interestrate);
    }

    // Merges two accounts. The account passed in as a parameter is "added"
    // to the account the method is called on. This simply means transferring
    // the savings and checking balances from the account passed in as a
    // parameter. Then this account is zeroed out. The interest rate is the
    // maximum of the two accounts.
    public void mergeAccount(BankAccount account1) {

		savings += account1.savings;
		account1.savings = 0;
		checking += account1.checking;
		account1.checking = 0;
		if (account1.interestrate > interestrate)
	    	interestrate = account1.interestrate;
    }

    // Returns true if its okay to write a check of the amount passed in as a
    // parameter.
    public boolean noBounce(double check) {
		return (check <= checking);
    }

    // Returns true if its okay to take amount amount of money from savings
    // account.
    public boolean drawSavings(double amount) {
		return (amount <= savings);
    }

    // Returns the total money stored in the BankAccount object.
    public double totalMoney() {
		return savings+checking;
    }

    // Prints all account information.
    public void printInfo() {
		System.out.println("Account Information");
		System.out.println("-------------------");
		System.out.println("Name : " + name);
		System.out.println("Savings Balance : $" + savings);
		System.out.println("Checking Balance : $" + checking);
		System.out.println("Interest Rate : " + 100*interestrate + "%");
    }
}
