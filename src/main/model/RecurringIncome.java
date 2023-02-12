package model;

//Represents a recurring income with a name, Category, amount earned (in $), start date, period it
//recurs in (bi-weekly, monthly, or yearly), and an ID unique to other RecurringIncomes.
public class RecurringIncome extends RecurringFinancials {

    private String period;
    private Category cat;

    //Constructor
    //REQUIRES: name is non-empty string and amount > 0
    //EFFECTS: creates a recurring income with a given name, given amount earned each period,
    // a given category, and a given period to recur in. Date is set to date object was initialized,
    // and ID is a unique positive integer.

    public RecurringIncome(String name, double amount, Category cat, String period) {
        this.date = java.time.LocalDate.now();
        this.name = name;
        this.amount = amount;
        this.cat = cat;
        this.period = period;
        this.id = nextIncomeOrExpenseID++;

    }



    public Category getCat() {
        return this.cat;

    }

    public String getPeriod() {
        return this.period;

    }

    //Setters


    public void setCat(IncomeCategory cat) {
        this.cat = cat;

    }


}
