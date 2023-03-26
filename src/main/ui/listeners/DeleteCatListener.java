package ui.listeners;

import model.Budget;
import model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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


    public DeleteCatListener(Budget budget, JList incomeList, LinkedList<JPanel> incomePanels,
                             JList expenseList, LinkedList<JPanel> expensePanels, int catLabel, Category selectedCat,
                             LinkedList<JPanel> incomePanelList, LinkedList<JPanel> expensePanelList,
                             CardLayout incomeCardLayout, CardLayout expenseCardLayout, JPanel mainPanel) {
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


    }

    public void setSelectedCat(Category cat) {
        this.selectedCat = cat;
    }

    public void setCatLabel(int label) {
        this.catLabel = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (catLabel == 0) {
            index = expenseList.getSelectedIndex();
            currentPanel = expenseCatPanels.get(index);
            expenseCardLayout.removeLayoutComponent(currentPanel);
            expenseList.remove(index);
            expensePanelList.remove(index + 1);
            expensePanelList.remove(index);
            int id = selectedCat.getID();
            budget.getExpenses().removeCat(id);

        } else {
            index = incomeList.getSelectedIndex();
            currentPanel = incomeCatPanels.get(index);
            incomeCardLayout.removeLayoutComponent(currentPanel);
            incomePanelList.remove(index);
            incomePanelList.remove(index + 1);
            int id = selectedCat.getID();
            budget.getIncomes().removeCat(id);
        }
        mainPanel.revalidate();
        mainPanel.repaint();

    }
}
