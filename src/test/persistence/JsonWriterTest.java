package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Budget budget = new Budget("My budget");
            JsonWriter writer = new JsonWriter("./data/my\0illegal!fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyBudget() {
        try {
            Budget budget = new Budget("My budget");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            budget = reader.read();
            assertEquals("My budget", budget.getName());
            CategoryList incomes = budget.getIncomes();
            CategoryList expenses = budget.getExpenses();
            assertEquals(0, incomes.getCatList().size());
            assertEquals(0, expenses.getCatList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBudget() {
        try {
            Budget budget = createBudget();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
            budget = reader.read();
            assertEquals("KYLIE'S BUDGET", budget.getName());
            LinkedList<Category> incomes = budget.getIncomes().getCatList();
            LinkedList<Category> expenses = budget.getExpenses().getCatList();
            assertEquals(2, incomes.size());
            assertEquals(2, expenses.size());
            checkCatList(1, incomes, budget.getIncomes());
            checkCatList(0, expenses, budget.getExpenses());
            testCatLists(incomes, expenses);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
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

        checkRecur("work", "bi-weekly", 1500, dateOne, cat, 2, recurs.get(1));
        checkRecur("bonus", "monthly", 500, dateTwo, cat, 1, recurs.get(0));
        checkSingle("one-time bonus", 500, dateThree, cat, 3, single.get(0));

    }

    protected void testCategoryTwo(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2020, 1, 1);
        LocalDate dateTwo = LocalDate.of(2023, 1, 20);
        LocalDate dateThree = LocalDate.of(2022, 5, 2);

        checkRecur("babysitting", "weekly", 50, dateOne, cat, 4, recurs.get(0));
        checkSingle("sold guitar", 400, dateTwo, cat, 6, single.get(1));
        checkSingle("acting gig", 1000, dateThree, cat, 5, single.get(0));

    }

    protected void testCategoryThree(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2023, 1, 1);
        LocalDate dateTwo = LocalDate.of(2023, 1, 1);
        LocalDate dateThree = LocalDate.of(2023, 2, 25);
        LocalDate dateFour = LocalDate.of(2023, 3, 1);

        checkRecur("groceries", "bi-weekly", 150, dateOne, cat, 8, recurs.get(1));
        checkRecur("costco trip", "monthly", 200, dateTwo, cat, 7, recurs.get(0));
        checkSingle("baking", 50, dateThree, cat, 10, single.get(1));
        checkSingle("dinner extras", 25, dateFour, cat, 9, single.get(0));

    }

    protected void testCategoryFour(Category cat) {
        LinkedList<Recurring> recurs = cat.getRecurring();
        LinkedList<IncomeExpense> single = cat.getSingle();
        LocalDate dateOne = LocalDate.of(2023, 1, 1);
        LocalDate dateThree = LocalDate.of(2022, 12, 3);


        checkRecur("rent", "monthly", 1500, dateOne, cat, 12, recurs.get(1));
        checkRecur("water bill", "monthly", 100, dateOne, cat, 11, recurs.get(0));
        checkSingle("roof repair", 5000, dateThree, cat, 14, single.get(1));
        checkSingle("new flooring", 10000, dateOne, cat, 13, single.get(0));

    }

    private Budget createBudget() {
        Budget budget = new Budget("KYLIE'S BUDGET");

        IncomeCategory salary = new IncomeCategory("SALARY");
        IncomeCategory oddjobs = new IncomeCategory("ODD JOBS");

        budget.setIncomes(getIncomeCatList(salary, oddjobs));

        ExpenseCategory groceries = new ExpenseCategory("GROCERIES");
        ExpenseCategory housing = new ExpenseCategory("HOUSING");

        budget.setExpenses(getExpenseCatList(groceries, housing));

        return budget;




    }

    private CategoryList getIncomeCatList(IncomeCategory salary, IncomeCategory oddjobs) {
        RecurringIncome rIncomeOne = new RecurringIncome("bonus", 500, "monthly");
        rIncomeOne.setDate(LocalDate.of(2022, 11, 30));

        RecurringIncome rIncomeTwo = new RecurringIncome("work", 1500, "bi-weekly");
        rIncomeTwo.setDate(LocalDate.of(2022, 12, 2));

        SingleIncome sIncomeOne = new SingleIncome("one-time bonus", 500);
        sIncomeOne.setDate(LocalDate.of(2023, 1, 20));

        RecurringIncome rIncomeThree = new RecurringIncome("babysitting", 50, "weekly");
        rIncomeThree.setDate(LocalDate.of(2020, 1, 1));

        SingleIncome sIncomeTwo = new SingleIncome("acting gig", 1000);
        sIncomeTwo.setDate(LocalDate.of(2022, 5, 2));

        SingleIncome sIncomeThree = new SingleIncome("sold guitar", 400);
        sIncomeThree.setDate(LocalDate.of(2023, 1, 20));
        salary.addRecurring(rIncomeOne);
        salary.addRecurring(rIncomeTwo);
        salary.addSingle(sIncomeOne);

        oddjobs.addRecurring(rIncomeThree);
        oddjobs.addSingle(sIncomeTwo);
        oddjobs.addSingle(sIncomeThree);

        CategoryList incomes = new CategoryList(1);
        incomes.addCat(salary);
        incomes.addCat(oddjobs);
        return incomes;

    }

    private CategoryList getExpenseCatList(ExpenseCategory groceries, ExpenseCategory housing) {
        RecurringExpense rExpenOne = new RecurringExpense("costco trip", 200, "monthly");
        rExpenOne.setDate(LocalDate.of(2023, 1, 1));

        RecurringExpense rExpenTwo = new RecurringExpense("groceries", 150, "bi-weekly");
        rExpenTwo.setDate(LocalDate.of(2023, 1, 1));

        SingleExpense sExpenOne = new SingleExpense("dinner extras", 25);
        sExpenOne.setDate(LocalDate.of(2023, 3, 1));

        SingleExpense sExpenTwo = new SingleExpense("baking", 50);
        sExpenTwo.setDate(LocalDate.of(2023, 2, 25));

        groceries.addRecurring(rExpenOne);
        groceries.addRecurring(rExpenTwo);
        groceries.addSingle(sExpenOne);
        groceries.addSingle(sExpenTwo);

        RecurringExpense rExpenThree = new RecurringExpense("water bill", 100, "monthly");
        rExpenThree.setDate(LocalDate.of(2023, 1, 1));

        RecurringExpense rExpenFour = new RecurringExpense("rent", 1500, "monthly");
        rExpenFour.setDate(LocalDate.of(2023, 1, 1));

        SingleExpense sExpenThree = new SingleExpense("new flooring", 10000);
        sExpenThree.setDate(LocalDate.of(2023, 1, 1));

        SingleExpense sExpenFour = new SingleExpense("roof repair", 5000);
        sExpenFour.setDate(LocalDate.of(2022, 12, 3));

        housing.addRecurring(rExpenThree);
        housing.addRecurring(rExpenFour);
        housing.addSingle(sExpenThree);
        housing.addSingle(sExpenFour);

        CategoryList expenses = new CategoryList(0);
        expenses.addCat(groceries);
        expenses.addCat(housing);
        return expenses;

    }
}
