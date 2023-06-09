package ui.listeners;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

//Represents an ActionListener for the addCategory button; adds a category
public class AddCatListener implements ActionListener {

    private String catName;
    private String type;
    private final JList<String> incomeList;
    private final JList<String> expenseList;
    private Category category;
    private Budget budget;
    JPanel parent;
    DefaultListModel model;
    private final JPanel incomeCatPanel;
    private final JPanel expenseCatPanel;
    private final DefaultListModel expenseModel;
    private final DefaultListModel incomeModel;
    private final DefaultListModel newCatModel = new DefaultListModel<>();
    private final JPanel currentPanel;
    private JList currentList;
    private JPanel panelContainer;
    private final JPanel incomeContainer;
    private final JPanel expenseContainer;
    private LinkedList<JPanel> currentCatList;
    private final LinkedList<JPanel> incomeCatList;
    private final LinkedList<JPanel> expenseCatList;
    private final JPanel selection;
    private final Map<Category, LinkedList> expenseListMap;
    private final Map<Category, LinkedList> incomeListMap;
    private final LinkedList<JPanel> incomeCatSingleRecurringPanels;

    private final LinkedList<JPanel> expenseCatSingleRecurringPanels;
    private LinkedList<JPanel> currentArrayList;

    //COSTRUCTOR
    public AddCatListener(JPanel expenseCatContainer, JPanel incomeCatContainer, Budget budget,
                          DefaultListModel expenseModel, DefaultListModel incomeModel, JList incomeList,
                          JList expenseList, JPanel currentPanel, JPanel incomeContainer, JPanel expenseContainer,
                          LinkedList<JPanel> incomePanelArray,
                          LinkedList<JPanel> expensePanelArray,
                          JPanel selection, Map<Category, LinkedList> expenseListMap,
                          Map<Category, LinkedList> incomeListMap, LinkedList<JPanel> incomeCatSingleRecurringPanels,
                          LinkedList<JPanel> expenseCatSingleRecurringPanels) {
        this.budget = budget;
        this.incomeCatPanel = incomeCatContainer;
        this.expenseCatPanel = expenseCatContainer;
        this.expenseModel = expenseModel;
        this.incomeModel = incomeModel;
        this.incomeList = incomeList;
        this.expenseList = expenseList;
        this.currentPanel = currentPanel;
        this.incomeContainer = incomeContainer;
        this.expenseContainer = expenseContainer;
        this.expenseCatList = expensePanelArray;
        this.incomeCatList = incomePanelArray;
        this.selection = selection;
        this.expenseListMap = expenseListMap;
        this.incomeListMap = incomeListMap;
        this.incomeCatSingleRecurringPanels = incomeCatSingleRecurringPanels;
        this.expenseCatSingleRecurringPanels = expenseCatSingleRecurringPanels;
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: Gets category info from user and creates it and its panel
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent[] components = new JComponent[] {
                new JLabel("Category Name:"),
                new JTextField(),
                new JLabel("Type:"),
                new JComboBox<String>(new String[] {"Expense", "Income"})
        };

        // Show the JOptionPane and get the user's input
        int result = JOptionPane.showConfirmDialog(currentPanel, components, "Create Category",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            JTextField nameField = (JTextField)components[1];
            JComboBox<String> yesNoBox = (JComboBox<String>)components[3];
            catName = nameField.getText().toUpperCase();
            type = yesNoBox.getSelectedItem().toString();
            System.out.println("Name: " + catName);
            System.out.println("Expense/Income: " + type);
        }
        addCat(catName, type);
    }

    //SETTER
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    //MODIFIES: this, budget
    //EFFECTS: creates category and adds to budget; then makes panel
    public void addCat(String catName, String type) {
        if (type.equals("Expense")) {
            category = new ExpenseCategory(catName);
            budget.getExpenses().addCat(category);
            expenseListMap.put(category, new LinkedList<>());
            currentArrayList = expenseCatSingleRecurringPanels;
            createNewPanel(0);
        } else {
            category = new IncomeCategory(catName);
            budget.getIncomes().addCat(category);
            incomeListMap.put(category, new LinkedList<>());
            currentArrayList = incomeCatSingleRecurringPanels;
            createNewPanel(1);
        }
    }

    //MODIFIES: mainPanel, this
    //EFFECTS: creates Category panel, include a label panel, a single panel and a recurring panel
    private void createNewPanel(int label) {
        JPanel newCatPanel = new JPanel();
        newCatPanel.setLayout(new BorderLayout());
        newCatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel catNamePanel = new JPanel();
        catNamePanel.setLayout(new BorderLayout());
        JPanel incomeExpensePanel = new JPanel();
        incomeExpensePanel.setLayout(new GridLayout(1, 2));
        JPanel singleIncomeExpensePanel = new JPanel();
        JPanel recurringIncomeExpensePanel = new JPanel();
        incomeExpensePanel.add(singleIncomeExpensePanel);
        incomeExpensePanel.add(recurringIncomeExpensePanel);
        createCatNamePanel(catNamePanel, newCatPanel);
        newCatPanel.add(incomeExpensePanel);



        checkLabel(label);
        insertElements();
        panelContainer.add(newCatPanel, category.getName());
        currentCatList.addFirst(newCatPanel);
        currentArrayList.add(singleIncomeExpensePanel);
        currentArrayList.add(recurringIncomeExpensePanel);
        update();
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: inserts category name to proper catList at the end of the list
    private void insertElements() {
        if (model.getSize() == 0) {
            model.insertElementAt(category.getName(), 0);
        } else {
            model.insertElementAt(category.getName(), model.getSize());
        }
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: creates Category name label, adds it to label panel, the adds panel to Category panel
    private void createCatNamePanel(JPanel catNamePanel, JPanel newCatPanel) {
        JLabel catNameLabel = new JLabel(category.getName());
        catNameLabel.setBounds(50, 10, 30, 10);
        catNamePanel.add(catNameLabel, BorderLayout.CENTER);
        newCatPanel.add(catNamePanel, BorderLayout.NORTH);
        catNameLabel.setForeground(Color.PINK);
        newCatPanel.add(catNamePanel, BorderLayout.NORTH);
    }

    //MODFIES: this, mainPanel
    //EFFECTS: revalidates and repaints CardLayout to show new catPanel
    private void update() {
        parent.revalidate();
        parent.repaint();
    }

    //MODIFIES: this
    //EFFECTS: sets fields to either income or expense depending on label; 0 == expense
    private void checkLabel(int label) {
        if (label == 0) {
            parent = expenseCatPanel;
            model = expenseModel;
            currentList = expenseList;
            panelContainer = expenseContainer;
            currentCatList = expenseCatList;
        } else {
            parent = incomeCatPanel;
            model = incomeModel;
            currentList = incomeList;
            panelContainer = incomeContainer;
            currentCatList = incomeCatList;
        }
    }


}

