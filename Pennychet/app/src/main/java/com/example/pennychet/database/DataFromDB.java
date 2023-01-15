package com.example.pennychet.database;

import java.util.ArrayList;
import java.util.List;

public class DataFromDB {

//    private String mCurrencyType = "UAH";

    private String[] categoriesNames;
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

//    public String getCurrencyType()
//    {
//        return mCurrencyType;
//    }

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
    }
}
