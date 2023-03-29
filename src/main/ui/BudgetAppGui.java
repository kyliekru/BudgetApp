package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.graphics.DrawPieChart;
import ui.graphics.LogoCanvas;
import ui.listeners.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//BudgetApp graphical user interface class using Swing
public class BudgetAppGui {
    
    private JPanel mainPanel;
    private JFrame frame;
    
    private JPanel incomeCatsPanel;
    private JScrollPane incomeCats;
    private JList<String> incomeCatNames;
    private DefaultListModel incomeModel = new DefaultListModel<>();
    
    private JPanel expenseCatsPanel;
    private JScrollPane expenseCats;
    private JList<String> expenseCatNames;
    private DefaultListModel expenseModel = new DefaultListModel<>();

    private Budget budget;
    private Category category;
    private CategoryList expenseCatList;
    private CategoryList incomeCatList;

    private JButton addCategory;
    private JButton deleteCategory;
    private JButton addIncomeButton;
    private JButton deleteIncomeButton;

    private JButton addExpenseButton;
    private JButton deleteExpenseButton;

    private JPanel incomePanelContainer;
    private JPanel expensePanelContainer;
    private CardLayout incomeCardLayout;
    private CardLayout expenseCardLayout;

    private LinkedList<JPanel> incomePanelArray;
    private LinkedList<JPanel> expensePanelArray;

    private JPanel selection;
    private DeleteIncomeExpenseListener deleteListener;
    private DeleteCatListener deleteCatListener;

    private Category selectedCat;
    private Map<Category, LinkedList> incomeCatMap;
    private Map<Category, LinkedList> expenseCatMap;
    private LinkedList<JPanel> incomePanelList;
    private LinkedList<JPanel> expensePanelList;
    private boolean isRecurring;
    private int currentCatLabel;
    private DrawPieChart expensePieChart;
    private DrawPieChart incomePieChart;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private AddCatListener addCatListener;
    private AddListener addIncomeListener;
    private AddListener addExpenseListener;

    private static final String JSON_STORE = "./data/budget.json";
    private JPanel currentPanel;
    private CatListSelectionListener expenseListListener;
    private CatListSelectionListener incomeListListener;
    private Map<String, Integer> incomeMap = new HashMap<>();
    private Map<String, Integer> expenseMap = new HashMap<>();

    //MODIFIES: this
    //EFFECTS: build base gui
    public BudgetAppGui() {
        initializeFields();
        initializeGraphics();
    }



    //MODIFIES: this
    //EFFECTS: initializes fields
    private void initializeFields() {
        budget = new Budget("unnamed");
        expenseCatList = new CategoryList(0);
        incomeCatList = new CategoryList(1);
        budget.setExpenses(expenseCatList);
        budget.setIncomes(incomeCatList);
        incomePanelArray = new LinkedList<>();
        expensePanelArray = new LinkedList<>();
        selection = new JPanel();
        incomeCatMap = new HashMap<>();
        expenseCatMap = new HashMap<>();
        incomePanelList = new LinkedList<>();
        expensePanelList = new LinkedList<>();
        deleteListener = new DeleteIncomeExpenseListener(budget, selectedCat, currentCatLabel, isRecurring,
                incomeMap, expenseMap);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: builds base graphics; buttons, catList panels and menuBar
    private void initializeGraphics() {
        frame = new JFrame("Budget App");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(750, 650));
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(900, 700));

        frame.pack();

        frame.setVisible(true);

        initialize();
        JComboBox<String> comboBox = new JComboBox<String>(new String[] {"Yes", "No"});
        int resultTwo = JOptionPane.showConfirmDialog(mainPanel, comboBox,
                "Load budget?", JOptionPane.OK_CANCEL_OPTION);

        if (resultTwo == JOptionPane.OK_OPTION) {
            String answer = comboBox.getSelectedItem().toString();
            processAnswer(answer);
        }
        frame.setJMenuBar(createMenuBar());

    }

    //MODIFIES: this
    //EFFECTS: intializes buttons and panels
    private void initialize() {
        initializePanels();
        initializeCatPanels();
        initializeButtons();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    //MODIFIES: this, budget
    //EFFECTS: asks user to load, if yes load budget; else start new
    private void processAnswer(String answer) {
        if (answer.equals("Yes")) {
            load();
        } else {
            String input = JOptionPane.showInputDialog("Name your budget:");
            if (input != null && !input.equals("")) {
                budget.setName(input);
            }
        }
    }

    //MODIFIES: this, budget
    //EFFECTS: load budget
    public void load() {
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

    //MODIFIES: this
    //EFFECTS: display loaded message and set listeners to loaded budget
    private void completeLoad() {
        JOptionPane.showMessageDialog(frame, "Loaded " + budget.getName() + " from " + JSON_STORE);
        addCatListener.setBudget(budget);
        addIncomeListener.setBudget(budget);
        addExpenseListener.setBudget(budget);
        deleteCatListener.setBudget(budget);
        deleteListener.setBudget(budget);
        incomeListListener.setLists(incomeCatNames, budget.getIncomes());
        expenseListListener.setLists(expenseCatNames, budget.getExpenses());

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
                currentPanel = expensePanelList.get(index);
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
                currentPanel = expensePanelList.get(index + 1);
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

    //EFFECTS: builds JMenuBar with save and load buttons and returns it
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);
        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Save");
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        SaveListener saver = new SaveListener(jsonWriter, budget, frame);
        rbMenuItem.addActionListener(saver);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

//        JRadioButtonMenuItem rbMenuItem2 = new JRadioButtonMenuItem("Load");
//        rbMenuItem2.setMnemonic(KeyEvent.VK_O);
//        loadListener = new LoadListener(budget, jsonReader, incomePanelList, expensePanelList, frame, saver);
//        rbMenuItem2.addActionListener(loadListener);
//        group.add(rbMenuItem2);
//        menu.add(rbMenuItem2);
        return menuBar;
    }

    //MODIFIES: this
    //EFFECTS: create buttons and add listeners
    private void initializeButtons() {
        addCategory = new JButton("Add Cat");
        deleteCategory = new JButton("Delete Cat");
        addIncomeButton = new JButton("Add");
        setIncomeAndCatButtons(addCategory, addIncomeButton, deleteCategory);

        deleteIncomeButton = new JButton("Delete");
        deleteIncomeButton.addActionListener(deleteListener);
        JPanel incomeButtonsPanel = new JPanel();
        JPanel expenseButtonsPanel = new JPanel();
        incomeButtonsPanel.add(addIncomeButton, BorderLayout.LINE_START);
        incomeButtonsPanel.add(deleteIncomeButton, BorderLayout.LINE_END);
        incomeCatsPanel.add(incomeButtonsPanel, BorderLayout.PAGE_END);

        addExpenseButton = new JButton("Add");
        addExpenseListener = new AddListener(budget, expenseCatNames, expenseCatMap, 0, selection,
                deleteListener, mainPanel, isRecurring, expensePanelList, expensePieChart, incomeMap, expenseMap);
        addExpenseButton.addActionListener(addExpenseListener);
        deleteExpenseButton = new JButton("Delete");
        deleteExpenseButton.addActionListener(deleteListener);
        expenseButtonsPanel.add(addExpenseButton, BorderLayout.LINE_START);
        expenseButtonsPanel.add(deleteExpenseButton, BorderLayout.LINE_END);
        expenseCatsPanel.add(expenseButtonsPanel, BorderLayout.PAGE_END);




    }

    //MODIFIES: this
    //EFFECTS: configure income and cat buttons and add to mainPanel
    private void setIncomeAndCatButtons(JButton addCategory, JButton addIncomeButton, JButton deleteCat) {
        addCategory.setPreferredSize(new Dimension(90, 60));
        addListeners();
        deleteCat.addActionListener(deleteCatListener);
        JPanel catButtonPanel = new JPanel();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("ProjectLogo.png");
        JLabel picLabel = new JLabel(new ImageIcon(url));
        picLabel.setBounds(10,20, 175, 65);
        JPanel spacerPanel = new JPanel();
        spacerPanel.setMinimumSize(new Dimension(65, 50));
        JPanel spacerPanel2 = new JPanel();
        spacerPanel2.setMinimumSize(new Dimension(65, 50));

        catButtonPanel.setLayout(new BoxLayout(catButtonPanel, BoxLayout.X_AXIS));
        catButtonPanel.add(picLabel, BorderLayout.LINE_START);
        catButtonPanel.add(spacerPanel);
        catButtonPanel.add(addCategory, BorderLayout.CENTER);
        catButtonPanel.add(spacerPanel2);
        catButtonPanel.add(deleteCategory, BorderLayout.LINE_END);
        mainPanel.add(catButtonPanel, BorderLayout.PAGE_START);
    }

    //MODIFIES: this
    //EFFECTS: adds listeners to load, addCat and addIncome buttons
    private void addListeners() {
        addCatListener = new AddCatListener(expenseCatsPanel, incomeCatsPanel, budget,
                expenseModel, incomeModel, incomeCatNames, expenseCatNames, mainPanel,
                incomePanelContainer, expensePanelContainer, incomePanelArray, expensePanelArray, selection,
                expenseCatMap, incomeCatMap, incomePanelList, expensePanelList);
        addCategory.addActionListener(addCatListener);
        addIncomeListener = new AddListener(budget, incomeCatNames, incomeCatMap, 1, selection,
                deleteListener, mainPanel, isRecurring, incomePanelList, incomePieChart, incomeMap, expenseMap);
        addIncomeButton.addActionListener(addIncomeListener);
    }

    //MODIFIES: this
    //EFFECTS: build income and expense category panel containers and add to mainPanel
    private void initializePanels() {

        incomeCatsPanel = new JPanel();
        incomeCatsPanel.setLayout(new BorderLayout());

        expenseCatsPanel = new JPanel();
        expenseCatsPanel.setLayout(new BorderLayout());

        incomeCatNames = new JList<>(incomeModel);
        expenseCatNames = new JList<>(expenseModel);

        incomeCats = new JScrollPane(incomeCatNames);
        expenseCats = new JScrollPane(expenseCatNames);

        incomePanelContainer = new JPanel();
        expensePanelContainer = new JPanel();
        incomeCardLayout = new CardLayout();
        expenseCardLayout = new CardLayout();
        incomePanelContainer.setLayout(incomeCardLayout);
        expensePanelContainer.setLayout(expenseCardLayout);
        addCatContainers(incomePanelContainer, expensePanelContainer);

        incomeCatsPanel.add(incomeCats);
        expenseCatsPanel.add(expenseCats);
        JLabel incomeCatLabel = new JLabel("INCOMING");
        JLabel expenseCatLabel = new JLabel("OUTGOING");
        incomeCatsPanel.add(incomeCatLabel, BorderLayout.NORTH);
        expenseCatsPanel.add(expenseCatLabel, BorderLayout.NORTH);
        mainPanel.add(incomeCatsPanel, BorderLayout.LINE_START);
        mainPanel.add(expenseCatsPanel, BorderLayout.LINE_END);


    }

    //MODIFIES: this
    //EFFECTS: creates deleteListener for deleteCatButton
    private void createDeleteListener() {
        deleteCatListener = new DeleteCatListener(budget, incomeCatNames, incomePanelArray, expenseCatNames,
                expensePanelArray, currentCatLabel, selectedCat, incomePanelList, expensePanelList, incomeCardLayout,
                expenseCardLayout, mainPanel, incomeModel, expenseModel);
    }

    //MODIFIES: this
    //EFFECTS: builds income and expense panel containers and adds to cat containers
    private void addCatContainers(JPanel incomePanelContainer, JPanel expensePanelContainer) {
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayout(1, 2));
        panelContainer.add(incomePanelContainer);
        panelContainer.add(expensePanelContainer);
//        JPanel incomePiePanel = new JPanel();
//        JPanel expensePiePanel = new JPanel();
//        incomePiePanel.setPreferredSize(new Dimension(400, 400));
//        expensePiePanel.setPreferredSize(new Dimension(400, 400));
//        incomePieChart = new DrawPieChart(budget, 1, mainPanel);
//        expensePieChart = new DrawPieChart(budget, 0, mainPanel);
//        expensePiePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//
//        incomePiePanel.add(incomePieChart, BorderLayout.CENTER);
//        expensePiePanel.add(expensePieChart, BorderLayout.CENTER);
//        panelContainer.add(incomePiePanel);
//        panelContainer.add(expensePiePanel);
        mainPanel.add(panelContainer, BorderLayout.CENTER);
        createDeleteListener();





    }

    //MODIFIES: this
    //EFFECTS: adds ListSelectionListener to income and expense category JLists
    private void initializeCatPanels() {
        incomeListListener = new CatListSelectionListener(incomeCatNames, incomeCatList, incomeCardLayout,
                incomePanelContainer, selectedCat, 1, currentCatLabel, deleteListener, deleteCatListener);
        incomeCatNames.addListSelectionListener(incomeListListener);
        expenseListListener = new CatListSelectionListener(expenseCatNames, expenseCatList, expenseCardLayout,
                expensePanelContainer, selectedCat, 0, currentCatLabel, deleteListener, deleteCatListener);
        expenseCatNames.addListSelectionListener(expenseListListener);
    }
}
