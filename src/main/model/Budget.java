package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

import static java.util.Collections.emptyList;

//Represents budget: holds list of income categories and expense categories and has a name
public class Budget implements Writable {
    private CategoryList expenses;
    private CategoryList incomes;
    private String name;

    //CONSTRUCTOR
    public Budget(String name) {
        this.name = name;
        expenses = new CategoryList(0);
        incomes = new CategoryList(1);
    }

    //GETTERS
    public String getName() {
        return this.name;
    }

    public CategoryList getIncomes() {
        return this.incomes;
    }

    public CategoryList getExpenses() {
        return this.expenses;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setIncomes(CategoryList incomeList) {
        incomes = incomeList;
    }

    public void setExpenses(CategoryList expenseList) {
        expenses = expenseList;
    }

    @Override
    //EFFECTS: returns budget as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("catLists", catListsToJson());
        return json;
    }

    //EFFECTS: returns incomes and expenses as a json array
    public JSONArray catListsToJson() {
        JSONArray jsonArray = new JSONArray();
        LinkedList<CategoryList> catLists = new LinkedList<>();
        catLists.add(incomes);
        catLists.add(expenses);

        for (CategoryList catList : catLists) {
            jsonArray.put(catList.toJson());
        }
        return jsonArray;
    }
}
