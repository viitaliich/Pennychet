package com.example.pennychet.database;

public class DataFromDB {

//    private String mCurrencyType = "UAH";

    private String[] categoriesNames;
    private Category[] categories;

    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private AccumulatedDateDao accumulatedDateDao;
    private CategoryDao categoryDao;

    public Category[] getCategory()
    {
        return categories;
    }


//    public String getCurrencyType()
//    {
//        return mCurrencyType;
//    }

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

    public void getAllDataFromDB()
    {
        for (int i = 0; i < categoriesNames.length; i++) {
            Category category = new Category();
            category.type = "Expences";
            category.name = categoriesNames[i];
            category.year = 2023;
            category.month = 1;
            category.sum_year = 0;
            category.sum_month = 0;
            categories[i] = category;
            categoryDao.insertAll(category);
        }
    }

    public DataFromDB()
    {
        categoriesNames = new String[]{"Groceries", "Cafe", "Free Time", "Transport",
                "Takeout", "Gift",
                "Loved ones", "Games",
                "Health", "Flowers", "Charity", "Shopping"};

        categories = new Category[12];
    }
}
