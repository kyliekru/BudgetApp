package persistence;

import model.*;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonTest {
    protected void checkSingle(String nme, double amt, LocalDate date, Category cat, int id, IncomeExpense single) {
        assertEquals(nme, single.getName());
        assertEquals(amt, single.getAmount());
        assertEquals(date, single.getDate());
        assertEquals(id, single.getID());
    }

    protected void checkRecur(String nme, String per, double amt, LocalDate dte, Category cat, int id, Recurring r) {
        checkSingle(nme, amt, dte, cat, id, r);
        assertEquals(per, r.getPeriod());
    }

    protected void checkCat(String nme, int id, LinkedList<Recurring> rec, LinkedList<IncomeExpense> si, Category cat) {
        assertEquals(nme, cat.getName());
        assertEquals(id, cat.getID());
        int i = 0;
        for (Recurring recurring : rec) {
          assertEquals(recurring, cat.getRecurring().get(i));
          i++;
        }
        int c = 0;
        for (IncomeExpense single : si) {
            assertEquals(single, cat.getSingle().get(c));
            c++;
        }
    }

    protected void checkCatList(int label, LinkedList<Category> cats, CategoryList catList) {
        assertEquals(label, catList.getLabel());
        int i = 0;
        for (Category cat : cats) {
            assertEquals(cat, catList.getCatList().get(i));
            i++;
        }
    }
}
