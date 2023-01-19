package com.example.pennychet.database;

import java.util.List;

public class DataFromDB {

//    private String mCurrencyType = "UAH";

    private String[] categoriesNamesExpense;
    private String[] categoriesNamesIncome;
    private List<Category> categoriesExpense;
    private List<Category> categoriesIncome;

    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private AccumulatedDateDao accumulatedDateDao;
    private CategoryDao categoryDao;

    public List<Category> getCategoriesExpense()
    {
        return categoriesExpense;
    }

    public List<Category> getCategoriesIncome()
    {
        return categoriesIncome;
    }

    public void setTransactionDao(TransactionDao transactionDao)
    {
        this.transactionDao = transactionDao;
    }
    public TransactionDao getTransactionDao() { return transactionDao; }

    public void setAccountDao(AccountDao accountDao)
    {
        this.accountDao = accountDao;
    }
    public AccountDao getAccountDao() { return accountDao; }

    public void setCategoryDao(CategoryDao categoryDao)
    {
        this.categoryDao = categoryDao;
    }
    public CategoryDao getCategoryDao() { return categoryDao; }

    public void setAccumulatedDateDao(AccumulatedDateDao accumulatedDateDao)
    {
        this.accumulatedDateDao = accumulatedDateDao;
    }
    public AccumulatedDateDao getAccumulatedDateDao() { return accumulatedDateDao; }

    public void getAllCategory()
    {
        categoriesExpense = categoryDao.getAllByType("Expense");
        if (categoriesExpense.isEmpty())
        {
            for (String categoriesName : categoriesNamesExpense) {
                Category category = new Category();
                category.type = "Expense";     // ???
                category.name = categoriesName;
                category.year = 2023;           // ???
                category.month = 1;             // ???
                category.sum_year = 0;
                category.sum_month = 0;
                categoriesExpense.add(category);
                categoryDao.insertAll(category);
            }
        }

        categoriesIncome = categoryDao.getAllByType("Income");
        if (categoriesIncome.isEmpty())
        {
            for (String categoriesName : categoriesNamesIncome) {
                Category category = new Category();
                category.type = "Income";     // ???
                category.name = categoriesName;
                category.year = 2023;           // ???
                category.month = 1;             // ???
                category.sum_year = 0;
                category.sum_month = 0;
                categoriesIncome.add(category);
                categoryDao.insertAll(category);
            }
        }
    }

    public DataFromDB(String[] categoriesExpense, String[] categoriesIncome )
    {
        categoriesNamesExpense = categoriesExpense;
        categoriesNamesIncome = categoriesIncome;
    }
}
