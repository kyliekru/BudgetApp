package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SingleExpenseTest {

    private final ExpenseCategory eatingOut = new ExpenseCategory("EatingOut");
    private final ExpenseCategory schoolSupplies = new ExpenseCategory("SchoolSupplies");
    private SingleExpense seTest1;
    private SingleExpense seTest2;
    private final LocalDate startDate = LocalDate.of(2023, 02, 4);
    private final LocalDate endDate = LocalDate.of(2023, 6, 11);
    private final LocalDate setDate = LocalDate.of(2023, 7, 8);
    private final LocalDate currentDate = LocalDate.of(2023, 2, 13);
    private final LocalDate setDate2 = LocalDate.of(2023, 1, 1);

    @BeforeEach
    void runBefore() {
        seTest1 = new SingleExpense("Coffee", 10);
        seTest2 = new SingleExpense("Calculator", 60);
    }

    @Test
    void singleExpenseTest() {
        assertEquals("Coffee", seTest1.getName());
        assertEquals(10, seTest1.getAmount());
        assertEquals(java.time.LocalDate.now(), seTest1.getDate());

        assertEquals("Calculator", seTest2.getName());
        assertEquals(60, seTest2.getAmount());
        assertEquals(java.time.LocalDate.now(), seTest2.getDate());

        seTest1.setName("Drinks");
        assertEquals("Drinks", seTest1.getName());

        seTest1.setAmount(7.50);
        assertEquals(7.50, seTest1.getAmount());
    }


    @Test
    void calculateTest() {
        seTest1.setDate(currentDate);
        assertEquals(10, seTest1.calculate(startDate, endDate));
        seTest1.setDate(setDate);
        assertEquals(0, seTest1.calculate(startDate, endDate));
        seTest2.setDate(setDate2);
        assertEquals(0, seTest2.calculate(startDate, endDate));
    }
}