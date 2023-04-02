package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SingleIncomeTest {

    private IncomeCategory soldItem;
    private IncomeCategory oddJobs;
    private SingleIncome siTest1;
    private SingleIncome siTest2;
    private final LocalDate startDate = LocalDate.of(2023, 02, 4);
    private final LocalDate endDate = LocalDate.of(2023, 6, 11);
    private final LocalDate setDate = LocalDate.of(2023, 7, 8);
    private final LocalDate currentDate = LocalDate.of(2023, 2, 13);
    private final LocalDate setDate2 = LocalDate.of(2023, 1, 1);

    @BeforeEach
    void runBefore() {
        soldItem = new IncomeCategory("sold item");
        oddJobs = new IncomeCategory("odd jobs");
        siTest1 = new SingleIncome("guitar", 500);
        siTest2 = new SingleIncome("mowed lawn", 25);

    }

    @Test
    void SingleIncomeTest() {
        assertEquals("guitar", siTest1.getName());
        assertEquals(500, siTest1.getAmount());
        assertEquals(java.time.LocalDate.now(), siTest1.getDate());

        assertEquals("mowed lawn", siTest2.getName());
        assertEquals(25, siTest2.getAmount());
        assertEquals(java.time.LocalDate.now(), siTest2.getDate());
    }


    @Test
    void calculateTest() {
        siTest1.setDate(currentDate);
        assertEquals(500, siTest1.calculate(startDate, endDate));

        siTest1.setDate(setDate);
        assertEquals(0, siTest1.calculate(startDate, endDate));
        siTest2.setDate(setDate2);
        assertEquals(0, siTest2.calculate(startDate, endDate));
    }





}
