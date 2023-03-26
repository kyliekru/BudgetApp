package ui;

import model.*;
import ui.listeners.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
        incomePanelArray = new LinkedList<>();
        expensePanelArray = new LinkedList<>();
        selection = new JPanel();
        incomeCatMap = new HashMap<>();
        expenseCatMap = new HashMap<>();
        incomePanelList = new LinkedList<>();
        expensePanelList = new LinkedList<>();
        deleteListener = new DeleteIncomeExpenseListener(budget, selectedCat, currentCatLabel, isRecurring);
    }

    private void initializeGraphics() {
        frame = new JFrame("Budget App");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        initializePanels();
        initializeCatPanels();
        initializeButtons();
    }

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
        addExpenseButton.addActionListener(new AddListener(budget, expenseCatNames, expenseCatMap, 0, selection,
                deleteListener, mainPanel, isRecurring, expensePanelList));
        deleteExpenseButton = new JButton("Delete");
        deleteExpenseButton.addActionListener(deleteListener);
        expenseButtonsPanel.add(addExpenseButton, BorderLayout.LINE_START);
        expenseButtonsPanel.add(deleteExpenseButton, BorderLayout.LINE_END);
        expenseCatsPanel.add(expenseButtonsPanel, BorderLayout.PAGE_END);




    }

    private void setIncomeAndCatButtons(JButton addCategory, JButton addIncomeButton, JButton deleteCat) {
        addCategory.setPreferredSize(new Dimension(90, 40));
        addCategory.addActionListener(new AddCatListener(expenseCatsPanel, incomeCatsPanel, budget,
                expenseModel, incomeModel, incomeCatNames, expenseCatNames, mainPanel,
                incomePanelContainer, expensePanelContainer, incomePanelArray, expensePanelArray, selection,
                expenseCatMap, incomeCatMap, incomePanelList, expensePanelList));
        addIncomeButton.addActionListener(new AddListener(budget, incomeCatNames, incomeCatMap, 1, selection,
                deleteListener, mainPanel, isRecurring, incomePanelList));
        deleteCat.addActionListener(deleteCatListener);
        JPanel catButtonPanel = new JPanel();
        catButtonPanel.setLayout(new FlowLayout());
        catButtonPanel.add(addCategory, BorderLayout.CENTER);
        catButtonPanel.add(deleteCategory);
        mainPanel.add(catButtonPanel, BorderLayout.PAGE_START);
    }

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

    private void createDeleteListener() {
        deleteCatListener = new DeleteCatListener(budget, incomeCatNames, incomePanelArray, expenseCatNames,
                expensePanelArray, currentCatLabel, selectedCat, incomePanelList, expensePanelList, incomeCardLayout,
                expenseCardLayout, mainPanel, incomeModel, expenseModel);
    }

    private void addCatContainers(JPanel incomePanelContainer, JPanel expensePanelContainer) {
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayout(1, 2));
        panelContainer.add(incomePanelContainer);
        panelContainer.add(expensePanelContainer);
        mainPanel.add(panelContainer, BorderLayout.CENTER);
        createDeleteListener();





    }

    private void initializeCatPanels() {
        incomeCatNames.addListSelectionListener(new
                CatListSelectionListener(incomeCatNames, incomeCatList, incomeCardLayout,
                incomePanelContainer, selectedCat, 1, currentCatLabel, deleteListener, deleteCatListener));
        expenseCatNames.addListSelectionListener(new
                CatListSelectionListener(expenseCatNames, expenseCatList, expenseCardLayout, expensePanelContainer,
                selectedCat, 0, currentCatLabel, deleteListener, deleteCatListener));
    }
}
