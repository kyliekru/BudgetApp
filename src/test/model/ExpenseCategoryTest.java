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

    private SingleExpense eatingOut;
    private RecurringExpense hairProducts;
    private RecurringExpense makeup;

    private LocalDate endDate;
    private LocalDate startDate;

    @BeforeEach
    void runBefore() {
        ecTest1 = new ExpenseCategory("food");
        ecTest2 = new ExpenseCategory("personal products");

        eatingOut = new SingleExpense("Dinner & Drinks", ecTest1, 75);
        hairProducts = new RecurringExpense("Shampoo&Conditioner", ecTest2, 50, "monthly");
        makeup = new RecurringExpense("makeup", ecTest2, 50, "monthly");

        endDate = LocalDate.of(2023, 5, 12);
        startDate = LocalDate.of(2023, 2, 4);
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
        List list = ecTest1.getSingle();
        assertEquals(eatingOut, list.get(0));
    }

    @Test
    void addTotalAmountTest() {
        ecTest1.addSingle(eatingOut);
        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);


        assertEquals(75, ecTest1.addTotalAmount(startDate, endDate));
        assertEquals(300, ecTest2.addTotalAmount(startDate, endDate));
    }

    @Test
    void removeSingleExpenseTest() {
        ecTest1.addSingle(eatingOut);


        ecTest1.removeSingle(15);
        assertEquals(emptyList(), ecTest1.getSingle());
    }

    @Test
    void removeRecurringExpenseTest() {
        ecTest2.addRecurring(hairProducts);
        ecTest2.addRecurring(makeup);



        ecTest2.removeRecurring(20);

        List list = ecTest2.getRecurring();
        assertEquals(hairProducts, list.get(0));
        ecTest2.removeRecurring(19);
        List list1 = ecTest2.getRecurring();
        assertEquals(emptyList(), list1);

        assertFalse(ecTest1.removeRecurring(50));
    }
}
