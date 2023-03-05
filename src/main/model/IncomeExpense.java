package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;

//Abstract class for recurring and single expenses and incomes
public abstract class IncomeExpense implements Writable {

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

    public void setId(int id) {
        this.id = id;
    }



    public void setAmount(double amount) {
        this.amount = amount;

    }

    //EFFECTS: calculate how much a given income/expense amounted to in a given period
    abstract double calculate(LocalDate startDate, LocalDate date);

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("amount", this.amount);
        json.put("id", this.id);
        json.put("year", this.date.getYear());
        json.put("month", this.date.getMonth());
        json.put("day", this.date.getDayOfMonth());

        return json;
    }

}
