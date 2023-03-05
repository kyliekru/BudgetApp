package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {

    @Test
    void budgetTest() {
        Budget budget = new Budget("My budget");
        assertEquals("My budget", budget.getName());
        budget.setName("Changed name");
        assertEquals("Changed name", budget.getName());
    }
}
