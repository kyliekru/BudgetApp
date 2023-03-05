package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.LinkedList;

//abstract class for Categories
public abstract class Category implements Writable {
    protected static int nextCategoryID = 1;

    //EFFECTS: adds total amount of income/expenses in a given period
    public abstract double addTotalAmount(LocalDate startDate, LocalDate endDate);

    //EFFECTS: adds total amount of one-off incomes/expenses in a given period
    public abstract double addSingleAmount(LocalDate startDate, LocalDate endDate);

    //EFFECTS: adds total amount of recurring incomes/expenses in a given period
    public abstract double addRecurringAmount(LocalDate startDate, LocalDate endDate);

    //GETTERS
    public abstract int getID();

    public abstract String getName();

    public abstract LinkedList getSingle();

    public abstract LinkedList getRecurring();

    //SETTER
    public abstract void setName(String name);

    public abstract void setId(int id);

    //REQUIRES: given income/expense is not recurring
    //EFFECTS: add one-off income/expense to one-off list
    public abstract void addSingle(IncomeExpense single);

    //REQUIRES: given income/expense is recurring
    //EFFECTS: add recurring income/expense to recurring list
    public abstract void addRecurring(IncomeExpense recurring);

    //REQUIRES: id matches an id in one-off list
    //EFFECTS: remove income/expense with given ID from one-off list
    public abstract boolean removeSingle(int id);

    //REQUIRES: id matches an id in recurring list
    //EFFECTS: remove income/expense with given ID from recurring list
    public abstract boolean removeRecurring(int id);



}
