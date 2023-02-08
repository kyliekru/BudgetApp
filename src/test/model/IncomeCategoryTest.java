package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class IncomeCategoryTest {

    private IncomeCategory icTest1 = new IncomeCategory("salary");
    private IncomeCategory icTest2 = new IncomeCategory("odd jobs");
    private RecurringIncome helper = new RecurringIncome("salary", 1500, icTest1, "bi-weekly");
    private RecurringIncome helper2 = new RecurringIncome("serving job", 500, icTest1, "bi-weekly");
    private SingleIncome singleHelper = new SingleIncome("washed windows", icTest2, 100);
    private SingleIncome singleHelper2 = new SingleIncome("washed car", icTest2, 50);
    private RecurringIncome helper3 = new RecurringIncome("babysitting", 50, icTest2, "weekly");

    LocalDate endDate = LocalDate.of(2023, 6, 11);
    LocalDate startDate = LocalDate.of(2023, 2, 4);
    @Test
    public void incomeCategoryTest() {
        assertEquals(emptyList(), icTest1.getRecurringIncomeList());
        assertEquals(emptyList(), icTest2.getSingleIncomeList());
        assertEquals("salary", icTest1.getName());
        assertEquals("odd jobs", icTest2.getName());

    }

    @Test
    public void addRecurringIncomeTest() {
        icTest1.addRecurringIncome(helper);
        icTest1.addRecurringIncome(helper2);
        List list = icTest1.getRecurringIncomeList();
        assertEquals(helper, list.get(1));
        assertEquals(helper2, list.get(0));


    }

    @Test
    public void addSingleIncomeTest() {
        icTest2.addSingleIncome(singleHelper);
        icTest2.addSingleIncome(singleHelper2);

        List list = icTest2.getSingleIncomeList();

        assertEquals(singleHelper, list.get(1));
        assertEquals(singleHelper2, list.get(0));
    }

    @Test
    public void addTotalAmountTest() {
        icTest2.addSingleIncome(singleHelper);
        icTest2.addSingleIncome(singleHelper2);
        icTest2.addRecurringIncome(helper3);

        assertEquals(1000, icTest2.addTotalAmount(startDate, endDate));

        icTest1.addRecurringIncome(helper);
        icTest1.addRecurringIncome(helper2);

        assertEquals(16000, icTest1.addTotalAmount(startDate, endDate));

    }



}
