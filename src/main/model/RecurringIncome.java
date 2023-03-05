package model;

//Represents a recurring income with a name, Category, amount earned (in $), start date, period it
//recurs in (bi-weekly, monthly, or yearly), and an ID unique to other RecurringIncomes.
public class RecurringIncome extends Recurring {

    private String period;

    //Constructor
    //REQUIRES: name is non-empty string and amount > 0
    //EFFECTS: creates a recurring income with a given name, given amount earned each period,
    // a given category, and a given period to recur in. Date is set to date object was initialized,
    // and ID is a unique positive integer.

    public RecurringIncome(String name, double amount, String period) {
        this.date = java.time.LocalDate.now();
        this.name = name;
        this.amount = amount;
        this.period = period;
        this.id = nextIncomeOrExpenseID++;

    }


    //GETTERS


    public String getPeriod() {
        return this.period;

    }

    //SETTER




}
