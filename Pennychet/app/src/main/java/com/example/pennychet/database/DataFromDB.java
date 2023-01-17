package com.example.pennychet.database;

import java.util.List;

public class DataFromDB {

//    private String mCurrencyType = "UAH";

    private String[] categoriesNames;
    private List<Category> categories;

    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private AccumulatedDateDao accumulatedDateDao;
    private CategoryDao categoryDao;

    public List<Category> getCategories()
    {
        return categories;
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
        categories = categoryDao.getAll();
        if (categories.isEmpty())
        {
            for (String categoriesName : categoriesNames) {
                Category category = new Category();
                category.type = "Expenses";     // ???
                category.name = categoriesName;
                category.year = 2023;           // ???
                category.month = 1;             // ???
                category.sum_year = 0;
                category.sum_month = 0;
                categories.add(category);
                categoryDao.insertAll(category);
            }
        }
    }

    public DataFromDB(String[] categories)
    {
        categoriesNames = categories;
    }
}
