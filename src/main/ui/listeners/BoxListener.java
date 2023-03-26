package ui.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoxListener extends MouseAdapter {

    private JPanel selection;
    private DeleteIncomeExpenseListener delete;

    public BoxListener(JPanel selection, DeleteIncomeExpenseListener delete) {
        this.selection = selection;
        this.delete = delete;
    }

    public void mouseClicked(MouseEvent me) {
        JPanel clickedBox = (JPanel)me.getSource();
        selection.setBackground(Color.white);
        selection = clickedBox;
        selection.setBackground(Color.gray);
        delete.setSelection(selection);

    }
}
