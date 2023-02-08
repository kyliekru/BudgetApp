package model;

import java.time.LocalDate;

public abstract class AddFinancials {

    String name;
    double amount;
    LocalDate date;
    int id;


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

    public void setName(String name) {
        this.name = name;

    }


    public void setAmount(double amount) {
        this.amount = amount;

    }

    abstract double calculate(LocalDate startDate, LocalDate date);


}
