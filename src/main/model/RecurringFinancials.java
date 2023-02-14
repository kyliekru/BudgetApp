package model;

import java.time.LocalDate;

import static java.lang.Math.floor;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

//Abstract class for Recurring expenses and incomes
public abstract class RecurringFinancials extends IncomeOrExpense {

    protected LocalDate date;
    protected String period;


    public LocalDate getStartDate() {
        return this.date;

    }

    public abstract String getPeriod();


    public void setStartDate(LocalDate date) {
        this.date = date;

    }


    //EFFECTS: produce total income over a given period, based on this
    //         period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        double numDaysBetween = 0;
        LocalDate thisStartDate = this.getStartDate();

        if (thisStartDate.isBefore(endDate)) {
            if (thisStartDate.isAfter(startDate)) {
                numDaysBetween = DAYS.between(this.getStartDate(), endDate);
            } else {
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
        int daysInMonth = startDate.getMonth().length(startDate.isLeapYear());

        if (this.getPeriod().equals("weekly")) {
            multiplier = numDaysBetween / 7;
        } else if (this.getPeriod().equals("bi-weekly")) {
            multiplier = (numDaysBetween / 7) / 2;
        } else {
            multiplier = MONTHS.between(startDate, endDate);
            if (multiplier < 1) {
                multiplier = numDaysBetween / daysInMonth;
            }
        }
        if (multiplier > 1) {
            multiplier = floor(multiplier);
        }
        return multiplier;
    }
}


