package com.example.pennychet.database;

import java.util.ArrayList;
import java.util.List;

public class DataFromDB {

    private String[] categoriesNames;

    private List<Expense> expensesCategoryT1;
    private List<Expense> expensesCategoryT2;
    private List<Expense> expensesCategoryT3;
    private List<Expense> expensesCategoryT4;
    private List<Expense> expensesCategoryL1;
    private List<Expense> expensesCategoryL2;
    private List<Expense> expensesCategoryR1;
    private List<Expense> expensesCategoryR2;
    private List<Expense> expensesCategoryB1;
    private List<Expense> expensesCategoryB2;
    private List<Expense> expensesCategoryB3;
    private List<Expense> expensesCategoryB4;

    private AccumulatedExpense[] accumulatedExpensesByCategory;

    private ExpenseDao expenseDao;
    private AccumulatedExpenseDao accumulatedExpenseDao;

    public AccumulatedExpense[] getAccumulatedExpensesByCategory()
    {
        return accumulatedExpensesByCategory;
    }

    public ExpenseDao getExpenseDao()
    {
        return expenseDao;
    }

    public void setExpenseDao(ExpenseDao expenseDao)
    {
        this.expenseDao = expenseDao;
    }

    public AccumulatedExpenseDao getAccumulatedExpenseDao()
    {
        return accumulatedExpenseDao;
    }

    public void setAccumulatedExpenseDao(AccumulatedExpenseDao accumulatedExpenseDao)
    {
        this.accumulatedExpenseDao = accumulatedExpenseDao;
    }

    public void getAllDataFromDB()
    {
        for (int i = 0; i < categoriesNames.length; i++) {
            AccumulatedExpense accumulatedExpense = new AccumulatedExpense();
            accumulatedExpense.category = categoriesNames[i];
            accumulatedExpense.sum = 0;
            accumulatedExpenseDao.insertAll(accumulatedExpense);
        }

        expensesCategoryT1 = expenseDao.getAllByCategory(categoriesNames[0]);
        expensesCategoryT2 = expenseDao.getAllByCategory(categoriesNames[1]);
        expensesCategoryT3 = expenseDao.getAllByCategory(categoriesNames[2]);
        expensesCategoryT4 = expenseDao.getAllByCategory(categoriesNames[3]);
        expensesCategoryL1 = expenseDao.getAllByCategory(categoriesNames[4]);
        expensesCategoryL2 = expenseDao.getAllByCategory(categoriesNames[5]);
        expensesCategoryR1 = expenseDao.getAllByCategory(categoriesNames[6]);
        expensesCategoryR2 = expenseDao.getAllByCategory(categoriesNames[7]);
        expensesCategoryB1 = expenseDao.getAllByCategory(categoriesNames[8]);
        expensesCategoryB2 = expenseDao.getAllByCategory(categoriesNames[9]);
        expensesCategoryB3 = expenseDao.getAllByCategory(categoriesNames[10]);
        expensesCategoryB4 = expenseDao.getAllByCategory(categoriesNames[11]);

        for(int i = 0; i < 12; i++)
        {
            getAccumulatedExpensesByCategory()[i] =
                    accumulatedExpenseDao.getByCategory(categoriesNames[i]);
        }
    }

    public DataFromDB()
    {
        categoriesNames = new String[]{"Groceries", "Cafe", "Free Time", "Transport",
                "Takeout", "Gift",
                "Loved ones", "Games",
                "Health", "Flowers", "Charity", "Shopping"};

        accumulatedExpensesByCategory = new AccumulatedExpense[12];

        expensesCategoryT1 = new ArrayList<>();
        expensesCategoryT2 = new ArrayList<>();
        expensesCategoryT3 = new ArrayList<>();
        expensesCategoryT4 = new ArrayList<>();
        expensesCategoryL1 = new ArrayList<>();
        expensesCategoryL2 = new ArrayList<>();
        expensesCategoryR1 = new ArrayList<>();
        expensesCategoryR2 = new ArrayList<>();
        expensesCategoryB1 = new ArrayList<>();
        expensesCategoryB2 = new ArrayList<>();
        expensesCategoryB3 = new ArrayList<>();
        expensesCategoryB4 = new ArrayList<>();
    }
}
