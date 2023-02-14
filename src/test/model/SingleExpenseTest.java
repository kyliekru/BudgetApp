package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SingleExpenseTest {

    private ExpenseCategory eatingOut = new ExpenseCategory("EatingOut");
    private ExpenseCategory schoolSupplies = new ExpenseCategory("SchoolSupplies");
    private SingleExpense seTest1;
    private SingleExpense seTest2;
    private LocalDate startDate = LocalDate.of(2023, 02, 4);
    private LocalDate endDate = LocalDate.of(2023, 6, 11);
    private LocalDate setDate = LocalDate.of(2023, 7, 8);
    private LocalDate currentDate = LocalDate.of(2023, 2, 13);
    private LocalDate setDate2 = LocalDate.of(2023, 1, 1);

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
        assertEquals(java.time.LocalDate.now(), seTest1.getDate());

        assertEquals("Calculator", seTest2.getName());
        assertEquals(schoolSupplies, seTest2.getCat());
        assertEquals(60, seTest2.getAmount());
        assertEquals(java.time.LocalDate.now(), seTest2.getDate());

        seTest1.setName("Drinks");
        assertEquals("Drinks", seTest1.getName());

        seTest1.setAmount(7.50);
        assertEquals(7.50, seTest1.getAmount());
    }

    @Test
    void setCatTest() {
        seTest1.setCat(schoolSupplies);
        assertEquals(schoolSupplies, seTest1.getCat());

        seTest2.setCat(eatingOut);
        assertEquals(eatingOut, seTest2.getCat());
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