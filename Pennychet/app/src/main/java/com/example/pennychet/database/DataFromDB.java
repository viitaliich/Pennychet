package com.example.pennychet.database;

import java.util.ArrayList;
import java.util.List;

public class DataFromDB {

    public String[] categoriesNames;

    public List<Expense> expensesCategoryT1;
    public List<Expense> expensesCategoryT2;
    public List<Expense> expensesCategoryT3;
    public List<Expense> expensesCategoryT4;
    public List<Expense> expensesCategoryL1;
    public List<Expense> expensesCategoryL2;
    public List<Expense> expensesCategoryR1;
    public List<Expense> expensesCategoryR2;
    public List<Expense> expensesCategoryB1;
    public List<Expense> expensesCategoryB2;
    public List<Expense> expensesCategoryB3;
    public List<Expense> expensesCategoryB4;

    public AccumulatedExpense[] accumulatedExpensesByCategory;

//    public AccumulatedExpense accumulatedExpensesCategoryT1;
//    public AccumulatedExpense accumulatedExpensesCategoryT2;
//    public AccumulatedExpense accumulatedExpensesCategoryT3;
//    public AccumulatedExpense accumulatedExpensesCategoryT4;
//    public AccumulatedExpense accumulatedExpensesCategoryL1;
//    public AccumulatedExpense accumulatedExpensesCategoryL2;
//    public AccumulatedExpense accumulatedExpensesCategoryR1;
//    public AccumulatedExpense accumulatedExpensesCategoryR2;
//    public AccumulatedExpense accumulatedExpensesCategoryB1;
//    public AccumulatedExpense accumulatedExpensesCategoryB2;
//    public AccumulatedExpense accumulatedExpensesCategoryB3;
//    public AccumulatedExpense accumulatedExpensesCategoryB4;

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
