package model;

import java.time.LocalDate;



// Represents an expense that has a name, Category, amount spent (in $),
// a date, and an ID that is unique to other SingleIncomes;
public class SingleIncome extends IncomeExpense {

    //Constructor
    //REQUIRES: name is non-empty string,
    //          amount is >0
    //EFFECTS: Creates an income in a given category with given name and
    //         amount earned. Date is set to day income was created and ID is a positive
    //         unique integer.
    public SingleIncome(String name, double amount) {
        date = java.time.LocalDate.now();
        this.name = name;
        this.amount = amount;
        this.id = nextIncomeOrExpenseID++;

    }



    @Override

    //EFFECTS: return amount if date is in given period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        if (this.date.isAfter(startDate) && this.date.isBefore(endDate)) {
            amount += this.amount;
        }
        return amount;
    }


}
