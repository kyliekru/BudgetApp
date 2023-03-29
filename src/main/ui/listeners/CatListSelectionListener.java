package ui.listeners;

import model.Category;
import model.CategoryList;
import model.ExpenseCategory;
import model.IncomeCategory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

//Represents a ListSelectionListener for income/expense JLists
public class CatListSelectionListener implements ListSelectionListener {

    private JList<String> categoryList;
    private CardLayout cardLayout;
    private JPanel container;
    private CategoryList list;
    private Category selectedCat;
    private int label;
    private int currLabel;
    private DeleteIncomeExpenseListener delete;
    private DeleteCatListener deleteCat;

    //CONSTRUCTOR
    public CatListSelectionListener(JList<String> catList, CategoryList list,
                                    CardLayout cardLayout, JPanel container, Category selectedCat, int label,
                                    int currLabel, DeleteIncomeExpenseListener delete, DeleteCatListener deleteCat) {
        this.cardLayout = cardLayout;
        this.categoryList = catList;
        this.container = container;
        this.list = list;
        this.selectedCat = selectedCat;
        this.label = label;
        this.currLabel = currLabel;
        this.delete = delete;
        this.deleteCat = deleteCat;
    }

    public void setLists(JList<String> catList, CategoryList list) {
        this.categoryList = catList;
        this.list = list;
    }

    //MODIFIES: this
    //EFFECTS: set selectedCategory to JList selection
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedCategory = categoryList.getSelectedValue();
            int categoryID = categoryList.getSelectedIndex();
            if (categoryID < 0) {
                categoryID = 0;
            }
            if (label == 0) {
                selectedCat = (ExpenseCategory) list.getCatList().get(categoryID);
                currLabel = 0;
            } else {
                selectedCat = (IncomeCategory) list.getCatList().get(categoryID);
                currLabel = 1;
            }

            cardLayout.show(container, selectedCategory);
            delete.setCurrentCat(selectedCat);
            deleteCat.setSelectedCat(selectedCat);
            deleteCat.setCatLabel(currLabel);
        }

    }
}
