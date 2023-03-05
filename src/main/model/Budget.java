package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

import static java.util.Collections.emptyList;

//Represents budget: holds list of income categories and expense categories
public class Budget implements Writable {
    private CategoryList incomeCategories;
    private CategoryList expenseCategories;
    private String name;

    //CONSTRUCTOR
    public Budget(String name) {
        this.name = name;
        incomeCategories = new CategoryList(1);
        expenseCategories = new CategoryList(0);
    }

    public String getName() {
        return this.name;
    }

    public CategoryList getIncomes() {
        return this.incomeCategories;
    }

    public CategoryList getExpenses() {
        return this.expenseCategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIncomes(CategoryList incomeList) {
        incomeCategories = incomeList;
    }

    public void setExpenses(CategoryList expenseList) {
        expenseCategories = expenseList;
    }

    @Override
    //TODO: toJson budget
    public JSONObject toJson() {
        return null;
    }
}
