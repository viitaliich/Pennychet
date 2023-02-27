package com.example.pennychet.database;

import java.util.List;

public class DataFromDB {

//    private String mCurrencyType = "UAH";

    private String[] categoriesNamesExpense;
    private String[] categoriesNamesIncome;
    private String[] accountsNames;
    private List<Category> categoriesExpense;
    private List<Category> categoriesIncome;
    private List<Account> categoriesAccounts;

    private  List<Transaction> transactionsAll;
    private  List<Transaction> transactionsExpense;
    private  List<Transaction> transactionsIncome;

    private TransactionDao transactionDao;
    private AccountDao accountDao;
    private AccumulatedDateDao accumulatedDateDao;
    private CategoryDao categoryDao;

    public List<Transaction> getTransactionsAll() { return transactionsAll; }
    public List<Transaction> getTransactionsExpense()
    {
        return transactionsExpense;
    }
    public List<Transaction> getTransactionsIncome()
    {
        return transactionsIncome;
    }

    public List<Category> getCategoriesExpense()
    {
        return categoriesExpense;
    }

    public List<Category> getCategoriesIncome()
    {
        return categoriesIncome;
    }

    public List<Account> getCategoriesAccounts()
    {
        return categoriesAccounts;
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

    public void getAllTransactions()
    {
        transactionsAll = transactionDao.getAll();
        transactionsExpense = transactionDao.getAllByType("Expense");
        transactionsIncome = transactionDao.getAllByType("Income");
    }

    public void getAllCategory()
    {
        categoriesAccounts = accountDao.getAll();
        if(categoriesAccounts.isEmpty())
        {
            for(String accountName : accountsNames)
            {
                Account account = new Account();
                account.name = accountName;
                account.account_sum = 0;        // set saved initial value
                categoriesAccounts.add(account);
                accountDao.insertAll(account);
            }
        }

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

    public DataFromDB(String[] categoriesExpense, String[] categoriesIncome, String[] categoriesAccounts)
    {
        categoriesNamesExpense = categoriesExpense;
        categoriesNamesIncome = categoriesIncome;
        accountsNames = categoriesAccounts;
    }
}
