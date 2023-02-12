package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecurringIncomeTest {
    private IncomeCategory salary = new IncomeCategory("paycheck");
    private IncomeCategory renters = new IncomeCategory("renter's check");
    private RecurringIncome riTest1 = new RecurringIncome("paycheck", 1500, salary, "bi-weekly");
    private RecurringIncome riTest2 = new RecurringIncome("renter's payment", 1100, renters, "monthly");

    LocalDate endDate = LocalDate.of(2023, 6, 11);
    LocalDate startDate = LocalDate.of(2023, 2, 4);

    @Test
    void recurringIncomeTest() {
        assertEquals("paycheck", riTest1.getName());
        assertEquals(1500, riTest1.getAmount());
        assertEquals(salary, riTest1.getCat());
        assertEquals("bi-weekly", riTest1.getPeriod());

        assertEquals("renter's payment", riTest2.getName());
        assertEquals(1100, riTest2.getAmount());
        assertEquals(renters, riTest2.getCat());
        assertEquals("monthly", riTest2.getPeriod());

        riTest1.setCat(renters);
        assertEquals(renters, riTest1.getCat());


    }

    @Test
    void calculateRecurringIncomeTest() {

        riTest1.setStartDate(LocalDate.of(2023, 2, 8));
        riTest2.setStartDate(LocalDate.of(2023, 2, 8));

        assertEquals(8*1500, riTest1.calculate(startDate, endDate));
        assertEquals(4*1100, riTest2.calculate(startDate, endDate));
    }
}
