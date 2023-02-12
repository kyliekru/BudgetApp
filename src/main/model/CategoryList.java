package model;

import java.time.LocalDate;
import java.util.LinkedList;

//Represents a category list with a label of expense or income (0 = expense), and list of categories
//associated with it
public class CategoryList {

    private int label;
    private LinkedList<Category> categories;

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

    public void addCat(Category cat) {
        categories.addFirst(cat);

    }

    public boolean removeCat(int id) {
        boolean outcome = false;
        for (Category cat : categories) {
            if (cat.getID() == id) {
                categories.remove(cat);
                outcome = true;
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

}
