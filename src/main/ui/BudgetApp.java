package ui;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.InvalidValue;
import ui.exceptions.WrongID;
import model.*;

import static java.lang.Math.*;

//Budget Application
public class BudgetApp {
    private static final String JSON_STORE = "./data/budget.json";
    private Scanner input;
    private Category currentCategory;
    private ExpenseCategory currExpenseCat;
    private IncomeCategory currIncomeCat;
    private Budget budget;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    private static final DecimalFormat df = new DecimalFormat("0.00");



    //EFFECTS: Runs the Budget Application
    public BudgetApp() {
        runBudget();

    }

    //EFFECTS: initializes Budget App with income and expense Category Lists
    //         and an input scanner.
    public void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        budget = new Budget("unnamed");


    }

    //MODIFIES: this
    //EFFECTS: processes user input
    //SOURCE: TellerApp example from EdX
    private void runBudget() {
        boolean mainRunning = true;
        String command;

        init();

        while (mainRunning) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("x")) {
                askToSave();
                mainRunning = false;
            } else {
                processDisplayMenu(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    //EFFECTS: display menu options to user
    public void displayMenu() {
        System.out.println("\nWelcome to EasyBudget!");
        System.out.println("\nSelect an option:");
        System.out.println(CYAN + "\tc -> create new budget" + RESET);
        System.out.println(CYAN + "\tv -> view budget" + RESET);
        System.out.println(CYAN + "\te -> edit budget" + RESET);
        System.out.println();
        System.out.println("\tl -> load budget from file");
        System.out.println("\ts -> save budget to file");
        System.out.println();
        System.out.println(RED + "\tx -> close application" + RESET);

    }

    //MODIFIES: this
    //EFFECTS: process user command from display menu
    public void processDisplayMenu(String option) {
        switch (option) {
            case "v":
                budgetIsMade();
                break;
            case "c":
                selectBudgetOption();
                break;
            case "e":
                categoryMenu();
                break;
            case "l":
                loadBudget();
                break;
            case "s":
                saveBudget();
                break;

        }

    }

    //EFFECTS: asks user if they want to save; if yes save file
    public void askToSave() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            System.out.println("\nSave file?");
            System.out.println(GREEN + "\ts -> save" + RESET);
            System.out.println(RED + "\tx -> quit without saving" + RESET);
            command = input.next().toLowerCase();
            if (command.equals("x")) {
                keepGoing = false;
            } else if (command.equals("s")) {
                saveBudget();
                keepGoing = false;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: sets budget name to name given by user
    public void getName() {
        System.out.println("\nEnter budget name:");
        String name = input.next().toUpperCase();
        budget.setName(name);
    }

    //MODIFIES: this
    //EFFECTS: saves budget to file
    public void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            System.out.println(GREEN + "Saved " + budget.getName() + " to " + JSON_STORE + RESET);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads budget from file
    public void loadBudget() {
        try {
            budget = jsonReader.read();
            System.out.println("Loaded " + budget.getName() + " from " + JSON_STORE);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



    //EFFECTS: Checks if user has a budget made, if so gives option to view, else
    //          redirects to createBudget.
    public void budgetIsMade() {

        if (budget.getIncomes().getCatList().isEmpty() && budget.getExpenses().getCatList().isEmpty()) {
            System.out.println("No budget created! Redirecting to budget creation screen...");
            selectBudgetOption();
        } else {
            viewOverallBudgetMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user input for budget viewing options
    public void selectBudgetOption() {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            if (budget.getIncomes().getCatList().isEmpty() && budget.getExpenses().getCatList().isEmpty()) {
                System.out.println("Time to make a budget!");
                getName();
            }
            displayBudgetMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processBudgetMenu(command);
            }
        }
        System.out.println("Returning to main menu...");
    }

    //EFFECTS: Displays default category options and asks for input
    public void displayBudgetMenu() {
        System.out.println("\nWould you like to view the default categories or make your own?");
        System.out.println("\tv -> view default");
        System.out.println(CYAN + "\tc -> create custom categories" + RESET);
        System.out.println(RED + "\tq -> return to main menu" + RESET);
    }

    //MODIFIES: this
    //EFFECTS: processes selected option from Budget menu
    public void processBudgetMenu(String option) {
        switch (option) {
            case "v":
                viewDefaultCategories();
                break;
            case "c":
                createCategoryMenu();
                break;
        }

    }

    //EFFECTS: displays default categories
    public void viewDefaultCategories() {
        String command;
        System.out.println("\nThe default categories are:");
        System.out.println("\nIncome Categories:");
        System.out.println("\t-Salary");
        System.out.println("\t-Odd Jobs");
        System.out.println("\nExpense Categories:");
        System.out.println("\t-Bills");
        System.out.println("\t-Food");
        System.out.println("\t-Entertainment");

        System.out.println("\nWould you like to use these or create your own?");
        System.out.println("\ty -> use default categories");
        System.out.println("\tn -> create custom categories");
        command = input.next();
        switch (command) {
            case "y":
                useDefaultCategories();
                break;
            case "n":
                createCategoryMenu();
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: create default categories and add them to appropriate CategoryList
    public void useDefaultCategories() {
        IncomeCategory salary = new IncomeCategory("salary");
        IncomeCategory oddJobs = new IncomeCategory("odd jobs");

        ExpenseCategory bills = new ExpenseCategory("Bills");
        ExpenseCategory food = new ExpenseCategory("Food");
        ExpenseCategory fun = new ExpenseCategory("Entertainment");

        budget.getIncomes().addCat(salary);
        budget.getIncomes().addCat(oddJobs);

        budget.getExpenses().addCat(bills);
        budget.getExpenses().addCat(food);
        budget.getExpenses().addCat(fun);

        categoryMenu();
    }

    //MODIFIES: this
    //EFFECTS: Displays income and expenses for each category
    public void displayIndividualCategoryAmounts(LocalDate startDate, LocalDate endDate, double totalI, double totalE) {
        LinkedList<IncomeCategory> incomeList = budget.getIncomes().getCatList();
        LinkedList<ExpenseCategory> expenseList = budget.getExpenses().getCatList();
        String name;
        double amount;
        double percentage;
        System.out.println("\nIndividual Incomes:");
        for (IncomeCategory income : incomeList) {
            name = income.getName().toUpperCase();
            amount = income.addTotalAmount(startDate, endDate);
            percentage = round((amount / totalI) * 100);
            System.out.println("\t" + name + " $" + df.format(amount) + " ~" + percentage + "% of income~");

        }

        System.out.println("\nIndividual Expenses:");
        for (ExpenseCategory expense : expenseList) {
            name = expense.getName().toUpperCase();
            amount = expense.addTotalAmount(startDate, endDate);
            percentage = round((amount / totalE) * 100);
            System.out.println("\t" + name + " $" + df.format(amount) + " ~" + percentage + "% of expenses~");
        }
    }

    //MODIFIES: this
    //EFFECTS: print budget overview from startDate to endDate
    public void processBudget(LocalDate startDate, LocalDate endDate) {

        double incomeTotal = budget.getIncomes().addTotalAmount(startDate, endDate);
        double expenseTotal = budget.getExpenses().addTotalAmount(startDate, endDate);
        double savingsCutoff = incomeTotal * 0.2;
        double savings = incomeTotal - expenseTotal;


        displayIndividualCategoryAmounts(startDate, endDate, incomeTotal, expenseTotal);

        getMessageForBudgetSavings(incomeTotal, expenseTotal, savings, savingsCutoff, startDate, endDate);

    }

    //EFFECTS: Prints budget overview message for a custom period
    public void getMessageForBudgetSavings(double inc, double expen, double save, double cutOff, LocalDate start,
                                           LocalDate endDate) {
        System.out.println("You made " + GREEN + "$" + inc + RESET + " from " + start + " to " + endDate + " and spent "
                + RED + "$" + expen + RESET);
        if (save < 0) {
            System.out.println("Overall you overspent by" + RED + "-$" + abs(save) + RESET + ".");
        } else if (save >= 0 && save <= cutOff) {
            System.out.println("Overall you saved " + YELLOW + "$" + save + RESET + ". Almost on track!");
        } else {
            System.out.println("Overall you saved " + GREEN + "$" + save + RESET + ". Great work!");
        }
    }

    //MODIFIES: this
    //EFFECTS: prints overview of budget based on income, expenses, savings, and msg
    public void printBudgetOverview(double income, double expens, double savin, String msg, boolean curr, String when) {
        String lostOrSaved = "saved";
        String stringSavings;
        String stringE;
        String stringI;
        String colour;
        String lastOr = "last";
        if (curr) {
            lastOr = "this";
        }
        if (savin < 0) {
            lostOrSaved = "lost";
            colour = RED;
            stringSavings = df.format(savin * -1);
        } else {
            colour = GREEN;
            stringSavings = df.format(savin);
        }
        stringE = df.format(expens);
        stringI = df.format(income);

        System.out.println("You made " + GREEN + "$" + stringI + RESET + " " + lastOr + " " + when + " and spent "
                + RED + "$" + stringE + RESET + ".");
        System.out.println("Overall you " + colour + lostOrSaved + " $" + stringSavings + RESET + "! " + msg);

    }

    //MODIFIES: this
    //EFFECTS: return message for viewing budget based on savings and savingsCutoff
    public String getMessageForSavings(double savings, double savingsCutoff, String period, String when) {
        String msg;
        if (savings < 0) {
            msg = "You overspent " + when + " " + period + "! Slow down!";
        } else if (savings > 0 && savings <= savingsCutoff) {
            msg = "Good job saving " + when + " " + period + ", but there's still room to improve!";
        } else {
            msg = "Great job saving " + when + " " + period + "!";
        }
        return msg;
    }



    //MODIFIES: this
    //EFFECTS: displays budget in the current partial period
    public void viewCurrentBudget(String period) {
        LocalDate currentDate = java.time.LocalDate.now();
        switch (period) {
            case "weekly":
                processCurrentWeek(currentDate);
                break;
            case "yearly":
                processCurrentYear(currentDate);
                break;
            case "monthly":
                processCurrentMonth(currentDate);
                break;
        }

    }

    //MODIFIES: this
    //EFFECTS: displays income/expenses for current week
    public void processCurrentWeek(LocalDate date) {
        LocalDate startDate = date.with(DayOfWeek.SUNDAY).minusWeeks(1);
        double incomeTotal = budget.getIncomes().addTotalAmount(startDate, date);
        double expenseTotal = budget.getExpenses().addTotalAmount(startDate, date);
        double savingsCutoff = (incomeTotal * 0.20);
        double savings = incomeTotal - expenseTotal;
        String msg;

        msg = getMessageForSavings(savings, savingsCutoff, "week", "this");
        displayIndividualCategoryAmounts(startDate, date, incomeTotal, expenseTotal);
        printBudgetOverview(incomeTotal, expenseTotal, savings, msg, true, "week");
    }

    //MODIFIES: this
    //EFFECTS: displays income/expenses for current month
    public void processCurrentMonth(LocalDate date) {
        LocalDate startDate = date.withDayOfMonth(1);
        double incomeTotal = budget.getIncomes().addTotalAmount(startDate, date);
        double expenseTotal = budget.getExpenses().addTotalAmount(startDate, date);
        double savingsCutoff = (incomeTotal * 0.20);
        double savings = incomeTotal - expenseTotal;
        String msg;

        msg = getMessageForSavings(savings, savingsCutoff, "month", "this");
        displayIndividualCategoryAmounts(startDate, date, incomeTotal, expenseTotal);

        printBudgetOverview(incomeTotal, expenseTotal, savings, msg, true, "month");

    }

    //MODIFIES: this
    //EFFECTS: displays total income/expenses for current year
    public void processCurrentYear(LocalDate date) {
        LocalDate startDate = date.withDayOfYear(1);
        double incomeTotal = budget.getIncomes().addTotalAmount(startDate, date);
        double expenseTotal = budget.getExpenses().addTotalAmount(startDate, date);
        double savingsCutoff = (incomeTotal * 0.20);
        double savings = incomeTotal - expenseTotal;
        String msg = getMessageForSavings(savings, savingsCutoff, "year", "this");
        displayIndividualCategoryAmounts(startDate, date, incomeTotal, expenseTotal);

        printBudgetOverview(incomeTotal, expenseTotal, savings, msg, true, "year");

    }

    //MODIFIES: this
    //EFFECTS: displays budget options and processes user input
    public void viewOverallBudgetMenu() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Would you like to view a custom time frame?");
            System.out.println("\tc -> custom period");
            System.out.println("\td -> view default choices");
            System.out.println("\tq -> return to menu");
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            }
            switch (command) {
                case "c":
                    processBudget(askStartDate(), askEndDate());
                    break;
                case "d":
                    viewCurrentBudget(askBudgetPeriod());
                    break;
            }
        }
    }

    //EFFECTS: asks user for start date and returns it
    public LocalDate askStartDate() {
        System.out.println("When would you like to start from?");
        LocalDate date = askDate();
        return date;
    }

    //EFFECTS: asks user for end date and returns it
    public LocalDate askEndDate() {
        System.out.println("and go until?");
        LocalDate date = askDate();
        return date;
    }


    //MODIFIES: this
    //EFFECTS: return period user wants to view budget in
    public String askBudgetPeriod() {
        System.out.println("In what time frame?");
        System.out.println("\tw -> last week");
        System.out.println("\tm -> last month");
        System.out.println("\ty -> last year");
        String period = null;
        String command = input.next();
        command = command.toLowerCase();
        switch (command) {
            case "w":
                period = "weekly";
                break;
            case "y":
                period = "yearly";
                break;
            case "m":
                period = "monthly";
                break;
        }
        return period;
    }

    //MODIFIES: this
    //EFFECTS: creates a new category from user input and adds to CategoryList
    public void createCategoryMenu() {
        boolean keepGoing = true;
        String command;
        int newCommand;

        while (keepGoing) {
            displayCategoryMenu();
            System.out.println("\nSelect an option:");
            System.out.println(GREEN + "\t1 -> create income category" + RESET);
            System.out.println(RED + "\t2 -> create expense category" + RESET);
            System.out.println("\t3 -> category menu");
            command = input.next();
            newCommand = Integer.parseInt(command);
            if (newCommand == 3) {
                keepGoing = false;
            } else {
                processCreateCategoryMenu(newCommand);

            }
        }

        categoryMenu();

    }


    //MODIFIES: this
    //EFFECTS: process input from create category menu
    public void processCreateCategoryMenu(int command) {
        switch (command) {
            case 1:
                createCategory(1);
                break;
            case 2:
                createCategory(2);
                break;
        }
    }

    //REQUIRES: label is either 1 or 0
    //MODIFIES: this
    //EFFECTS: creates new category from user input and adds to appropriate list
    public void createCategory(int label) {
        String userInput;
        System.out.println("What would you like the name to be?");

        userInput = input.next().toLowerCase();
        if (label == 1) {
            IncomeCategory newIncomeCat = new IncomeCategory(userInput);
            budget.getIncomes().addCat(newIncomeCat);
        } else {
            ExpenseCategory newExpenseCat = new ExpenseCategory(userInput);
            budget.getExpenses().addCat(newExpenseCat);
        }




    }

    //MODIFIES: this
    //EFFECTS: display categories and process user input
    public void categoryMenu() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayCategoryMenu();
            System.out.println("\nEnter Category ID to edit or:");
            System.out.println(YELLOW + "\te -> edit category list" + RESET);
            System.out.println(RED + "\tq -> return to menu" + RESET);

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else if (command.equals("e")) {
                editCategoryList();
            } else {
                processCategoryMenu(command);
            }
        }


    }

    //EFFECTS: display category options to user
    public void displayCategoryMenu() {
        LinkedList<IncomeCategory> incomeList = budget.getIncomes().getCatList();
        LinkedList<ExpenseCategory> expenseList = budget.getExpenses().getCatList();

        System.out.println("Here are your created categories:");
        System.out.println(GREEN + "Income Categories:" + RESET);

        for (IncomeCategory income : incomeList) {
            System.out.println(GREEN + "\t-" + income.getName().toUpperCase() + " ID: " + income.getID() + RESET);
        }

        System.out.println(RED + "\nExpense Categories:" + RESET);
        for (ExpenseCategory expense : expenseList) {
            System.out.println(RED + "\t-" + expense.getName().toUpperCase() + " ID: " + expense.getID() + RESET);
        }



    }


    //MODIFIES: this
    //EFFECTS: process user input from category menu
    public void processCategoryMenu(String command) {
        boolean idExists;
        int newCommand = 0;
        try {
            newCommand = Integer.parseInt(command);
        } catch (NumberFormatException ex) {
            System.out.println("Not a valid string input.");
        }

        LinkedList<IncomeCategory> incomeList = budget.getIncomes().getCatList();
        LinkedList<ExpenseCategory> expenseList = budget.getExpenses().getCatList();

        idExists = checkCatID(incomeList, expenseList, newCommand);

        if (!idExists) {
            try {
                throw new WrongID();
            } catch (WrongID e) {
                System.out.println("Incorrect value entered! Try again.");
            }
        }


    }


    //MODIFIES: this
    //EFFECTS: returns true if ID is in either income or expense list
    public boolean checkCatID(LinkedList<IncomeCategory> incomes, LinkedList<ExpenseCategory> expenses, int command) {
        boolean idExists = false;
        for (IncomeCategory income : incomes) {
            if (income.getID() == command) {
                idExists = true;
                currentCategory = income;
                createIncomeMenu();
                break;
            }
        }
        for (ExpenseCategory expense : expenses) {
            if (expense.getID() == command) {
                idExists = true;
                currentCategory = expense;
                createExpenseMenu();
                break;
            }
        }
        return idExists;
    }


    //MODIFIES: this
    //EFFECTS: Process user input and displays income/expense menu
    public void createExpenseMenu() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayExpenseMenu();
            displayIncomeOrExpenseMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processExpenseMenu(command);
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: display expenses in category
    public void displayExpenseMenu() {
        LinkedList<SingleExpense> singleExpenses = currentCategory.getSingle();
        LinkedList<RecurringExpense> recurringExpenses = currentCategory.getRecurring();
        System.out.println("\nYou're currently editing " + RED + currentCategory.getName().toUpperCase() + RESET);
        System.out.println("\nOne-Time Expenses:");

        for (SingleExpense expense : singleExpenses) {
            String name = expense.getName().toUpperCase();
            String amount = Double.toString(expense.getAmount());
            System.out.println("\t" + name + ": " + amount + " ID: " + expense.getID());
        }

        System.out.println("\nRecurring Expenses:");
        for (RecurringExpense expense : recurringExpenses) {
            String name = expense.getName().toUpperCase();
            String amount = Double.toString(expense.getAmount());
            String period = expense.getPeriod().toUpperCase();
            System.out.println("\t" + name + ": " + amount + " " + period + " ID: " + expense.getID());
        }

    }

    //MODIFIES: this
    //EFFECTS: Processes user input from expense menu and redirects user
    public void processExpenseMenu(String command) {
        if (command.equals("a")) {
            addExpense();
        } else if (command.equals("e")) {
            editCat(currentCategory);
        } else {
            processDeleteExpense(command);
        }

    }


    //MODIFIES: this
    //EFFECTS: Process user input and displays income/expense menu
    public void createIncomeMenu() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayIncomeMenu();
            displayIncomeOrExpenseMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processIncomeMenu(command);
            }

        }
    }

    //MODIFIES: this
    //EFFECTS: displays incomes in currentCategory
    public void displayIncomeMenu() {
        LinkedList<SingleIncome> singleIncomes = currentCategory.getSingle();
        LinkedList<RecurringIncome> recurringIncomes = currentCategory.getRecurring();
        System.out.println("\nYou're currently editing " + GREEN + currentCategory.getName().toUpperCase() + RESET);
        System.out.println("\nOne-Time Incomes:");

        for (SingleIncome income : singleIncomes) {
            String name = income.getName().toUpperCase();
            String amount = Double.toString(income.getAmount());
            System.out.println("\t" + name + ": " + amount + " ID: " + income.getID());
        }

        System.out.println("\nRecurring Incomes:");
        for (RecurringIncome income : recurringIncomes) {
            String name = income.getName().toUpperCase();
            String amount = Double.toString(income.getAmount());
            String period = income.getPeriod().toUpperCase();
            System.out.println("\t" + name + ": " + amount + " " + period + " ID: " + income.getID());
        }
    }


    //EFFECTS: displays income/expense menu
    public void displayIncomeOrExpenseMenu() {
        System.out.println("\nEnter ID to" + RED + " delete" + RESET + " OR:");
        System.out.println(GREEN + "\ta -> add new" + RESET);
        System.out.println(YELLOW + "\te -> edit name" + RESET);
        System.out.println(RED + "\tq -> quit" + RESET);
    }

    //MODIFIES: this
    //EFFECTS: processes user command from Income Menu
    public void processIncomeMenu(String command) {
        if (command.equals("a")) {
            addIncome();
        } else if (command.equals("e")) {
            editCat(currentCategory);
        } else {
            processDeleteIncome(command);
        }
    }

    //MODIFIES: currentCategory
    //EFFECTS: edits category from user input
    public void editCat(Category currentCategory) {
        boolean keepGoing = true;
        String name = currentCategory.getName().toUpperCase();
        while (keepGoing) {
            System.out.println("The current category's name is " + name);
            System.out.println("press q to keep name or enter new name:");
            String command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                currentCategory.setName(command);
                name = currentCategory.getName().toUpperCase();
            }
        }
    }


    //MODIFIES: this, currentCategory
    //EFFECTS: processes user input then deletes given income
    public void processDeleteIncome(String command) {
        int newCommand = Integer.parseInt(command);
        LinkedList<SingleIncome> singleIncomes = currentCategory.getSingle();
        LinkedList<RecurringIncome> recurIncomes = currentCategory.getRecurring();

        boolean idExists = checkIncomeID(singleIncomes, recurIncomes, newCommand);

        if (!idExists) {
            try {
                throw new WrongID();
            } catch (WrongID e) {
                System.out.println("Incorrect value entered! Try again.");
            }
        }


    }

    //MODIFIES: this, currentCategory
    //EFFECTS: checks if id is in list, if true ask for user confirmation then delete from currentCategory,
    //         else do nothing
    public boolean checkIncomeID(LinkedList<SingleIncome> siIncome, LinkedList<RecurringIncome> reIncome, int command) {
        boolean idExists = false;
        boolean userSure;

        for (SingleIncome income : siIncome) {
            if (income.getID() == command) {
                idExists = true;
                userSure = checkSure();
                if (userSure) {
                    currentCategory.removeSingle(command);
                }
            }
        }

        for (RecurringIncome income : reIncome) {
            if (income.getID() == command) {
                idExists = true;
                userSure = checkSure();
                if (userSure) {
                    currentCategory.removeRecurring(command);
                }
            }
        }
        return idExists;

    }


    //MODIFIES: this, currentCategory
    //EFFECTS: checks if id exists, if true remove else throw error
    public void processDeleteExpense(String command) {
        int newCommand = Integer.parseInt(command);
        LinkedList<SingleExpense> singleExpenses = currentCategory.getSingle();
        LinkedList<RecurringExpense> recurringExpenses = currentCategory.getRecurring();

        boolean idExists = checkExpenseID(singleExpenses, recurringExpenses, newCommand);

        if (!idExists) {
            try {
                throw new WrongID();
            } catch (WrongID e) {
                System.out.println("Incorrect value entered! Try again.");
            }
        }
    }

    //MODIFIES: this, currentCategory
    //EFFECTS: if id exists, ask for user confirmation then delete expense, else do nothing
    public boolean checkExpenseID(LinkedList<SingleExpense> siExpen, LinkedList<RecurringExpense> reExpen, int comm) {
        boolean idExists = false;
        boolean userSure;

        for (SingleExpense expense : siExpen) {
            if (expense.getID() == comm) {
                idExists = true;
                userSure = checkSure();
                if (userSure) {
                    currentCategory.removeSingle(comm);
                }
            }
        }

        for (RecurringExpense expense : reExpen) {
            if (expense.getID() == comm) {
                idExists = true;
                userSure = checkSure();
                if (userSure) {
                    currentCategory.removeRecurring(comm);
                }
            }
        }
        return idExists;

    }

    //MODIFIES: this
    //EFFECTS: ask user if they want to delete, if yes return true else return false
    public boolean checkSure() {
        boolean sure;
        String command;
        System.out.println("\nAre you sure you want to delete this?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        command = input.next().toLowerCase();
        sure = command.equals("y");
        return sure;
    }

    //REQUIRES: user enters a non-empty string
    //MODIFIES: this
    //EFFECTS: gets user input then adds income to income list
    public void addIncome() {
        String name;
        double amount;
        String period;
        String catName = currentCategory.getName().toUpperCase();

        System.out.println("Where is the income from?");
        name = input.next();
        if (askRecurring()) {
            period = askPeriod();
            System.out.println("How much will you earn " + period + "?");
            amount = Double.parseDouble(input.next());
            RecurringIncome income = new RecurringIncome(name, amount, period);
            currentCategory.addRecurring(income);
            income.setDate(askDate());

            System.out.println("Successfully added " + income.getName() + " to " + catName);
        } else {
            System.out.println("How much did you earn?");
            amount = Double.parseDouble(input.next());
            SingleIncome singleIncome = new SingleIncome(name, amount);
            currentCategory.addSingle(singleIncome);
            singleIncome.setDate(askDate());
            System.out.println("Successfully added " + singleIncome.getName() + " to " + catName);
        }




    }

    //REQUIRES: user enters non-empty string
    //MODIFIES: this, currentCategory
    //EFFECTS: creates an expense from user input and adds it to currentCategory
    public void addExpense() {
        String name;
        double amount;
        String period;
        String catName = currentCategory.getName().toUpperCase();

        System.out.println("Where is the expense from?");
        name = input.next();
        if (askRecurring()) {
            period = askPeriod();
            System.out.println("How much will you spend " + period + "?");
            amount = Double.parseDouble(input.next());
            RecurringExpense expense = new RecurringExpense(name, amount, period);
            expense.setDate(askDate());
            currentCategory.addRecurring(expense);

            System.out.println("Successfully added " + expense.getName() + " to " + catName);
        } else {
            System.out.println("How much did you spend?");
            amount = Double.parseDouble(input.next());
            SingleExpense singleExpense = new SingleExpense(name, amount);
            singleExpense.setDate(askDate());
            currentCategory.addSingle(singleExpense);

            System.out.println("Successfully added " + singleExpense.getName() + " to " + catName);
        }

    }

    //MODIFIES: this
    //EFFECTS: gets date value from user then creates LocalDate from value
    public LocalDate askDate() {
        int year;
        int month;
        int day;
        boolean keepGoing = true;
        LocalDate date = java.time.LocalDate.now();

        year = askYear();
        month = askMonth();
        day = askDay();

        date = LocalDate.of(year, month, day);

        return date;
    }

    //MODIFIES: this
    //EFFECTS: returns valid day value obtained from user
    public int askDay() {
        boolean keepGoing = true;
        int day = java.time.LocalDate.now().getDayOfMonth();

        System.out.println("day:");

        while (keepGoing) {
            day = Integer.parseInt(input.next());
            try {
                if (day >= 1 && day <= 31) {
                    keepGoing = false;
                } else {
                    throw new InvalidValue();
                }
            } catch (InvalidValue e) {
                System.out.println("Invalid day! Try again:");
            }

        }
        return day;
    }

    //MODIFIES: this
    //EFFECTS: returns valid month value obtained from user
    public int askMonth() {
        boolean keepGoing = true;
        int month = java.time.LocalDate.now().getMonthValue();

        System.out.println("month:");

        while (keepGoing) {
            month = Integer.parseInt(input.next());
            try {
                if (month >= 1 && month <= 12) {
                    keepGoing = false;
                } else {
                    throw new InvalidValue();
                }
            } catch (InvalidValue e) {
                System.out.println("Invalid month! Try again:");
            }

        }

        return month;
    }

    //MODIFIES: this
    //EFFECTS: returns valid year obtained from user
    public int askYear() {
        boolean keepGoing = true;
        int year = java.time.LocalDate.now().getYear();
        System.out.println("Input year:");

        while (keepGoing) {
            year = Integer.parseInt(input.next());
            try {
                if (year >= 1000 && year <= 3000) {
                    keepGoing = false;
                } else {
                    throw new InvalidValue();
                }
            } catch (InvalidValue e) {
                System.out.println("Invalid year! Try again:");
            }

        }
        return year;

    }

    //MODIFIES: this
    //EFFECTS: return true if income/expense is recurring
    public boolean askRecurring() {
        boolean keepGoing = true;
        boolean isRecurring = false;
        while (keepGoing) {
            System.out.println("Is it recurring?");
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");


            String command = input.next();
            command = command.toLowerCase();
            switch (command) {
                case "y":
                    isRecurring = true;
                    keepGoing = false;
                    break;
                case "n":
                    keepGoing = false;
                    break;
            }
        }


        return isRecurring;
    }

    //MODIFIES: this
    //EFFECTS: returns period of weekly, bi-weekly, or monthly.
    public String askPeriod() {
        String period = null;
        boolean keepGoing = true;
        String command = null;
        while (keepGoing) {
            displayPeriodOptions();
            command = input.next();
            command = command.toLowerCase();

            try {
                if (command.equals("w") || command.equals("m") || command.equals("b")) {
                    keepGoing = false;
                } else {
                    throw new InvalidValue();
                }
            } catch (InvalidValue e) {
                System.out.println("Invalid entry! Try again.");
            }
        }
        period = processPeriod(command);


        return period;
    }


    //MODIFIES: this
    //EFFECTS: returns period relating to given command
    public String processPeriod(String command) {
        String period = null;
        switch (command) {
            case "w":
                period = "weekly";
                break;
            case "b":
                period = "bi-weekly";
                break;
            case "m":
                period = "monthly";
                break;
        }
        return period;
    }

    //EFFECTS: displays options for periods
    public void displayPeriodOptions() {
        System.out.println("How often will it recur?");
        System.out.println("\tw -> weekly");
        System.out.println("\tb -> bi-weekly");
        System.out.println("\tm -> monthly");
    }


    //MODIFIES: this
    //EFFECTS: process user input to delete Categories from CategoryList
    public void editCategoryList() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayCategoryMenu();
            System.out.println("\n");
            System.out.println("\ta -> add category");
            System.out.println("\td -> delete category");
            System.out.println("\tq -> return to menu");
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                switch (command) {
                    case "a":
                        createCategoryMenu();
                        break;
                    case "d":
                        deleteCategoryMenu();
                        break;
                }
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: removes category based on user input
    public void deleteCategoryMenu() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            System.out.println("Enter Category ID to delete or q to quit:");
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processDeleteMenu(command);
            }
        }


    }

    //MODIFIES: this
    //EFFECTS: checks id exists then process user input to delete given category
    public void processDeleteMenu(String command) {
        int newCommand = Integer.parseInt(command);
        LinkedList<IncomeCategory> incomeList = budget.getIncomes().getCatList();
        LinkedList<ExpenseCategory> expenseList = budget.getExpenses().getCatList();

        boolean idExists = checkDeleteCatID(incomeList, expenseList, newCommand);

        if (!idExists) {
            try {
                throw new WrongID();
            } catch (WrongID e) {
                System.out.println("Incorrect value entered! Try again.");
            }
        }


    }

    //MODIFIES: this
    //EFFECTS: check if id exists, if true delete from CategoryList, else do nothing
    public boolean checkDeleteCatID(LinkedList<IncomeCategory> incomes, LinkedList<ExpenseCategory> expenses, int com) {
        boolean idExists = false;
        for (IncomeCategory income : incomes) {
            if (income.getID() == com) {
                idExists = true;
                currIncomeCat = income;
                deleteCategory();
                break;
            }
        }
        for (ExpenseCategory expense : expenses) {
            if (expense.getID() == com) {
                idExists = true;
                currExpenseCat = expense;
                deleteCategory(currExpenseCat);
                break;
            }
        }
        return idExists;
    }

    //MODIFIES: this
    //EFFECTS: confirms with user then removes income category from Category List
    public void deleteCategory() {
        String command;
        String name = currIncomeCat.getName().toUpperCase();
        System.out.println("You have selected: " + name);
        System.out.println("Are you sure you want to delete this?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        command = input.next().toLowerCase();
        if (command.equals("n")) {
            editCategoryList();
        } else {
            budget.getIncomes().removeCat(currIncomeCat.getID());
            System.out.println("Successfully deleted " + name + "!");
        }
    }

    //MODIFIES: this
    //EFFECTS: confirms with user then removes given ExpenseCategory from CategoryList
    public void deleteCategory(ExpenseCategory currentCategory) {
        String command;
        String name = currExpenseCat.getName().toUpperCase();
        System.out.println("You have selected: " + name);
        System.out.println("Are you sure you want to delete this?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        command = input.next().toLowerCase();
        if (command.equals("n")) {
            editCategoryList();
        } else {
            budget.getExpenses().removeCat(currExpenseCat.getID());
            System.out.println("Successfully deleted " + name + "!");
        }

    }


}
