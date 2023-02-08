package model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

//Represents an income category with a name, list of recurring incomes and list of
// single incomes associated with it, and an ID unique to other IncomeCategories.
public class IncomeCategory implements Category {

    private String name;
    private LinkedList<RecurringIncome> recurringIncomes;
    private LinkedList<SingleIncome> singleIncomes;
    private int id;
    private static int nextIncomeCategoryID = 1;

    //Constructor
    //REQUIRES: name is non-empty String
    //EFFECTS: creates an income category with a name, an empty list of recurring
    //incomes, an empty list of singular incomes, and an id that is a unique positive integer.
    public IncomeCategory(String name) {
        this.name = name;
        recurringIncomes = new LinkedList<>();
        singleIncomes = new LinkedList<>();
        id = nextIncomeCategoryID++;

    }

    //GETTERS

    public String getName() {
        return this.name;

    }

    public int getID() {
        return this.id;
    }

    public LinkedList getSingleIncomeList() {
        return this.singleIncomes;
    }

    public LinkedList getRecurringIncomeList() {
        return this.recurringIncomes;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;

    }

    public void addSingleIncome(SingleIncome income) {
        singleIncomes.addFirst(income);

    }

    public void addRecurringIncome(RecurringIncome income) {
        recurringIncomes.addFirst(income);
    }

    //EFFECTS: Searches for SingleIncome in singleIncomes list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeSingleIncome(int id) {
        boolean outcome = false;

        for (SingleIncome income : singleIncomes) {
            if (income.getID() == id) {
                singleIncomes.remove(income);
                outcome = true;
            }
        }

        return outcome;
    }

    //EFFECTS: Searches for RecurringIncome in recurringIncomes list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeRecurringIncome(int id) {
        boolean outcome = false;

        for (RecurringIncome income : recurringIncomes) {
            if (income.getID() == id) {
                recurringIncomes.remove(income);
                outcome = true;
            }
        }

        return outcome;

    }

    @Override

    //EFFECTS: produce total amount of singular incomes (in $) in category
    public double addSingleAmount(LocalDate startDate, LocalDate endDate) {
        double singleAmount = 0;
        for (SingleIncome income : singleIncomes) {
            singleAmount += income.calculate(startDate, endDate);
        }

        return singleAmount;
    }

    public double addRecurringAmount(LocalDate startDate, LocalDate endDate) {
        double recurringAmount = 0;

        for (RecurringIncome income : recurringIncomes) {
            recurringAmount += income.calculate(startDate, endDate);
        }

        return recurringAmount;
    }

    @Override
    //EFFECTS: return total amount of income (in $) in category over given period
    public double addTotalAmount(LocalDate startDate, LocalDate endDate) {
        double totalSingles = this.addSingleAmount(startDate, endDate);
        double totalRecurring = this.addRecurringAmount(startDate, endDate);

        return totalSingles + totalRecurring;

    }


}
