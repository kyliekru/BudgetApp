package model;

import java.time.LocalDate;

import static java.lang.Math.floor;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

public abstract class RecurringFinancials extends AddFinancials {

    protected LocalDate date;
    protected String period;


    public LocalDate getStartDate() {
        return this.date;

    }

    public String getPeriod() {
        return this.period;
    }


    public void setStartDate(LocalDate date) {
        this.date = date;

    }

    //REQUIRES: endDate in dd/MM/YYYY format
    //EFFECTS: produce total income over a given period, based on this
    //         period
    public double calculate(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        double numDaysBetween = 0;

        if (this.getStartDate().isBefore(endDate)) {
            if (this.getStartDate().isAfter(startDate)) {
                numDaysBetween = DAYS.between(this.getStartDate(), endDate);
            } else {
                numDaysBetween = DAYS.between(startDate, endDate);
            }
        }
        double multiplier;
        if (this.getPeriod().equals("weekly")) {
            multiplier = floor(numDaysBetween / 7);
        } else if (this.getPeriod().equals("bi-weekly")) {
            multiplier = floor((numDaysBetween / 7) / 2);
        } else {
            multiplier = floor(MONTHS.between(startDate, endDate));
        }

        amount = this.amount * multiplier;

        return amount;
    }
}
