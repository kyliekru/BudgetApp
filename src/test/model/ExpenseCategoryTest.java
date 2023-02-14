package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseCategoryTest {

    private ExpenseCategory ecTest1;
    private ExpenseCategory ecTest2;
    private ExpenseCategory ecTest3;

    private SingleExpense eatingOut;
    private SingleExpense coffee;
    private RecurringExpense hairProducts;
    private RecurringExpense makeup;

    private LocalDate endDate;
    private LocalDate startDate;
    private LocalDate setDate;

    @BeforeEach
    void runBefore() {
        ecTest1 = new ExpenseCategory("food");
        ecTest2 = new ExpenseCategory("personal products");
        ecTest3 = new ExpenseCategory("bills");


        eatingOut = new SingleExpense("Dinner & Drinks", ecTest1, 75);
        hairProducts = new RecurringExpense("Shampoo&Conditioner", ecTest2, 50, "monthly");
        makeup = new RecurringExpense("makeup", ecTest2, 50, "weekly");
        coffee = new SingleExpense("Starbucks", ecTest1, 12);

        endDate = LocalDate.of(2023, 5, 12);
        startDate = LocalDate.of(2023, 2, 4);
        setDate = LocalDate.of(2023, 2, 8);

    }

    @Test
    void expenseCategoryTest() {
        assertEquals("food", ecTest1.getName());
        assertEquals("personal products", ecTest2.getName());
        assertEquals(emptyList(), ecTest1.getRecurring());
        assertEquals(emptyList(), ecTest1.getSingle());

        ecTest1.setName("eating");
        assertEquals("eating", ecTest1.getName());
    }

    @Test
    void addRecurringExpenseTest() {

        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);

        List list = ecTest2.getRecurring();
        assertEquals(hairProducts, list.get(0));
        assertEquals(makeup, list.get(1));

    }

    @Test
    void addSingleExpenseTest() {
        ecTest1.addSingle(eatingOut);
        ecTest1.addSingle(coffee);
        List list = ecTest1.getSingle();
        assertEquals(eatingOut, list.get(0));
        assertEquals(coffee, list.get(1));
    }

    @Test
    void addTotalAmountTest() {
        eatingOut.setDate(setDate);
        hairProducts.setDate(setDate);
        makeup.setDate(setDate);
        ecTest1.addSingle(eatingOut);
        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);
        ecTest1.addSingle(coffee);
        ecTest2.addSingle(coffee);


        assertEquals(87, ecTest1.addTotalAmount(startDate, endDate));
        assertEquals(812, ecTest2.addTotalAmount(startDate, endDate));

        setDate = LocalDate.of(2023, 6, 12);
        eatingOut.setDate(setDate);
        assertEquals(12, ecTest1.addTotalAmount(startDate, endDate));
        coffee.setDate(setDate);
        assertEquals(0, ecTest1.addTotalAmount(startDate, endDate));
        assertEquals(0, ecTest3.addTotalAmount(startDate, endDate));
        ecTest2.addSingle(eatingOut);
        assertEquals(800, ecTest2.addTotalAmount(startDate, endDate));
    }

    @Test
    void removeSingleExpenseTest() {
        ecTest1.addSingle(eatingOut);
        ecTest1.addRecurring(hairProducts);
        ecTest1.addRecurring(makeup);
        ecTest1.addSingle(coffee);

        assertTrue(ecTest1.removeSingle(24));

        assertEquals(eatingOut, ecTest1.getSingle().get(0));
        assertFalse(ecTest1.removeSingle(25));
        assertFalse(ecTest3.removeSingle(1));
    }

    @Test
    void removeRecurringExpenseTest() {
        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);



        ecTest2.removeRecurring(27);

        List list = ecTest2.getRecurring();
        assertEquals(hairProducts, list.get(0));
        assertTrue(ecTest2.removeRecurring(26));
        List list1 = ecTest2.getRecurring();
        assertEquals(emptyList(), list1);

        assertFalse(ecTest1.removeRecurring(50));
        assertFalse(ecTest3.removeRecurring(23));
    }

    @Test
    void addSingleAmountTest() {
        coffee.setDate(setDate);
        ecTest1.addSingle(coffee);
        eatingOut.setDate(setDate);
        ecTest1.addSingle(eatingOut);
        ecTest1.addRecurring(hairProducts);
        assertEquals(87, ecTest1.addSingleAmount(startDate, endDate));
        assertEquals(0, ecTest2.addSingleAmount(startDate, endDate));
    }

    @Test
    void addRecurringAmountTest() {
        hairProducts.setDate(setDate);
        makeup.setDate(setDate);
        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);
        ecTest2.addSingle(eatingOut);
        assertEquals(800, ecTest2.addRecurringAmount(startDate, endDate));
        assertEquals(0, ecTest1.addRecurringAmount(startDate, endDate));

    }


}
