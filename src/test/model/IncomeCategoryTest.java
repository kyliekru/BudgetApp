package model;

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
        assertEquals(emptyList(), icTest1.getRecurring());
        assertEquals(emptyList(), icTest2.getSingle());
        assertEquals("salary", icTest1.getName());
        assertEquals("odd jobs", icTest2.getName());

        icTest1.setName("paycheck");
        assertEquals("paycheck", icTest1.getName());

    }

    @Test
    public void addRecurringIncomeTest() {
        icTest1.addRecurring(helper);
        icTest1.addRecurring(helper2);
        List list = icTest1.getRecurring();
        assertEquals(helper, list.get(0));
        assertEquals(helper2, list.get(1));


    }

    @Test
    public void addSingleIncomeTest() {
        icTest2.addSingle(singleHelper);
        icTest2.addSingle(singleHelper2);

        List list = icTest2.getSingle();

        assertEquals(singleHelper, list.get(0));
        assertEquals(singleHelper2, list.get(1));
    }

    @Test
    public void addTotalAmountTest() {
        icTest2.addSingle(singleHelper);
        icTest2.addSingle(singleHelper2);
        icTest2.addRecurring(helper3);

        assertEquals(1000, icTest2.addTotalAmount(startDate, endDate));

        icTest1.addRecurring(helper);
        icTest1.addRecurring(helper2);

        assertEquals(16000, icTest1.addTotalAmount(startDate, endDate));

    }

    @Test
    void removeSingleIncomeTest() {
        icTest2.addSingle(singleHelper);
        assertEquals(singleHelper, icTest2.getSingle().get(0));




        assertTrue(icTest2.removeSingle(44));
        assertEquals(emptyList(), icTest2.getSingle());
        assertFalse(icTest2.removeSingle(1));
    }

    @Test
    void removeRecurringIncomeTest() {
        icTest1.addRecurring(helper);
        assertEquals(helper, icTest1.getRecurring().get(0));

        assertTrue(icTest1.removeRecurring(37));
        assertEquals(emptyList(), icTest1.getRecurring());
        assertFalse(icTest1.removeRecurring(2));
    }



}
