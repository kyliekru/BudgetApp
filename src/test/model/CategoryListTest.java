package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class CategoryListTest {

    private IncomeCategory allowance;
    private IncomeCategory salary;
    private ExpenseCategory utilities;
    private ExpenseCategory rent;
    private CategoryList clTest1;
    private CategoryList clTest2;
    private SingleExpense eatingOut;
    private RecurringExpense hairProducts;
    private final LocalDate startDate = LocalDate.of(2023, 2, 7);
    private final LocalDate endDate = LocalDate.of(2023, 6, 11);
    private final LocalDate setDate = LocalDate.of(2023, 2, 8);





    @BeforeEach
    void runBefore() {
        allowance = new IncomeCategory("allowance");
        salary = new IncomeCategory("paychecks");
        utilities = new ExpenseCategory("utility bills");
        rent = new ExpenseCategory("rent payments");
        clTest1 = new CategoryList(0);
        clTest2 = new CategoryList(1);
        eatingOut = new SingleExpense("Dinner & Drinks", 75);
        hairProducts = new RecurringExpense("Shampoo&Conditioner", 50, "monthly");


    }

    @Test
    void categoryListTest() {
        assertEquals(0, clTest1.getLabel());
        assertEquals(emptyList(), clTest1.getCatList());
        assertEquals(1, clTest2.getLabel());
        assertEquals(emptyList(), clTest2.getCatList());

        clTest1.setLabel(1);
        assertEquals(1, clTest1.getLabel());
    }

    @Test
    void addCatTest() {
        clTest1.addCat(utilities);
        clTest1.addCat(rent);

        List list = clTest1.getCatList();
        assertEquals(rent, list.get(1));
        assertEquals(utilities, list.get(0));

        clTest2.addCat(allowance);
        clTest2.addCat(salary);

        List list1 = clTest2.getCatList();
        assertEquals(allowance, list1.get(0));
        assertEquals(salary, list1.get(1));
    }

    @Test
    void removeCatTest() {
        clTest1.addCat(utilities);
        clTest1.addCat(rent);

        clTest2.addCat(allowance);
        clTest2.addCat(salary);

        clTest1.removeCat(rent.getID());

        clTest2.removeCat(allowance.getID());
        clTest2.removeCat(salary.getID());

        LinkedList list = clTest1.getCatList();


        assertEquals(emptyList(), clTest2.getCatList());
        assertEquals(utilities, list.get(0));
        assertFalse(clTest1.removeCat(30));

    }

    @Test
    void addTotalAmountTest() {
        utilities.addSingle(eatingOut);
        rent.addRecurring(hairProducts);

        clTest1.addCat(utilities);
        clTest1.addCat(rent);

        eatingOut.setDate(LocalDate.of(2023, 2, 8));
        hairProducts.setDate(LocalDate.of(2023, 2, 8));

        assertEquals(325, clTest1.addTotalAmount(startDate, endDate));


    }


}
