package model;

import java.time.LocalDate;

import static java.lang.Math.floor;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

//Abstract class for Recurring expenses and incomes
public abstract class RecurringFinancials extends IncomeOrExpense {


    public abstract String getPeriod();


    //EFFECTS: produce total income over a given period, based on this
    //         period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        double numDaysBetween = 0;
        LocalDate thisStartDate = this.getDate();
        int dayOfMonth = thisStartDate.getDayOfMonth();

        if (thisStartDate.isBefore(endDate)) {
            if (thisStartDate.isAfter(startDate)) {
                numDaysBetween = DAYS.between(this.getDate(), endDate);
            } else if (thisStartDate.isBefore(startDate)) {
                startDate = startDate.withDayOfMonth(dayOfMonth);
                numDaysBetween = DAYS.between(startDate, endDate);
            }
        }

        double multiplier = calculateMultiplier(numDaysBetween, startDate, endDate);

        amount = this.amount * multiplier;

        return amount;
    }

    //REQUIRES: numDaysBetween >= 0
    //EFFECTS: returns multiplier for calculation based on period
    public double calculateMultiplier(double numDaysBetween, LocalDate startDate, LocalDate endDate) {
        double multiplier;
        if (numDaysBetween == 0) {
            multiplier = 0;
        } else if (this.getPeriod().equals("weekly")) {
            multiplier = numDaysBetween / 7;
        } else if (this.getPeriod().equals("bi-weekly")) {
            multiplier = (numDaysBetween / 7) / 2;
        } else {
            multiplier = MONTHS.between(startDate, endDate);

        }

        if (multiplier > 1) {
            multiplier = floor(multiplier);
        }
        return multiplier;
    }
}


