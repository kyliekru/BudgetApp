package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecurringExpenseTest {

    private RecurringExpense reTest1;
    private RecurringExpense reTest2;
    private RecurringExpense reTest3;
    private final ExpenseCategory utilities = new ExpenseCategory("utilities");
    private final ExpenseCategory rent = new ExpenseCategory("rent");
    private final LocalDate endDate = LocalDate.of(2023, 6, 11);
    private final LocalDate startDate = LocalDate.of(2023, 2, 4);
    private final LocalDate setDate = LocalDate.of(2023, 2, 8);
    private final LocalDate afterEndDate = LocalDate.of(2024, 4, 3);

    @BeforeEach
    void runBefore() {
        reTest1 = new RecurringExpense("Rent Payment", 1500, "monthly");
        reTest2 = new RecurringExpense("Water", 150, "bi-weekly");
        reTest3 = new RecurringExpense("stuff", 50, "weekly");
    }

    @Test
    void recurringExpenseTest() {
        assertEquals("Rent Payment", reTest1.getName());
        assertEquals(1500, reTest1.getAmount());
        assertEquals("monthly", reTest1.getPeriod());

        assertEquals("Water", reTest2.getName());
        assertEquals(150, reTest2.getAmount());
        assertEquals("bi-weekly", reTest2.getPeriod());

    }

    @Test
    void calculateRecurringExpenseTest() {

        reTest1.setDate(setDate);
        reTest2.setDate(setDate);
        assertEquals(5*1500, reTest1.calculate(startDate, endDate));
        assertEquals(9*150, reTest2.calculate(startDate, endDate));
        reTest2.setDate(afterEndDate);
        assertEquals(0, reTest2.calculate(startDate, endDate));


    }
}
