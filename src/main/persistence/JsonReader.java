package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.*;

//Represents a reader that reads budget from JSON data stored in file
//SOURCE: JsonSerializationDemo
public class JsonReader {
    private String source;

    //EFFECTS: construct reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads budget from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Budget budget = new Budget(name);

        JSONArray array = jsonObject.getJSONArray("catLists");
        for (Object json : array) {
            JSONObject nextCatList = (JSONObject) json;
            addCatList(budget, nextCatList);
        }

        return budget;

    }

    //MODIFIES: budget
    //EFFECTS: parses category lists from JSON object and adds them to budget
    private void addCatList(Budget budget, JSONObject jsonObject) {
        int label = jsonObject.getInt("label");
        if (label == 1) {
            CategoryList incomeCats = new CategoryList(label);
            addIncomeCatList(incomeCats, jsonObject);
            budget.setIncomes(incomeCats);
        } else {
            CategoryList expenseCats = new CategoryList(label);
            addExpenseCatList(expenseCats, jsonObject);
            budget.setExpenses(expenseCats);
        }

    }

    private void addIncomeCatList(CategoryList list, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("cats");
        for (Object json : array) {
            JSONObject nextCat = (JSONObject) json;
            addIncomeCats(list, nextCat);
        }

    }

    private void addExpenseCatList(CategoryList list, JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("cats");
        for (Object json : array) {
            JSONObject nextCat = (JSONObject) json;
            addExpenseCats(list, nextCat);
        }

    }



    //MODIFIES: budget
    //EFFECTS: parses categories from JSON object and adds them to budget
    private void addIncomeCats(CategoryList cats, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");

        IncomeCategory cat = new IncomeCategory(name);
        cat.setId(id);
        addRecurringIncomeList(cat, jsonObject);
        addSingleIncomeList(cat, jsonObject);
        cats.addCat(cat);

    }

    //MODIFIES: budget
    //EFFECTS: parses categories from JSON object and adds them to budget
    private void addExpenseCats(CategoryList cats, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");

        ExpenseCategory cat = new ExpenseCategory(name);
        cat.setId(id);
        addRecurringExpenseList(cat, jsonObject);
        addSingleExpenseList(cat, jsonObject);
        cats.addCat(cat);

    }

    //MODIFIES: budget
    //EFFECTS: parses recurring income list and adds it to budget
    private void addRecurringIncomeList(Category cat, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recurringIncomes");

        for (Object json : jsonArray) {
            JSONObject nextRecur = (JSONObject) json;
            addRecurringIncome(cat, nextRecur);
        }

    }

    //MODIFIES: budget
    //EFFECTS: parses recurring expense list and adds it to budget
    private void addRecurringExpenseList(Category cat, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recurringExpenses");

        for (Object json : jsonArray) {
            JSONObject nextRecur = (JSONObject) json;
            addRecurringExpense(cat, nextRecur);
        }

    }

    //MODIFIES: budget
    //EFFECTS: parses single income list and adds it to budget
    private void addSingleIncomeList(Category cat, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("singleIncomes");

        for (Object json : jsonArray) {
            JSONObject nextSingle = (JSONObject) json;
            addSingleIncome(cat, nextSingle);
        }

    }

    //MODIFIES: budget
    //EFFECTS: parses single expense list and adds it to budget
    private void addSingleExpenseList(Category cat, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("singleExpenses");

        for (Object json : jsonArray) {
            JSONObject nextSingle = (JSONObject) json;
            addSingleExpense(cat, nextSingle);
        }

    }

    //MODIFIES: budget
    //EFFECTS: parses recurring income and adds it to budget
    private void addRecurringIncome(Category cat, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double amount = jsonObject.getDouble("amount");
        String period = jsonObject.getString("period");
        int id = jsonObject.getInt("id");
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");

        LocalDate date = LocalDate.of(year, month, day);
        RecurringIncome income = new RecurringIncome(name, amount, period);
        income.setDate(date);
        income.setId(id);

        cat.addRecurring(income);

    }

    //MODIFIES: budget
    //EFFECTS: parses recurring expense and adds it to budget
    private void addRecurringExpense(Category cat, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double amount = jsonObject.getDouble("amount");
        String period = jsonObject.getString("period");
        int id = jsonObject.getInt("id");
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");

        LocalDate date = LocalDate.of(year, month, day);
        RecurringExpense expense = new RecurringExpense(name, amount, period);
        expense.setDate(date);
        expense.setId(id);

        cat.addRecurring(expense);

    }

    //MODIFIES: budget
    //EFFECTS: parses single income and adds it to budget
    private void addSingleIncome(Category cat, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double amount = jsonObject.getDouble("amount");
        int id = jsonObject.getInt("id");
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");

        LocalDate date = LocalDate.of(year, month, day);
        SingleIncome income = new SingleIncome(name, amount);
        income.setDate(date);
        income.setId(id);

        cat.addSingle(income);

    }

    //MODIFIES: budget
    //EFFECTS: parses single expense and adds it to budget
    private void addSingleExpense(Category cat, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double amount = jsonObject.getDouble("amount");
        int id = jsonObject.getInt("id");
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");

        LocalDate date = LocalDate.of(year, month, day);
        SingleExpense expense = new SingleExpense(name, amount);
        expense.setDate(date);
        expense.setId(id);

        cat.addSingle(expense);

    }
}
