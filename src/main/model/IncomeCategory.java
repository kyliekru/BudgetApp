package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.LinkedList;

//Represents an income category with a name, list of recurring incomes and list of
// single incomes associated with it, and an ID unique to other IncomeCategories.
public class IncomeCategory extends Category {

    private String name;
    private final LinkedList<RecurringIncome> recurringIncomes;
    private final LinkedList<SingleIncome> singleIncomes;
    private int id;

    //Constructor
    //REQUIRES: name is non-empty String
    //EFFECTS: creates an income category with a name, an empty list of recurring
    //incomes, an empty list of singular incomes, and an id that is a unique positive integer.
    public IncomeCategory(String name) {
        this.name = name;
        recurringIncomes = new LinkedList<>();
        singleIncomes = new LinkedList<>();
        id = nextCategoryID++;

    }

    //GETTERS

    public String getName() {
        return this.name;

    }

    public int getID() {
        return this.id;
    }

    public LinkedList getSingle() {
        return this.singleIncomes;
    }

    public LinkedList getRecurring() {
        return this.recurringIncomes;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;

    }

    public void setId(int id) {
        this.id = id;
    }

    //MODIFIES: singleIncomes
    //EFFECTS: adds one-off income to single incomes list
    public void addSingle(IncomeExpense income) {
        singleIncomes.addLast((SingleIncome) income);
        EventLog.getInstance().logEvent(new Event("Single income added."));

    }

    //MODIFIES: recurringIncomes
    //EFFECTS: adds recurring income to recurring incomes list
    public void addRecurring(IncomeExpense income) {
        recurringIncomes.addLast((RecurringIncome) income);
        EventLog.getInstance().logEvent(new Event("Recurring income added."));
    }


    //EFFECTS: Searches for SingleIncome in singleIncomes list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeSingle(int id) {
        boolean outcome = false;

        for (SingleIncome income : singleIncomes) {
            if (income.getID() == id) {
                singleIncomes.remove(income);
                outcome = true;
            }
        }

        if (outcome) {
            EventLog.getInstance().logEvent(new Event("Single income removed."));
        }

        return outcome;
    }


    //EFFECTS: Searches for RecurringIncome in recurringIncomes list with given ID,
    //         if found it is removed and returns true, else returns false.
    public boolean removeRecurring(int id) {
        boolean outcome = false;

        for (RecurringIncome income : recurringIncomes) {
            if (income.getID() == id) {
                recurringIncomes.remove(income);
                outcome = true;
            }
        }

        if (outcome) {
            EventLog.getInstance().logEvent(new Event("Recurring income removed."));
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

    //EFFECTS: produce total amount of recurring incomes (in $) in category in
    //         a given period
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

    @Override
    //EFFECTS: returns IncomeCategory as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("id", this.id);
        json.put("recurringIncomes", recurIncomesToJson());
        json.put("singleIncomes", singleIncomesToJson());

        return json;

    }

    //EFFECTS: returns recurringIncomes as a json array
    private JSONArray recurIncomesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (RecurringIncome recur : recurringIncomes) {
            jsonArray.put(recur.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns singleIncomes as a json array
    private JSONArray singleIncomesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SingleIncome single : singleIncomes) {
            jsonArray.put(single.toJson());
        }
        return jsonArray;

    }



}
