package model;

import java.time.LocalDate;

public interface Category {


    public double addTotalAmount(LocalDate startDate, LocalDate endDate);

    public double addSingleAmount(LocalDate startDate, LocalDate endDate);

    public double addRecurringAmount(LocalDate startDate, LocalDate endDate);

    public int getID();



}
