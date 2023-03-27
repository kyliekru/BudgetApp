package ui.listeners;

import model.*;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
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


    //CONSTRUCTOR
    public LoadListener(Budget budget, JsonReader jsonReader, LinkedList<JPanel> incomePanels,
                        LinkedList<JPanel> expensePanels, JFrame frame) {
        this.budget = budget;
        this.jsonReader = jsonReader;
        this.incomePanelList = incomePanels;
        this.expensePanelList = expensePanels;
        this.frame = frame;
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
            JOptionPane.showMessageDialog(frame, "Loaded " + budget.getName() + " from " + JSON_STORE);



        } catch (IOException exception) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: adds expense panels from loaded budget
    private void addExpenses(Category cat, int index) {
        LinkedList<SingleExpense> singleExpenses = cat.getSingle();
        LinkedList<RecurringExpense> recurringExpenses = cat.getRecurring();
        for (SingleExpense expense : singleExpenses) {
            currentPanel = expensePanelList.get(index);
            String name = expense.getName();
            Double amount = expense.getAmount();
            LocalDate date = expense.getDate();
            addExpenseListener.createSinglePanel(name, amount, cat, date, currentPanel);
        }
        for (RecurringExpense expense : recurringExpenses) {
            currentPanel = expensePanelList.get(index + 1);
            addExpenseListener.createRecurringPanel(expense.getName(), expense.getAmount(),
                    expense.getPeriod(), cat, expense.getDate(), currentPanel);

        }
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: adds income panels from loaded budget
    private void addIncomes(Category cat, int index) {
        LinkedList<SingleIncome> singleIncomes = cat.getSingle();
        LinkedList<RecurringIncome> recurringIncomes = cat.getRecurring();
        for (SingleIncome income : singleIncomes) {
            currentPanel = incomePanelList.get(index);
            addIncomeListener.createSinglePanel(income.getName(), income.getAmount(), cat, income.getDate(),
                    currentPanel);
        }
        for (RecurringIncome income : recurringIncomes) {
            currentPanel = incomePanelList.get(index + 1);
            addIncomeListener.createRecurringPanel(income.getName(), income.getAmount(), income.getPeriod(),
                    cat, income.getDate(), currentPanel);
        }
    }
}
