package model;


//Represents a recurring expense that has a name, category, amount spent (in $),
// a start date, the period it recurs in (weekly, monthly, or yearly), and an ID that is
// unique to other RecurringExpenses.
public class RecurringExpense extends Recurring {



    //Constructor
    //REQUIRES: name is non-empty string, amount is > 0
    //EFFECTS: Creates a recurring expense in a given category with given name, amount spent,
    //         and recurring period. Date is set to date object was initialized, and ID is a
    //         unique positive integer.

    public RecurringExpense(String name, double amount, String period) {
        date = java.time.LocalDate.now();
        this.name = name;
        this.amount = amount;
        this.period = period;
        this.id = nextIncomeOrExpenseID++;

    }

    //Getters
    public String getPeriod() {
        return this.period;

    }







}
