package model;

import java.time.LocalDate;


// Represents an expense that has a name, Category, amount spent (in $),
// a date, and an ID unique to other SingleExpenses.
public class SingleExpense extends IncomeOrExpense {

    private Category cat;


    //Constructor
    //REQUIRES: name is non-empty string,
    //          amount is >0
    //EFFECTS: Creates an expense in a given category with given name and
    //         amount spent. Date is set to day expense was created and ID is a
    //         unique positive integer.
    public SingleExpense(String name, Category cat, double amount) {

        date = java.time.LocalDate.now();
        this.name = name;
        this.cat = cat;
        this.amount = amount;
        this.id = nextIncomeOrExpenseID++;
    }


    //GETTER
    public Category getCat() {
        return this.cat;

    }




    //SETTER
    public void setCat(ExpenseCategory cat) {
        this.cat = cat;

    }


    @Override
    //EFFECTS: return amount if date is in given period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        if (this.date.isAfter(startDate) && this.date.isBefore(endDate)) {
            amount = this.amount;
        }
        return amount;
    }


}
