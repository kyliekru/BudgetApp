# Budget Tracker Application

## Proposal

The budget tracker application will maintain a record of 
a user's expenses and income in custom or predetermined categories, as well as allow them to
create a custom budget. Using the created budget, the
application will track expenses and report spending in
each given category for a chosen period (week, month, or year).
These reports will also allow the application to notify the user
on excessive spending or increased savings in each category.

I will use the budget tracker, along with friends and family and
anyone else that comes across it. This project is interesting to me
because I have found most budget trackers online have most of
their features locked behind a subscription paywall. I'd like to
make a simple budget app that I can use for free, since it's difficult
to keep track of a personal budget otherwise.

*User Stories*:
- As a user, I want to be able to create a custom budget with
spending categories of my choosing.
- As a user, I want to be able to add an expense to each list of
weekly, monthly, and yearly expenses.
- As a user, I want to be able to create a regular expensive to be added
to my lists of expenses on a given basis
- As a user, I want to be able to add a regular income that repeats
on a given basis to each list of biweekly, monthly, and yearly income.
- As a user, I want to be able to add supplemental income at any time
to each list of biweekly, monthly, and yearly income.
- As a user I want to be able to receive a report of my
budget for a given week, month, or year.
- As a user I want to be able to see a visual representation of my
budget for a given week, month, or year.
- As a user, I want to be able to stop a regular expense or income
from continuing to occur
- As a user, I want to be able to undo an input error
- As a user, I want to be able to save my budget to a file
- As a user, I want to be able to reload my budget from a file or start a new one

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by when the application is open
and the budget has been named, click the "add cat" button in the top middle of the screen. Then enter a name and choose
the type. Finally, click "OK" and the list item will be generated on the left or right depending on type chosen.
- You can generate the second required event related to adding Xs to a Y by once a category has been added as stated
above, select the category you would like to add to by clicking, and click the "Add" button below its list panel. Fill in required
info, requires in this order: String, double, proper year value, proper month value, proper day value. Then select
yes or no and select "OK". If yes, select period and click "OK", and income/expense will be added to selected category.
- You can locate my visual component by viewing the top left corner of the screen, where there is a logo.
- You can save the state of my application by clicking the "file" button in the menubar, and then the "save" option.
- You can reload the state of my application by when starting the application, choose yes when asked if you'd like to
load your budget.

# Phase 4: Task 2
Tue Apr 11 08:30:14 PDT 2023
Expense category added.


Tue Apr 11 08:30:24 PDT 2023
Recurring expense added.


Tue Apr 11 08:30:36 PDT 2023
Single expense added.


Tue Apr 11 08:30:43 PDT 2023
Income category added.


Tue Apr 11 08:30:57 PDT 2023
Recurring income added.


Tue Apr 11 08:31:05 PDT 2023
Recurring income removed.


Tue Apr 11 08:31:08 PDT 2023
Income category deleted.


This EventLog occurs after starting the program and adding first an expense category, a single and a recurring expense,
then an income category followed by a recurring income are added. Finally, the recurring income is deleted and then the
income category is deleted.

# Phase 4: Task 3
If I had more time to work on the project, I think I would change how I implemented
some of my classes. For example, I would change my ID system to be a Singleton design to make the
identification more useful. This would have made implementing my GUI significantly easier. I also
would get rid of the CategoryList class, as I think it adds unnecessary complexity with the budget class
being implemented. Or, I would make CategoryList iterable. Finally, I would implement a single income/expense abstract class, similar to the recurring class,
to decrease complexity when implementing the GUI.

- Alter ID system
- Remove CategoryList class
- Implement SingleIncomeExpense class that extends IncomeExpense
