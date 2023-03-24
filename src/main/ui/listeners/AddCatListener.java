package ui.listeners;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCatListener implements ActionListener {

    private JPanel currentPanel;
    private String catName;
    private String type;
    private Category category;
    private Budget budget;
    private JPanel incomeContainer;
    private JPanel expenseContainer;

    public AddCatListener(JPanel panel, JPanel expenseContainer, JPanel incomeContainer, Budget budget) {
        currentPanel = panel;
        this.budget = budget;
        this.incomeContainer = incomeContainer;
        this.expenseContainer = expenseContainer;
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
            catName = nameField.getText();
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
            createNewPanel(0);
        } else {
            category = new IncomeCategory(catName);
            budget.getIncomes().addCat(category);
            createNewPanel(1);
        }
    }

    private void createNewPanel(int label) {
        JPanel newCat = new JPanel();
        newCat.setLayout(new GridLayout(0, 3));
        newCat.add(new JLabel(category.getName()));
        JPanel parent;
        if (label == 0) {
            parent = expenseContainer;
        } else {
            parent = incomeContainer;
        }
        parent.add(new JScrollPane(newCat), BorderLayout.PAGE_START);
        parent.revalidate();
        parent.repaint();
    }

}

