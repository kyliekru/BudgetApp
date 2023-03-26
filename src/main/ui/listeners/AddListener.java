package ui.listeners;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class AddListener implements ActionListener {


    private JPanel currentPanel;
    private Budget budget;
    private JList list;
    private String incomeExpenseName;
    private double amount;
    private String period;
    private String answer;
    private int label;
    private Category currentCategory;
    private JPanel selection;
    private BoxListener panelSelectionListener;
    private DeleteIncomeExpenseListener delete;
    private JPanel mainPanel;
    private int index;
    private Map<Category, LinkedList> currentListMap;
    private boolean isRecurring;
    private LinkedList<JPanel> currentPanelList;

    public AddListener(Budget budget, JList list, Map<Category, LinkedList> currentListMap,
                       int label, JPanel selection,
                       DeleteIncomeExpenseListener delete, JPanel mainPanel, boolean isRecurring,
                       LinkedList<JPanel> currentPanelList) {

        this.budget = budget;
        this.list = list;
        this.label = label;
        this.selection = selection;
        this.delete = delete;
        panelSelectionListener = new BoxListener(selection, delete);
        this.mainPanel = mainPanel;
        this.currentListMap = currentListMap;
        this.isRecurring = isRecurring;
        this.currentPanelList = currentPanelList;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index = Integer.parseInt(String.valueOf(list.getSelectedIndex()));
        if (index == -1) {
            index = 0;
        }
        if (label == 0) {
            currentCategory = (Category) budget.getExpenses().getCatList().get(index);
        } else {
            currentCategory = (Category) budget.getIncomes().getCatList().get(index);
        }
        JComponent[] components = new JComponent[] {
                new JLabel("Label:"),
                new JTextField(),
                new JLabel("Amount:"),
                new JTextField(),
                new JLabel("Recurring?"),
                new JComboBox<String>(new String[] {"Yes", "No"})
        };

        computeResult(components);


    }

    private void computeResult(JComponent[] components) {
        // Show the JOptionPane and get the user's input
        int result = JOptionPane.showConfirmDialog(mainPanel, components, "Create Category",
                JOptionPane.OK_CANCEL_OPTION);

        // If the user clicked OK, display the input
        if (result == JOptionPane.OK_OPTION) {
            JTextField nameField = (JTextField)components[1];
            JTextField amountField = (JTextField)components[3];
            JComboBox<String> yesNoBox = (JComboBox<String>)components[5];
            incomeExpenseName = nameField.getText().toUpperCase();
            amount = Double.parseDouble(amountField.getText());
            answer = yesNoBox.getSelectedItem().toString();
            System.out.println("Name: " + incomeExpenseName);
            System.out.println("Amount: " + amount);
            System.out.println("Recurring: " + answer);
        }
        checkAnswer(answer);
        createNewPanel(answer);
    }

    private void checkAnswer(String answer) {
        if (answer.equals("Yes")) {
            isRecurring = true;
            currentPanel = currentPanelList.get(index + 1);
            askRecurring();
        } else {
            currentPanel = currentPanelList.get(index);
            isRecurring = false;
        }
    }

    private void askRecurring() {
        JComboBox<String> comboBox = new JComboBox<String>(new String[] {"weekly", "bi-weekly", "monthly"});
        int resultTwo = JOptionPane.showConfirmDialog(mainPanel, comboBox,
                "Select Period", JOptionPane.OK_CANCEL_OPTION);

        if (resultTwo == JOptionPane.OK_OPTION) {
            period = comboBox.getSelectedItem().toString();
        }
    }



    private void createNewPanel(String answer) {
        if (answer.equals("Yes")) {
            createRecurringPanel();
        } else {
            createSinglePanel();
        }
    }

    private void createSinglePanel() {
        JPanel panel = new JPanel();
        JLabel name = new JLabel(incomeExpenseName);
        JLabel amountLabel = new JLabel("$" + String.valueOf(amount));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(name, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        if (label == 0) {
            SingleExpense expense = new SingleExpense(incomeExpenseName, amount);
            currentCategory.addSingle(expense);
        } else {
            SingleIncome income = new SingleIncome(incomeExpenseName, amount);
            currentCategory.addSingle(income);
        }
        panel.addMouseListener(panelSelectionListener);
        panel.revalidate();
        panel.repaint();

        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }

    private void createRecurringPanel() {
        JPanel panel = new JPanel();
        JLabel name = new JLabel(incomeExpenseName);
        JLabel amountLabel = new JLabel("$" + String.valueOf(amount));
        JLabel periodLabel = new JLabel(period);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(name, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        panel.add(periodLabel, BorderLayout.SOUTH);
        if (label == 0) {
            RecurringExpense expense = new RecurringExpense(incomeExpenseName, amount, period);
            currentCategory.addRecurring(expense);
        } else {
            RecurringIncome income = new RecurringIncome(incomeExpenseName, amount, period);
            currentCategory.addRecurring(income);
        }
        panel.addMouseListener(panelSelectionListener);
        panel.revalidate();
        panel.repaint();

        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }
}
