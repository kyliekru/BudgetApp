package model;

import java.time.LocalDate;

public abstract class IncomeOrExpense {

    String name;
    double amount;
    LocalDate date;
    int id;
    protected static int nextIncomeOrExpenseID = 1;


    //Getters
    public String getName() {
        return this.name;

    }

    public int getID() {
        return this.id;
    }


    public double getAmount() {
        return this.amount;

    }


    //Setters
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;

    }


    public void setAmount(double amount) {
        this.amount = amount;

    }

    abstract double calculate(LocalDate startDate, LocalDate date);


}
