package ui.listeners;

import model.*;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

//Represents an ActionListener for load button in file menu; loads budget from file
public class LoadListener implements ActionListener {

    private JsonReader jsonReader;
    private Budget budget;
    private CategoryList expenseCatList;
    private CategoryList incomeCatList;
    private static final String JSON_STORE = "./data/budget.json";
    private AddCatListener addCatListener;
    private AddListener addExpenseListener;
    private AddListener addIncomeListener;
    private LinkedList<JPanel> incomePanelList;
    private LinkedList<JPanel> expensePanelList;
    private JPanel currentPanel;
    private JFrame frame;
    private SaveListener saver;


    //CONSTRUCTOR
    public LoadListener(Budget budget, JsonReader jsonReader, LinkedList<JPanel> incomePanels,
                        LinkedList<JPanel> expensePanels, JFrame frame, SaveListener saver) {
        this.budget = budget;
        this.jsonReader = jsonReader;
        this.incomePanelList = incomePanels;
        this.expensePanelList = expensePanels;
        this.frame = frame;
        this.saver = saver;
    }

    //SETTERS
    public void setAddCatListener(AddCatListener catListener) {
        addCatListener = catListener;
    }

    public void setAddIncomeListener(AddListener addIncome) {
        this.addIncomeListener = addIncome;
    }

    public void setAddExpenseListener(AddListener addExpense) {
        this.addExpenseListener = addExpense;
    }


    //MODIFIES: this, mainPanel, budget
    //EFFECTS: reads budget from file and builds panels
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            budget = jsonReader.read();
            System.out.println("Loaded " + budget.getName() + " from " + JSON_STORE);
            expenseCatList = budget.getExpenses();
            incomeCatList = budget.getIncomes();
            LinkedList<Category> expenseCats = expenseCatList.getCatList();
            LinkedList<Category> incomeCats = incomeCatList.getCatList();
            int index = 0;

            for (Category cat : expenseCats) {
                addCatListener.addCat(cat.getName(), "Expense");

                addExpenses(cat, index);
                index++;
            }
            index = 0;
            for (Category cat : incomeCats) {
                addCatListener.addCat(cat.getName(), "Income");
                addIncomes(cat, index);
                index++;
            }
            completeLoad();



        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void completeLoad() {
        saver.setBudget(budget);

        JOptionPane.showMessageDialog(frame, "Loaded " + budget.getName() + " from " + JSON_STORE);
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: adds expense panels from loaded budget
    private void addExpenses(Category cat, int index) {
        LinkedList<SingleExpense> singleExpenses = cat.getSingle();
        //Collections.reverse(singleExpenses);
        LinkedList<RecurringExpense> recurringExpenses = cat.getRecurring();
        //Collections.reverse(recurringExpenses);
        for (SingleExpense expense : singleExpenses) {
            if (index == 0) {
                currentPanel = expensePanelList.get(index + 1);
            } else {
                currentPanel = expensePanelList.get((index * 2));
            }
            String name = expense.getName();
            Double amount = expense.getAmount();
            LocalDate date = expense.getDate();
            addExpenseListener.createSinglePanel(name, amount, cat, date, currentPanel, 0);
        }
        for (RecurringExpense expense : recurringExpenses) {
            if (index == 0) {
                currentPanel = expensePanelList.get(index);
            } else {
                currentPanel = expensePanelList.get((index * 2) + 1);
            }
            addExpenseListener.createRecurringPanel(expense.getName(), expense.getAmount(),
                    expense.getPeriod(), cat, expense.getDate(), currentPanel, 0);

        }
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: adds income panels from loaded budget
    private void addIncomes(Category cat, int index) {
        LinkedList<SingleIncome> singleIncomes = cat.getSingle();
        //Collections.reverse(singleIncomes);
        LinkedList<RecurringIncome> recurringIncomes = cat.getRecurring();
        //Collections.reverse(recurringIncomes);
        for (SingleIncome income : singleIncomes) {
            if (index == 0) {
                currentPanel = incomePanelList.get(index);
            } else {
                currentPanel = incomePanelList.get((index * 2));
            }

            addIncomeListener.createSinglePanel(income.getName(), income.getAmount(), cat, income.getDate(),
                    currentPanel, 1);
        }
        for (RecurringIncome income : recurringIncomes) {
            if (index == 0) {
                currentPanel = incomePanelList.get(index + 1);
            } else {
                currentPanel = incomePanelList.get((index * 2) + 1);
            }
            addIncomeListener.createRecurringPanel(income.getName(), income.getAmount(), income.getPeriod(),
                    cat, income.getDate(), currentPanel, 1);
        }
    }
}