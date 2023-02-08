package model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

//Represents an expense category that has a name, list of recurring expenses amd
// list of single expenses associated with it, and an ID unique to other ExpenseCategories.
public class ExpenseCategory implements Category {

    private String name;
    private LinkedList<RecurringExpense> recurringExpenses;
    private LinkedList<SingleExpense> singleExpenses;
    private int id;
    private static int nextExpenseCategoryID = 1;

    //Constructor
    //REQUIRES: name is non-empty string
    //EFFECTS: creates a category with a given name, empty single expense list, empty
    // recurring expense list, and an ID that is a unique positive integer.
    public ExpenseCategory(String name) {
        this.name = name;
        recurringExpenses = new LinkedList<>();
        singleExpenses = new LinkedList<>();
        id = nextExpenseCategoryID++;

    }

    //GETTERS

    public String getName() {
        return this.name;

    }

    public int getID() {
        return this.id;
    }

    public LinkedList getSingleExpenseList() {
        return this.singleExpenses;

    }

    public LinkedList getRecurringExpenseList() {
        return this.recurringExpenses;
    }


    //SETTERS

    public void setName(String name) {
        this.name = name;

    }

    public void addSingleExpense(SingleExpense expense) {
        singleExpenses.addFirst(expense);

    }

    public void addRecurringExpense(RecurringExpense expense) {
        recurringExpenses.addFirst(expense);

    }

    //EFFECTS: Searches for SingleExpense in singleExpenses list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeSingleExpense(int id) {
        boolean outcome = false;

        for (SingleExpense expense : singleExpenses) {
            if (expense.getID() == id) {
                singleExpenses.remove(expense);
                outcome = true;
            }
        }

        return outcome;

    }

    //EFFECTS: Searches for RecurringExpense in recurringExpenses list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeRecurringExpense(int id) {
        boolean outcome = false;

        for (RecurringExpense expense : recurringExpenses) {
            if (expense.getID() == id) {
                recurringExpenses.remove(expense);
                outcome = true;
            }
        }

        return outcome;

    }

    @Override
    //EFFECTS: return total amount of single expenses (in $) in category over given period
    public double addSingleAmount(LocalDate startDate, LocalDate endDate) {
        double singleAmount = 0;
        for (SingleExpense expense : singleExpenses) {
            singleAmount += expense.calculate(startDate, endDate);
        }

        return singleAmount;
    }

    @Override
    //EFFECTS: return total amount of recurring expenses (in $) in category over given period
    public double addRecurringAmount(LocalDate startDate, LocalDate endDate) {
        double recurringAmount = 0;

        for (RecurringExpense expense : recurringExpenses) {
            recurringAmount += expense.calculate(startDate, endDate);

        }

        return recurringAmount;
    }

    @Override
    //EFFECTS: produce total amount of expenses (in $) in Category over given period
    public double addTotalAmount(LocalDate startDate, LocalDate endDate) {
        double totalRecurring = this.addRecurringAmount(startDate, endDate);
        double totalSingles = this.addSingleAmount(startDate, endDate);

        return totalRecurring + totalSingles;
    }


}
