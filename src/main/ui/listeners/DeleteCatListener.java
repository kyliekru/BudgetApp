package ui.listeners;

import model.Budget;
import model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

//Represents an ActionListener for deleteCategory button; deletes category currently selected
public class DeleteCatListener implements ActionListener {

    private Budget budget;
    private JList<String> incomeList;
    private JList<String> expenseList;
    private LinkedList<JPanel> incomeCatPanels;
    private LinkedList<JPanel> expenseCatPanels;
    private int catLabel;
    private Category selectedCat;
    private LinkedList<JPanel> incomePanelList;
    private LinkedList<JPanel> expensePanelList;
    private int index;
    private CardLayout incomeCardLayout;
    private CardLayout expenseCardLayout;
    private JPanel currentPanel;
    private JPanel mainPanel;
    private DefaultListModel expenseModel;
    private DefaultListModel incomeModel;



    //CONSTRUCTOR
    public DeleteCatListener(Budget budget, JList incomeList, LinkedList<JPanel> incomePanels,
                             JList expenseList, LinkedList<JPanel> expensePanels, int catLabel, Category selectedCat,
                             LinkedList<JPanel> incomePanelList, LinkedList<JPanel> expensePanelList,
                             CardLayout incomeCardLayout, CardLayout expenseCardLayout, JPanel mainPanel,
                             DefaultListModel incomeModel, DefaultListModel expenseModel) {
        this.budget = budget;
        this.incomeList = incomeList;
        this.expenseList = expenseList;
        this.incomeCatPanels = incomePanels;
        this.expenseCatPanels = expensePanels;
        this.catLabel = catLabel;
        this.selectedCat = selectedCat;
        this.incomePanelList = incomePanelList;
        this.expensePanelList = expensePanelList;
        index = 0;
        this.incomeCardLayout = incomeCardLayout;
        this.expenseCardLayout = expenseCardLayout;
        this.mainPanel = mainPanel;
        this.expenseModel = expenseModel;
        this.incomeModel = incomeModel;


    }

    //SETTERS
    public void setSelectedCat(Category cat) {
        this.selectedCat = cat;
    }

    public void setCatLabel(int label) {
        this.catLabel = label;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    //MODIFIES: this, mainPanel, budget
    //EFFECTS: remove currently selected panel; deleted selected category from budget
    @Override
    public void actionPerformed(ActionEvent e) {
        if (catLabel == 0) {
            index = expenseList.getSelectedIndex();
            currentPanel = expenseCatPanels.get(index);
            Container parent = currentPanel.getParent();
            expenseCardLayout.removeLayoutComponent(currentPanel);
            parent.remove(currentPanel);
            expenseModel.remove(index);
            expenseList.getParent().revalidate();
            expenseList.getParent().repaint();
            expensePanelList.remove(index + 1);
            expensePanelList.remove(index);
            int id = selectedCat.getID();
            budget.getExpenses().removeCat(id);


        } else {
            deleteIncome();
        }


    }

    //MODIFIES: this, mainPanel, budget
    //EFFECTS: deletes income category from budget and removes panel
    private void deleteIncome() {
        index = incomeList.getSelectedIndex();
        currentPanel = incomeCatPanels.get(index);
        Container parent = currentPanel.getParent();
        incomeCardLayout.removeLayoutComponent(currentPanel);
        parent.remove(currentPanel);
        incomeModel.remove(index);
        incomeList.getParent().revalidate();
        incomeList.getParent().repaint();
        incomePanelList.remove(index + 1);
        incomePanelList.remove(index);
        int id = selectedCat.getID();
        budget.getIncomes().removeCat(id);
    }
}
