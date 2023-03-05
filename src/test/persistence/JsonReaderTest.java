package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//SOURCE: from JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget budget = reader.read();
            fail("IO Exception Expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals("My budget", budget.getName());
            List<CategoryList> incomes = budget.getIncomes().getCatList();
            List<CategoryList> expenses = budget.getExpenses().getCatList();
            assertEquals(0, incomes.size());
            assertEquals(0, expenses.size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals("KYLIE'S BUDGET", budget.getName());
            LinkedList<Category> incomes = budget.getIncomes().getCatList();
            LinkedList<Category> expenses = budget.getExpenses().getCatList();
            assertEquals(2, incomes.size());
            assertEquals(2, expenses.size());
            checkCatList(1, incomes, budget.getIncomes());
            checkCatList(0, expenses, budget.getExpenses());
            testCatLists(incomes, expenses);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    protected void testCatLists(LinkedList<Category> incomes, LinkedList<Category> expenses) {
        Category incomeOne = incomes.get(0);
        Category incomeTwo = incomes.get(1);
        Category expenseOne = expenses.get(0);
        Category expenseTwo = expenses.get(1);

        checkCat("SALARY", 1, incomeOne.getRecurring(), incomeOne.getSingle(), incomeOne);
        checkCat("ODD JOBS", 2, incomeTwo.getRecurring(), incomeTwo.getSingle(), incomeTwo);
        checkCat("GROCERIES", 3, expenseOne.getRecurring(), expenseOne.getSingle(), expenseOne);
        checkCat("HOUSING", 4, expenseTwo.getRecurring(), expenseTwo.getSingle(), expenseTwo);

        testCategoryOne(incomeOne);
        testCategoryTwo(incomeTwo);
        testCategoryThree(expenseOne);
        testCategoryFour(expenseTwo);
    }

    protected void testCategoryOne(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2022, 12, 2);
        LocalDate dateTwo = LocalDate.of(2022, 11, 30);
        LocalDate dateThree = LocalDate.of(2023, 1, 20);

        checkRecur("work", "bi-weekly", 1500, dateOne, cat, 1, recurs.get(0));
        checkRecur("bonus", "monthly", 500, dateTwo, cat, 2, recurs.get(1));
        checkSingle("one-time bonus", 500, dateThree, cat, 3, single.get(0));

    }

    protected void testCategoryTwo(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2020, 1, 1);
        LocalDate dateTwo = LocalDate.of(2023, 1, 20);
        LocalDate dateThree = LocalDate.of(2022, 5, 2);

        checkRecur("babysitting", "weekly", 50, dateOne, cat, 4, recurs.get(0));
        checkSingle("sold guitar", 400, dateTwo, cat, 5, single.get(0));
        checkSingle("acting gig", 1000, dateThree, cat, 6, single.get(1));

    }

    protected void testCategoryThree(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2023, 1, 1);
        LocalDate dateTwo = LocalDate.of(2023, 1, 1);
        LocalDate dateThree = LocalDate.of(2023, 2, 25);
        LocalDate dateFour = LocalDate.of(2023, 3, 1);

        checkRecur("bi-weekly shopping trip", "bi-weekly", 150, dateOne, cat, 7, recurs.get(0));
        checkRecur("monthly costco trip", "monthly", 200, dateTwo, cat, 8, recurs.get(1));
        checkSingle("baking ingredients", 50, dateThree, cat, 9, single.get(0));
        checkSingle("dinner extras", 25, dateFour, cat, 10, single.get(1));

    }

    protected void testCategoryFour(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2023, 1, 1);
        LocalDate dateThree = LocalDate.of(2022, 12, 3);


        checkRecur("rent", "monthly", 1500, dateOne, cat, 11, recurs.get(0));
        checkRecur("water bill", "monthly", 100, dateOne, cat, 12, recurs.get(1));
        checkSingle("roof repair", 5000, dateThree, cat, 13, single.get(0));
        checkSingle("new flooring", 10000, dateOne, cat, 14, single.get(1));

    }


}
