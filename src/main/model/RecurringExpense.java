package model;


//Represents a recurring expense that has a name, category, amount spent (in $),
// a start date, the period it recurs in (weekly, monthly, or yearly), and an ID that is
// unique to other RecurringExpenses.
public class RecurringExpense extends RecurringFinancials {

    private Category cat;
    private String period;

    //Constructor
    //REQUIRES: name is non-empty string, amount is > 0
    //EFFECTS: Creates a recurring expense in a given category with given name, amount spent,
    //         and recurring period. Date is set to date object was initialized, and ID is a
    //         unique positive integer.

    public RecurringExpense(String name, Category cat, double amount, String period) {
        date = java.time.LocalDate.now();
        this.name = name;
        this.cat = cat;
        this.amount = amount;
        this.period = period;
        this.id = nextIncomeOrExpenseID++;

    }

    //Getters
    public Category getCat() {
        return this.cat;

    }

    public String getPeriod() {
        return this.period;

    }


    //Setters
    public void setCat(ExpenseCategory cat) {
        this.cat = cat;

    }





}
