package ui.listeners;

import model.Budget;
import model.Category;
import model.ExpenseCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

//Represents an ActionListener for delete income/expense button; removes income/expense
public class DeleteIncomeExpenseListener implements ActionListener {

    private JPanel selection;
    private Budget budget;
    private int index;
    private Category currentCat;
    private final int label;
    private boolean isRecurring;
    private final Map<String, Integer> incomeMap;
    private final Map<String, Integer> expenseMap;

    //CONSTRUCTOR
    public DeleteIncomeExpenseListener(Budget budget, Category currentCat, int label, boolean isRecurring,
                                       Map<String, Integer> incomeMap, Map<String, Integer> expenseMap) {
        this.budget = budget;
        this.currentCat = currentCat;
        this.label = label;
        this.isRecurring = isRecurring;
        this.incomeMap = incomeMap;
        this.expenseMap = expenseMap;

    }



    //SETTERS
    public void setSelection(JPanel newSelection) {
        this.selection = newSelection;
    }

    public void setCurrentCat(Category cat) {
        this.currentCat = cat;
    }

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }



    //MODIFIES: this, budget, mainPanel
    //EFFECTS: removes income/expense panel and deletes it from budget
    @Override
    public void actionPerformed(ActionEvent e) {
        Container parent = selection.getParent();
        parent.remove(selection);
        Component[] containers = parent.getComponents();
        Component[] selectionContainers = selection.getComponents();
        JLabel name = (JLabel) selectionContainers [0];
        String itemName = name.getText();
        if (selectionContainers.length > 2) {
            isRecurring = true;
        }
        index = 0;
        for (int i = 0; i < containers.length; i++) {
            if (containers[i].equals(selection)) {
                return;
            }
            index++;
        }
        removeItem(itemName);

        parent.revalidate();
        parent.repaint();
    }

    //MODIFIES: this, budget, currentCat
    //EFFECTS: removes selected income/expense from selected category
    private void removeItem(String itemName) {
        if (isRecurring) {
            if (currentCat instanceof ExpenseCategory) {
                currentCat.removeRecurring(expenseMap.get(itemName));
            } else {
                currentCat.removeRecurring(incomeMap.get(itemName));
            }
        } else {
            if (currentCat instanceof ExpenseCategory) {
                currentCat.removeSingle(expenseMap.get(itemName));
            } else {
                currentCat.removeSingle(incomeMap.get(itemName));
            }
        }
    }
}
