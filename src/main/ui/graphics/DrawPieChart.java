package ui.graphics;

import model.Budget;
import model.Category;
import model.CategoryList;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

//Represents a PieChart for each income and expense category list **NOT IN USE**
public class DrawPieChart extends JPanel {

    private Budget budget;
    private final int label;
    private CategoryList catList;
    private LinkedList<Category> cats;
    private static final int COLOR_INDEX = 1;
    private final int width;
    private final int height;
    private final int xcoord;
    private final int ycoord;
    private int startAngle;
    private final int diameter;
    private Double percentage;
    private final JPanel mainPanel;
    private int arcAngle;
    private Double amount;
    private Double totalForCat;

    //MODIFIES: this
    //CONSTRUCTOR
    public DrawPieChart(Budget budget, int label, JPanel mainPanel) {
        this.budget = budget;
        this.label = label;
        width = 200;
        height = 200;
        diameter = width - 20;
        xcoord = (width - diameter) / 2;
        ycoord = (height - diameter) / 2;
        startAngle = 0;
        this.mainPanel = mainPanel;
        percentage = 0.0;

    }

    //MODIFIES: this
    //EFFECTS: repaints pie chart
    public void update() {
        this.repaint();

        this.getParent().revalidate();
        this.getParent().repaint();
        mainPanel.repaint();
    }

    //SETTER
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    //MODIFIES: this
    //EFFECTS: Draws piechart based on categoryList
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkLabel();
        LocalDate current = LocalDate.now();
        LocalDate start = current.minusDays(365);
        totalForCat = catList.addTotalAmount(start, current);
        for (Category cat : cats) {
            String catName = cat.getName();
            calculateCat(totalForCat, cat, current, start);
            arcAngle = (int) Math.round(percentage * 360);

            g.setColor(getColorForCategory(cat));
            g.fillArc(xcoord, ycoord, diameter, diameter, startAngle, arcAngle);
            int labelAngle = startAngle + arcAngle / 2;
            double labelRadians = Math.toRadians(labelAngle);
            int labelX = xcoord + diameter / 2 + (int) (Math.cos(labelRadians) * diameter / 4);
            int labelY = ycoord + diameter / 2 + (int) (Math.sin(labelRadians) * diameter / 4);

            String labelText = String.format("%s (%.0f%%)", catName, percentage * 100);
            FontMetrics fm = g.getFontMetrics();
            int labelWidth = fm.stringWidth(labelText);
            int labelHeight = fm.getHeight();

            g.setColor(Color.BLACK);
            g.drawString(labelText, labelX - labelWidth / 2, labelY + labelHeight / 2);

            startAngle += arcAngle;
        }
    }


    //MODIFIES: this
    //EFFECTS: calculates percentage category makes up of total amount
    private void calculateCat(Double totalForCat, Category cat, LocalDate current, LocalDate start) {
        amount = cat.addTotalAmount(start, current);
        percentage = amount / totalForCat;
    }

    //MODIFIES: this
    //EFFECTS: checks if label is 0, if true sets catList to expenseCatList, else sets it to incomeCatList
    private void checkLabel() {
        if (label == 0) {
            catList = budget.getExpenses();
        } else {
            catList = budget.getIncomes();
        }
        cats = catList.getCatList();
    }

    //EFFECTS: Rotates through colours for category slice in chart
    private Color getColorForCategory(Category cat) {
        if (COLOR_INDEX % 3 == 0) {
            return Color.CYAN;
        } else if (COLOR_INDEX % 2 == 0) {
            return Color.PINK;
        } else {
            return Color.ORANGE;
        }
    }

}
