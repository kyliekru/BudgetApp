package ui.listeners;

import model.Budget;
import model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteIncomeExpenseListener implements ActionListener {

    private JPanel selection;
    private Budget budget;
    private int index;
    private Category currentCat;
    private int label;
    private boolean isRecurring;

    public DeleteIncomeExpenseListener(Budget budget, Category currentCat, int label, boolean isRecurring) {
        this.budget = budget;
        this.currentCat = currentCat;
        this.label = label;
        this.isRecurring = isRecurring;

    }


    public void setSelection(JPanel newSelection) {
        this.selection = newSelection;
    }

    public void setCurrentCat(Category cat) {
        this.currentCat = cat;
    }

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Container parent = selection.getParent();
        parent.remove(selection);
        Component[] containers = parent.getComponents();
        Component[] selectionContainers = selection.getComponents();
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
        if (isRecurring) {
            currentCat.removeRecurring(index + 1);
        } else {
            currentCat.removeSingle(index + 1);
        }

        parent.revalidate();
        parent.repaint();
    }
}
