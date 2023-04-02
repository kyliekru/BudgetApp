package ui.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Represents a Click listener for income/expense panels
public class BoxListener extends MouseAdapter {

    private JPanel selection;
    private final DeleteIncomeExpenseListener delete;

    //CONSTRUCTOR
    public BoxListener(JPanel selection, DeleteIncomeExpenseListener delete) {
        this.selection = selection;
        this.delete = delete;
    }

    //MODIFIES: mainPanel, income/expensePanel
    //EFFECTS: set selection to clicked panel, change its color to grey, and revert previous selections colour
    public void mouseClicked(MouseEvent me) {
        JPanel clickedBox = (JPanel)me.getSource();
        selection.setBackground(Color.white);
        selection = clickedBox;
        selection.setBackground(Color.gray);
        delete.setSelection(selection);

    }
}
