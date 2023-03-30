package ui.listeners;

import model.*;
import ui.graphics.DrawPieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

//Represents an actionListener for addIncome and addExpense buttons; adds income/expense
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
    private Map<String, Integer> incomeMap;
    private Map<String, Integer> expenseMap;
    private static int id = 1;

    //CONSTRUCTOR
    public AddListener(Budget budget, JList list, Map<Category, LinkedList> currentListMap,
                       int label, JPanel selection,
                       DeleteIncomeExpenseListener delete, JPanel mainPanel, boolean isRecurring,
                       LinkedList<JPanel> currentPanelList, DrawPieChart chart, Map<String, Integer> incomeMap,
                       Map<String, Integer> expenseMap) {

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
        this.incomeMap = incomeMap;
        this.expenseMap = expenseMap;


    }

    //SETTER
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: Gets info from user then creates income/expense and adds it to proper panel
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

    //MODIFIES: this
    //EFFECTS: if index is not selected (== -1) set it to 0
    private void parseIndex() {
        index = Integer.parseInt(String.valueOf(list.getSelectedIndex()));
        if (index == -1) {
            index = 0;
        }
    }

    //MODIFIES: this, mainPanel, budget
    //EFFECTS: creates income/expense and adds it to proper panel
    private void computeResult(JComponent[] components) {
        int result = JOptionPane.showConfirmDialog(mainPanel, components, "Create Category",
                JOptionPane.OK_CANCEL_OPTION);

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

    //MODIFIES: this
    //EFFECTS: sets currentPanel to single or recurring panel depending on user input
    private void checkAnswer(String answer) {
        if (answer.equals("Yes")) {
            isRecurring = true;
            if (index == 0) {
                currentPanel = currentPanelList.get(index + 1);
            } else {
                int newIndex = (index * 2) + 1;
                currentPanel = currentPanelList.get(newIndex);
            }
            askRecurring();
        } else {
            if (index == 0) {
                currentPanel = currentPanelList.get(index);
            } else {
                currentPanel = currentPanelList.get((index * 2));
            }
            isRecurring = false;
        }
    }

    //EFFECTS: ask user whether income/expense is recurring or not
    private void askRecurring() {
        JComboBox<String> comboBox = new JComboBox<String>(new String[] {"weekly", "bi-weekly", "monthly"});
        int resultTwo = JOptionPane.showConfirmDialog(mainPanel, comboBox,
                "Select Period", JOptionPane.OK_CANCEL_OPTION);

        if (resultTwo == JOptionPane.OK_OPTION) {
            period = comboBox.getSelectedItem().toString();
        }
    }



    //MODIFIES: this, mainPanel
    //EFFECTS: creates new panel with name, amount, and period labels and adds it to correct panel
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
            createRecurringPanel(incomeExpenseName, amount, period, currentCategory, date, currentPanel, label);
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
            createSinglePanel(incomeExpenseName, amount, currentCategory, date, currentPanel, label);

        }
    }


    //MODIFIES: this, mainPanel
    //EFFECTS: creates a new single panel and adds it to single panel list
    public void createSinglePanel(String incomeExpenseName, Double amount, Category currentCategory, LocalDate date,
                                  JPanel currentPanel, int label) {
        JPanel panel = new JPanel();
        JLabel name = new JLabel(incomeExpenseName);
        JLabel amountLabel = new JLabel("$" + String.valueOf(amount));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(name, BorderLayout.NORTH);
        panel.add(amountLabel, BorderLayout.CENTER);
        panel.addMouseListener(panelSelectionListener);
        if (label == 0) {
            expenseMap.put(incomeExpenseName, id);
        } else {
            incomeMap.put(incomeExpenseName, id);
        }
        id++;
//        updateChart();
        panel.revalidate();
        panel.repaint();

        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }

    //MODIFIES: this, currentChart
    //EFFECTS: update pieChart
    private void updateChart() {
        currentChart.setBudget(budget);
        currentChart.update();
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: creates recurring panel and adds it to recurring list
    public void createRecurringPanel(String incomeExpenseName, Double amount, String period, Category currentCategory,
                                      LocalDate date, JPanel currentPanel, int label) {
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
        if (label == 0) {
            expenseMap.put(incomeExpenseName, id);
        } else {
            incomeMap.put(incomeExpenseName, id);
        }
        id++;
//        updateChart();
        panel.revalidate();
        panel.repaint();

        update(panel, currentPanel);
    }

    //MODIFIES: this, mainPanel
    //EFFECTS: adds new panel to proper single/recurring mainPanel, and updates it
    private void update(JPanel panel, JPanel currentPanel) {
        currentPanel.add(panel);

        currentPanel.revalidate();
        currentPanel.repaint();
    }
}
