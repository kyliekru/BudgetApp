package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecurringIncomeTest {
    private IncomeCategory salary = new IncomeCategory("paycheck");
    private IncomeCategory renters = new IncomeCategory("renter's check");
    private RecurringIncome riTest1 = new RecurringIncome("paycheck", 1500, salary, "bi-weekly");
    private RecurringIncome riTest2 = new RecurringIncome("renter's", 1100, renters, "monthly");
    private RecurringIncome riTest3 = new RecurringIncome("fun money", 50, salary, "monthly");

    private LocalDate endDate = LocalDate.of(2023, 6, 11);
    private LocalDate startDate = LocalDate.of(2023, 2, 4);
    private LocalDate setDate = LocalDate.of(2023, 2, 8);
    private LocalDate startDate2 = LocalDate.of(2023, 6, 7);
    private LocalDate endDate2 = LocalDate.of(2023, 5, 6);


    @Test
    void recurringIncomeTest() {
        assertEquals("paycheck", riTest1.getName());
        assertEquals(1500, riTest1.getAmount());
        assertEquals(salary, riTest1.getCat());
        assertEquals("bi-weekly", riTest1.getPeriod());

        assertEquals("renter's", riTest2.getName());
        assertEquals(1100, riTest2.getAmount());
        assertEquals(renters, riTest2.getCat());
        assertEquals("monthly", riTest2.getPeriod());

        riTest1.setCat(renters);
        assertEquals(renters, riTest1.getCat());


    }

    @Test
    void calculateRecurringIncomeTest() {
        riTest3.setDate(startDate2);
        assertEquals(0, riTest3.calculate(startDate, endDate2));
        riTest1.setDate(setDate);
        riTest2.setDate(LocalDate.of(2023, 2, 3));

        assertEquals(8*1500, riTest1.calculate(startDate, endDate));
        assertEquals(4*1100, riTest2.calculate(startDate, endDate));

        assertEquals(0, riTest2.calculate(startDate2, endDate));

    }
}
