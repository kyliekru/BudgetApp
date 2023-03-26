package ui.listeners;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class AddCatListener implements ActionListener {

    private String catName;
    private String type;
    private JList<String> incomeList;
    private JList<String> expenseList;
    private Category category;
    private Budget budget;
    JPanel parent;
    DefaultListModel model;
    private JPanel incomeCatPanel;
    private JPanel expenseCatPanel;
    private DefaultListModel expenseModel;
    private DefaultListModel incomeModel;
    private DefaultListModel newCatModel = new DefaultListModel<>();
    private JPanel currentPanel;
    private JList currentList;
    private JPanel panelContainer;
    private JPanel incomeContainer;
    private JPanel expenseContainer;
    private LinkedList<JPanel> currentCatList;
    private LinkedList<JPanel> incomeCatList;
    private LinkedList<JPanel> expenseCatList;
    private JPanel selection;
    private Map<Category, LinkedList> expenseListMap;
    private Map<Category, LinkedList> incomeListMap;
    private LinkedList<JPanel> incomeCatSingleRecurringPanels;

    private LinkedList<JPanel> expenseCatSingleRecurringPanels;
    private LinkedList<JPanel> currentArrayList;

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

        // If the user clicked OK, display the input
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

    private void addCat(String catName, String type) {
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

    private void createNewPanel(int label) {
        JPanel newCatPanel = new JPanel();
        newCatPanel.setLayout(new BorderLayout());
        newCatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel catNamePanel = new JPanel();
        JPanel incomeExpensePanel = new JPanel();
        incomeExpensePanel.setLayout(new GridLayout(1, 2));
        JPanel singleIncomeExpensePanel = new JPanel();
        JPanel recurringIncomeExpensePanel = new JPanel();
        incomeExpensePanel.add(singleIncomeExpensePanel);
        incomeExpensePanel.add(recurringIncomeExpensePanel);
        newCatPanel.add(incomeExpensePanel);

        JLabel catNameLabel = new JLabel(category.getName());
        catNamePanel.add(catNameLabel);
        newCatPanel.add(catNamePanel, BorderLayout.NORTH);
        catNameLabel.setForeground(Color.RED);
        newCatPanel.add(catNameLabel, BorderLayout.NORTH);
        checkLabel(label);
        model.insertElementAt(category.getName(), 0);
        panelContainer.add(newCatPanel, category.getName());
        currentCatList.addFirst(newCatPanel);
        currentArrayList.addFirst(singleIncomeExpensePanel);
        currentArrayList.addFirst(recurringIncomeExpensePanel);
        update();
    }

    private void update() {
        parent.revalidate();
        parent.repaint();
    }

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

