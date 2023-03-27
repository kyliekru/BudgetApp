package ui.listeners;

import model.*;
import ui.graphics.DrawPieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
    private LocalDate date;
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
    private DrawPieChart currentChart;

    public AddListener(Budget budget, JList list, Map<Category, LinkedList> currentListMap,
                       int label, JPanel selection,
                       DeleteIncomeExpenseListener delete, JPanel mainPanel, boolean isRecurring,
                       LinkedList<JPanel> currentPanelList, DrawPieChart chart) {

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
        currentChart = chart;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parseIndex();
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
                new JLabel("Start year:"),
                new JTextField(),
                new JLabel("Start month:"),
                new JTextField(),
                new JLabel("Start day:"),
                new JTextField(),
                new JLabel("Recurring?"),
                new JComboBox<String>(new String[] {"Yes", "No"})
        };

        computeResult(components);


    }

    private void parseIndex() {
        index = Integer.parseInt(String.valueOf(list.getSelectedIndex()));
        if (index == -1) {
            index = 0;
        }
    }

    private void computeResult(JComponent[] components) {
        // Show the JOptionPane and get the user's input
        int result = JOptionPane.showConfirmDialog(mainPanel, components, "Create Category",
                JOptionPane.OK_CANCEL_OPTION);

        // If the user clicked OK, display the input
        if (result == JOptionPane.OK_OPTION) {
            JTextField nameField = (JTextField)components[1];
            JTextField amountField = (JTextField)components[3];
            JTextField startYear = (JTextField) components[5];
            JTextField startMonth = (JTextField) components[7];
            JTextField startDay = (JTextField) components[9];
            JComboBox<String> yesNoBox = (JComboBox<String>)components[11];
            incomeExpenseName = nameField.getText().toUpperCase();
            amount = Double.parseDouble(amountField.getText());
            answer = yesNoBox.getSelectedItem().toString();
            String year = startYear.getText();
            String month = startMonth.getText();
            String day = startDay.getText();
            date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
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
            if (label == 0) {
                RecurringExpense expense = new RecurringExpense(incomeExpenseName, amount, period);
                currentCategory.addRecurring(expense);
                expense.setDate(date);
            } else {
                RecurringIncome income = new RecurringIncome(incomeExpenseName, amount, period);
                currentCategory.addRecurring(income);
                income.setDate(date);
            }
            createRecurringPanel(incomeExpenseName, amount, period, currentCategory, date, currentPanel);
        } else {
            if (label == 0) {
                SingleExpense expense = new SingleExpense(incomeExpenseName, amount);
                currentCategory.addSingle(expense);
                expense.setDate(date);
            } else {
                SingleIncome income = new SingleIncome(incomeExpenseName, amount);
                currentCategory.addSingle(income);
                income.setDate(date);
            }
            createSinglePanel(incomeExpenseName, amount, currentCategory, date, currentPanel);

        }
    }

    public void createSinglePanel(String incomeExpenseName, Double amount, Category currentCategory, LocalDate date,
                                  JPanel currentPanel) {
        JPanel panel = new JPanel();
        JLabel name = new JLabel(incomeExpenseName);
        JLabel amountLabel = new JLabel("$" + String.valueOf(amount));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(name, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        panel.addMouseListener(panelSelectionListener);
//        updateChart();
        panel.revalidate();
        panel.repaint();

        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }

//    private void updateChart() {
//        currentChart.setBudget(budget);
//        currentChart.update();
//    }

    public void createRecurringPanel(String incomeExpenseName, Double amount, String period, Category currentCategory,
                                      LocalDate date, JPanel currentPanel) {
        JPanel panel = new JPanel();
        JLabel name = new JLabel(incomeExpenseName);
        JLabel amountLabel = new JLabel("$" + String.valueOf(amount));
        JLabel periodLabel = new JLabel(period);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(name, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        panel.add(periodLabel, BorderLayout.SOUTH);
        panel.addMouseListener(panelSelectionListener);
//        updateChart();
        panel.revalidate();
        panel.repaint();

        update(panel, currentPanel);
    }

    private void update(JPanel panel, JPanel currentPanel) {
        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }
}
