package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class IncomeCategoryTest {

    private final IncomeCategory icTest1 = new IncomeCategory("salary");
    private final IncomeCategory icTest2 = new IncomeCategory("odd jobs");
    private final IncomeCategory icTest3 = new IncomeCategory("stuff");
    private final RecurringIncome helper = new RecurringIncome("salary", 1500, "bi-weekly");
    private final RecurringIncome helper2 = new RecurringIncome("serving job", 500, "bi-weekly");
    private final SingleIncome singleHelper = new SingleIncome("washed windows", 100);
    private final SingleIncome singleHelper2 = new SingleIncome("washed car", 50);
    private final RecurringIncome helper3 = new RecurringIncome("babysitting", 50, "weekly");

    private final LocalDate endDate = LocalDate.of(2023, 6, 11);
    private final LocalDate startDate = LocalDate.of(2023, 2, 4);
    private LocalDate setDate = LocalDate.of(2023, 2, 8);
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
        singleHelper.setDate(setDate);
        singleHelper2.setDate(setDate);
        helper3.setDate(setDate);

        icTest2.addSingle(singleHelper);
        icTest2.addSingle(singleHelper2);
        icTest2.addRecurring(helper3);

        assertEquals(1050, icTest2.addTotalAmount(startDate, endDate));

        icTest1.addRecurring(helper);
        icTest1.addRecurring(helper2);

        assertEquals(18000, icTest1.addTotalAmount(startDate, endDate));

        setDate = LocalDate.of(2023, 7, 11);
        singleHelper.setDate(setDate);
        singleHelper2.setDate(setDate);
        assertEquals(900, icTest2.addTotalAmount(startDate, endDate));
        assertEquals(0, icTest3.addTotalAmount(startDate, endDate));

    }

    @Test
    void removeSingleIncomeTest() {
        icTest2.addSingle(singleHelper);
        icTest2.addSingle(singleHelper2);
        assertEquals(singleHelper, icTest2.getSingle().get(0));


        assertTrue(icTest2.removeSingle(63));
        assertEquals(singleHelper2, icTest2.getSingle().get(0));
        assertFalse(icTest2.removeSingle(1));
        assertFalse(icTest3.removeSingle(1));
    }

    @Test
    void removeRecurringIncomeTest() {
        icTest1.addRecurring(helper);
        icTest1.addRecurring(helper2);
        assertEquals(helper, icTest1.getRecurring().get(0));

        assertTrue(icTest1.removeRecurring(56));
        assertEquals(helper2, icTest1.getRecurring().get(0));
        assertFalse(icTest1.removeRecurring(2));
        assertFalse(icTest3.removeRecurring(4));
    }

    @Test
    void addSingleAmountTest() {
        singleHelper.setDate(setDate);
        singleHelper2.setDate(setDate);
        helper2.setDate(setDate);
        assertEquals(0, icTest3.addSingleAmount(startDate, endDate));
        icTest2.addSingle(singleHelper);
        icTest2.addSingle(singleHelper2);
        icTest2.addRecurring(helper2);
        assertEquals(150, icTest2.addSingleAmount(startDate, endDate));
    }

    @Test
    void addRecurringAmountTest() {
        singleHelper.setDate(setDate);
        helper.setDate(setDate);
        helper2.setDate(setDate);
        assertEquals(0, icTest3.addRecurringAmount(startDate, endDate));
        icTest1.addSingle(singleHelper);
        icTest2.addRecurring(helper);
        icTest2.addRecurring(helper2);
        assertEquals(18000, icTest2.addRecurringAmount(startDate, endDate));


    }



}
