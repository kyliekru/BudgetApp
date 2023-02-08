package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SingleExpenseTest {

    private ExpenseCategory eatingOut = new ExpenseCategory("EatingOut");
    private ExpenseCategory schoolSupplies = new ExpenseCategory("SchoolSupplies");
    private SingleExpense seTest1;
    private SingleExpense seTest2;

    @BeforeEach
    void runBefore() {
        seTest1 = new SingleExpense("Coffee", eatingOut, 10);
        seTest2 = new SingleExpense("Calculator", schoolSupplies, 60);
    }

    @Test
    void singleExpenseTest() {
        assertEquals("Coffee", seTest1.getName());
        assertEquals(eatingOut, seTest1.getCat());
        assertEquals(10, seTest1.getAmount());
        assertEquals("Calculator", seTest2.getName());
        assertEquals(schoolSupplies, seTest2.getCat());
        assertEquals(60, seTest2.getAmount());
    }
}