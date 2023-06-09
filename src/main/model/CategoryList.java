package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.LinkedList;

//Represents a category list with a label of expense or income (0 = expense), and list of categories
//associated with it
public class CategoryList implements Writable {

    private int label;
    private final LinkedList<Category> categories;

    //Constructor
    //EFFECTS: creates a category list with a label of expense or income and
    //an empty list of categories matching that label
    public CategoryList(int label) {
        this.label = label;
        categories = new LinkedList<>();

    }

    //GETTERS
    public int getLabel() {
        return this.label;

    }

    public LinkedList getCatList() {
        return this.categories;

    }

    //SETTERS

    public void setLabel(int label) {
        this.label = label;

    }

    //MODIFIES: this
    //EFFECTS: add cat to Category list
    public void addCat(Category cat) {
        categories.addLast(cat);
        if (label == 0) {
            EventLog.getInstance().logEvent(new Event("Expense category added."));
        } else {
            EventLog.getInstance().logEvent(new Event("Income category added."));
        }

    }

    //REQUIRES: id matches an id in Category list
    //MODIFIES: this
    //EFFECTS: removes category from category list
    public boolean removeCat(int id) {
        boolean outcome = false;
        for (Category cat : categories) {
            if (cat.getID() == id) {
                categories.remove(cat);
                outcome = true;
            }
        }
        if (outcome) {
            if (label == 0) {
                EventLog.getInstance().logEvent(new Event("Expense category deleted."));
            } else {
                EventLog.getInstance().logEvent(new Event("Income category deleted."));
            }
        }
        return outcome;

    }

    //EFFECTS: produce total amount (in $) of income/expenses in given period
    public double addTotalAmount(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        for (Category cat : categories) {
            amount += cat.addTotalAmount(startDate, endDate);
        }
        return amount;
    }

    @Override
    //EFFECTS: returns CategoryList as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("cats", categoriesToJson());
        return json;
    }

    //EFFECTS returns categories as json array
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category cat : categories) {
            jsonArray.put(cat.toJson());
        }

        return jsonArray;
    }

}
