package model;

import java.time.LocalDate;
import java.util.LinkedList;

public abstract class Category {
    protected static int nextCategoryID = 1;


    public abstract double addTotalAmount(LocalDate startDate, LocalDate endDate);

    public abstract double addSingleAmount(LocalDate startDate, LocalDate endDate);

    public abstract double addRecurringAmount(LocalDate startDate, LocalDate endDate);

    public abstract int getID();

    public abstract String getName();

    public abstract LinkedList getSingle();

    public abstract LinkedList getRecurring();

    public abstract void setName(String name);

    public abstract void addSingle(IncomeOrExpense single);

    public abstract void addRecurring(IncomeOrExpense recurring);

    public abstract boolean removeSingle(int id);

    public abstract boolean removeRecurring(int id);

}
