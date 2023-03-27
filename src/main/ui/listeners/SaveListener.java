package ui.listeners;

import model.Budget;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveListener implements ActionListener {

    private static final String JSON_STORE = "./data/budget.json";
    private JsonWriter jsonWriter;
    private Budget budget;
    private JFrame frame;

    public SaveListener(JsonWriter jsonWriter, Budget budget, JFrame frame) {
        this.jsonWriter = jsonWriter;
        this.budget = budget;
        this.frame = frame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Saved " + budget.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException exception) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}