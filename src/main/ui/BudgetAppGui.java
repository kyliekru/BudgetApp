package ui;

import model.*;
import ui.listeners.AddCatListener;

import javax.swing.*;
import java.awt.*;

public class BudgetAppGui {

    private JPanel mainPanel = new JPanel();
    private JPanel incomeCatListContainer;
    private JPanel expenseCatListContainer;
    private JPanel incomeListContainer;
    private JPanel expenseListContainer;
    private JButton addButton;
    private JButton deleteButton;
    private Budget budget;
    private Category category;
    private CategoryList expenseCatList;
    private CategoryList incomeCatList;


    public BudgetAppGui() {
        initializeFields();
        initializeGraphics();

    }


    private void initializeFields() {
        budget = new Budget("unnamed");
        expenseCatList = new CategoryList(0);
        incomeCatList = new CategoryList(1);
        budget.setExpenses(expenseCatList);
        budget.setIncomes(incomeCatList);
    }

    public void initializeGraphics() {
        JFrame frame = new JFrame("Budget App");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        initializePanels();
        initializeButtons();
    }

    public void initializePanels() {
        mainPanel.setLayout(new GridLayout(1, 2));
        JPanel incomeMainPanel = new JPanel();
        incomeMainPanel.setLayout(new BoxLayout(incomeMainPanel, BoxLayout.Y_AXIS));
        JPanel expenseMainPanel = new JPanel();
        expenseMainPanel.setLayout(new BoxLayout(expenseMainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(incomeMainPanel);
        mainPanel.add(expenseMainPanel);

        incomeCatListContainer = new JPanel();
        incomeCatListContainer.setLayout(new BoxLayout(incomeCatListContainer, BoxLayout.Y_AXIS));

        incomeMainPanel.add(new JScrollPane(incomeCatListContainer), BorderLayout.CENTER);

        expenseCatListContainer = new JPanel();
        expenseCatListContainer.setLayout(new BoxLayout(expenseCatListContainer, BoxLayout.Y_AXIS));

        expenseMainPanel.add(new JScrollPane(expenseCatListContainer), BorderLayout.CENTER);
    }

    public void initializeButtons() {
        JButton addCategoryButton = new JButton("Add Category");
        JButton addIncomeButton = new JButton("Add");
        JButton removeIncomeButton = new JButton("Remove");
        JButton addExpenseButton = new JButton("Add");
        JButton removeExpenseButton = new JButton("Remove");
        addCategoryButton.addActionListener(new AddCatListener(mainPanel, expenseCatListContainer,
                incomeCatListContainer, budget));

        mainPanel.add(addCategoryButton);
        expenseCatListContainer.add(addExpenseButton, BorderLayout.CENTER);
        incomeCatListContainer.add(addIncomeButton, BorderLayout.CENTER);
        expenseCatListContainer.add(removeExpenseButton, BorderLayout.CENTER);
        incomeCatListContainer.add(removeIncomeButton, BorderLayout.CENTER);
    }

}
