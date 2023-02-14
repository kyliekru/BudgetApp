package model;

import java.time.LocalDate;

//Abstract class for recurring and single expenses and incomes
public abstract class IncomeOrExpense {

    protected String name;
    protected double amount;
    protected LocalDate date;
    protected int id;
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

    public LocalDate getDate() {
        return this.date;
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
