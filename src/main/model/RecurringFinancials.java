package model;

import java.time.LocalDate;

import static java.lang.Math.floor;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

//Abstract class for Recurring expenses and incomes
public abstract class RecurringFinancials extends IncomeOrExpense {


    //GETTER
    public abstract String getPeriod();


    //EFFECTS: produce total income over a given period, based on this
    //         period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        double numDaysBetween = 0;
        LocalDate thisDate = this.getDate();

        if (thisDate.isBefore(endDate)) {
            if (thisDate.isAfter(startDate) || thisDate.isEqual(startDate)) {
                numDaysBetween = DAYS.between(this.getDate(), endDate);
            } else {
                numDaysBetween = DAYS.between(startDate, endDate);
            }
        }

        double multiplier = calculateMultiplier(numDaysBetween, startDate, endDate, thisDate);

        amount = this.amount * multiplier;

        return amount;
    }

    //REQUIRES: numDaysBetween >= 0
    //EFFECTS: returns multiplier for calculation based on period
    public double calculateMultiplier(double numDays, LocalDate startDate, LocalDate endDate, LocalDate day) {
        double multiplier = 0;
        int count = 0;
        if (numDays == 0) {
            multiplier = 0;
        } else if (this.getPeriod().equals("weekly")) {
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                if (date.getDayOfWeek().equals(day.getDayOfWeek())) {
                    multiplier += 1;
                }
            }
        } else if (this.getPeriod().equals("bi-weekly")) {
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                if (date.getDayOfWeek().equals(day.getDayOfWeek())) {
                    count += 1;
                    if ((count % 2) == 0) {
                        multiplier += 1;
                    }
                }
            }
        } else {
            multiplier = calculateMonthMultiplier(startDate, endDate, day);

        }
        return floor(multiplier);
    }

    //EFFECTS: calculates multiplier for monthly periods
    public double calculateMonthMultiplier(LocalDate startDate, LocalDate endDate, LocalDate day) {
        double multiplier = 0;
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.getDayOfMonth() == day.getDayOfMonth()) {
                multiplier += 1;
            }
        }
        return multiplier;
    }
}


