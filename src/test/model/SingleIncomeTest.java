package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SingleIncomeTest {

    private IncomeCategory soldItem;
    private IncomeCategory oddJobs;
    private SingleIncome siTest1;
    private SingleIncome siTest2;

    @BeforeEach
    void runBefore() {
        soldItem = new IncomeCategory("sold item");
        oddJobs = new IncomeCategory("odd jobs");
        siTest1 = new SingleIncome("guitar", soldItem, 500);
        siTest2 = new SingleIncome("mowed lawn", oddJobs, 25);

    }

    @Test
    void SingleIncomeTest() {
        assertEquals("guitar", siTest1.getName());
        assertEquals(soldItem, siTest1.getCat());
        assertEquals(500, siTest1.getAmount());

        assertEquals("mowed lawn", siTest2.getName());
        assertEquals(oddJobs, siTest2.getCat());
        assertEquals(25, siTest2.getAmount());
    }

    @Test
    void setCatTest() {
        siTest1.setCat(oddJobs);
        assertEquals(oddJobs, siTest1.getCat());
    }



}
