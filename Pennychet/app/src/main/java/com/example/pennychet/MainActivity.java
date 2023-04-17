package com.example.pennychet;

import static java.lang.Double.max;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.pennychet.database.Account;
import com.example.pennychet.database.AppDatabase;
import com.example.pennychet.database.Category;
import com.example.pennychet.database.DataFromDB;
import com.example.pennychet.database.Transaction;
import com.example.pennychet.ui.ExpandableHeightGridView;
import com.example.pennychet.ui.GridAdapter;
import com.example.pennychet.ui.GridModel;
import com.example.pennychet.ui.ListAdapter;
import com.example.pennychet.ui.ListClickListener;
import com.example.pennychet.ui.ListData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MainActivity";

    enum State
    {
        EXPENSE,
        INCOME,
        ACCOUNT
    }
    State mState;

    // Calendar button inside category
    Button btnPickDate;
    String mDate;

    // Floating Action Button (FAB)
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;

    // PieChart
    PieChart pieChart;
    PieDataSet pieDataSetExpense;
    PieDataSet pieDataSetIncome;
    PieDataSet pieDataSetAccount;

    // Categories buttons grid
    ArrayList<GridModel> gridModelArrayList;
    GridAdapter adapter;
    ExpandableHeightGridView ctgGridView;

    // Transactions List
    List<ListData> transactionsList;
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    ListClickListener listener;


    int[] btnIconArrayExpense = new int[] {
            R.drawable.ic_outline_shopping_cart_64,
            R.drawable.ic_outline_free_breakfast_24,
            R.drawable.ic_baseline_category_24,
            R.drawable.ic_outline_tram_24,
            R.drawable.ic_outline_takeout_dining_24,
            R.drawable.ic_baseline_favorite_border_24,
            R.drawable.ic_baseline_face_24,
            R.drawable.ic_outline_sports_esports_24,
            R.drawable.ic_outline_health_and_safety_24,
            R.drawable.ic_baseline_local_movies_24,
            R.drawable.ic_outline_shopping_cart_24,
            R.drawable.ic_outline_local_mall_24,
    };

    int[] btnIconArrayIncome = new int[] {
            R.drawable.ic_round_local_florist_24,
            R.drawable.ic_outline_attach_money_24,
    };

    int[] btnIconArrayAccount = new int[] {
            R.drawable.ic_outline_attach_money_24,
            R.drawable.ic_outline_attach_money_24,
    };

    int[] ctgColorArrayExpense;
    int[] ctgColorArrayIncome;
    int[] ctgColorArrayAccount;

    String[] categoriesExpense;
    String[] categoriesIncome;
    String[] categoriesAccount;

    // Swap categories button
    Button swapBtn;
    TextView swapBtnText;

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mState = State.EXPENSE;

        ctgColorArrayExpense = new int[]{
                getResources().getColor(R.color.ctg1),
                getResources().getColor(R.color.ctg2),
                getResources().getColor(R.color.ctg3),
                getResources().getColor(R.color.ctg4),
                getResources().getColor(R.color.ctg5),
                getResources().getColor(R.color.ctg6),
                getResources().getColor(R.color.ctg7),
                getResources().getColor(R.color.ctg8),
                getResources().getColor(R.color.ctg9),
                getResources().getColor(R.color.ctg10),
                getResources().getColor(R.color.ctg11),
                getResources().getColor(R.color.ctg12),
        };

        ctgColorArrayIncome = new int[]{
                getResources().getColor(R.color.ctg1),
                getResources().getColor(R.color.ctg2),
        };

        ctgColorArrayAccount = new int[]{
                getResources().getColor(R.color.ctg11),
                getResources().getColor(R.color.ctg12),
        };

        categoriesExpense = getResources().getStringArray(R.array.categoriesExpense);
        categoriesIncome = getResources().getStringArray(R.array.categoriesIncome);
        categoriesAccount = getResources().getStringArray(R.array.accounts);

        // DB
        // allowMainThreadQueries() not recommended ???
        DataFromDB dataFromDB = new DataFromDB(categoriesExpense, categoriesIncome, categoriesAccount);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MainDB").allowMainThreadQueries().build();
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "MainDB").build();
        dataFromDB.setTransactionDao(db.transactionDao());
        dataFromDB.setCategoryDao(db.categoryDao());
        dataFromDB.setAccountDao(db.accountDao());
        dataFromDB.setAccumulatedDateDao(db.accumulatedDateDao());

        dataFromDB.getAllCategory();
        dataFromDB.getAllTransactions();

        pieDataSetExpense = new PieDataSet(pieChartCategories(dataFromDB, State.EXPENSE), "label");
        pieDataSetIncome = new PieDataSet(pieChartCategories(dataFromDB, State.INCOME), "label");
        pieDataSetAccount = new PieDataSet(pieChartCategories(dataFromDB, State.ACCOUNT), "label");

//        AsyncTask.execute(() -> dataFromDB.getAllCategory());

        // CategoryButtons

        ctgGridView = findViewById(R.id.ctgGridView);
        ctgGridView.setExpanded(true);

        gridModelArrayList = new ArrayList<GridModel>();
        for(int i = 0; i < categoriesExpense.length; i++)
        {
            gridModelArrayList.add(new GridModel(categoriesExpense[i], btnIconArrayExpense[i], ctgColorArrayExpense[i], (int)dataFromDB.getCategoriesExpense().get(i).sum_month));
        }
        adapter = new GridAdapter(this, gridModelArrayList);
        ctgGridView.setAdapter(adapter);
        ctgGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mState == State.ACCOUNT)
                    showBottomSheetAccount(dataFromDB, position);
                else
                    showBottomSheetCategory(dataFromDB, position, false);
            }
        });

        // Transactions List

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        transactionsList = new ArrayList<>();

        List<Transaction> transactionsExpense = dataFromDB.getTransactionsExpense();
        for (int i = 0; i < transactionsExpense.size(); i++)
        {
            Transaction transaction = transactionsExpense.get(i);
            transactionsList.add(new ListData(
                    transaction.type,
                    transaction.category,
                    transaction.account,
                    transaction.date,
                    transaction.description,
                    transaction.sum,
                    transaction.icon,
                    transaction.color
            ));
        }

        listener = new ListClickListener() {
            @Override
            public void click(int index){
//                Toast.makeText(MainActivity.this, "clicked item index is " + index, Toast.LENGTH_SHORT).show();
                showBottomSheetCategory(dataFromDB, index, true);
            }
        };
        listAdapter = new ListAdapter(transactionsList, getApplication(),listener);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // PieChart
        setupPieChart(mState);
        pieChart.invalidate();

        // Swap Income / Expense / Accounts button
        swapBtn = findViewById(R.id.pieChartButton);
        swapBtnText = findViewById(R.id.pieChartText);
        swapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapCategoriesButton(dataFromDB);
            }
        });

        // Drawer Layout
        DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.drawer_layout);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // FAB
        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);

        addAlarmActionText = findViewById(R.id.add_alarm_action_text);
        addPersonActionText = findViewById(R.id.add_person_action_text);

        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);
        isAllFabsVisible = false;

        mAddFab.setOnClickListener(view -> {
            if (!isAllFabsVisible) {
                mAddAlarmFab.show();
                mAddPersonFab.show();
                addAlarmActionText.setVisibility(View.VISIBLE);
                addPersonActionText.setVisibility(View.VISIBLE);
                isAllFabsVisible = true;
            } else {
                mAddAlarmFab.hide();
                mAddPersonFab.hide();
                addAlarmActionText.setVisibility(View.GONE);
                addPersonActionText.setVisibility(View.GONE);
                isAllFabsVisible = false;
            }
        });

        mAddPersonFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Person Added", Toast.LENGTH_SHORT
                ).show());

        mAddAlarmFab.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Alarm Added", Toast.LENGTH_SHORT
                ).show());
    }

    private void swapCategoriesButton(DataFromDB dataFromDB)
    {
        gridModelArrayList.clear();
        transactionsList.clear();

        if(mState == State.EXPENSE)
        {
            mState = State.INCOME;
            swapBtnText.setText("Income");

            for(int i = 0; i < categoriesIncome.length; i++)
            {
                gridModelArrayList.add(new GridModel(
                        categoriesIncome[i],
                        btnIconArrayIncome[i],
                        ctgColorArrayIncome[i],
                        (int)dataFromDB.getCategoriesIncome().get(i).sum_month));
            }

            List<Transaction> transactionsIncome = dataFromDB.getTransactionsIncome();
            for (int i = 0; i < transactionsIncome.size(); i++)
            {
                Transaction transaction = transactionsIncome.get(i);
                transactionsList.add(new ListData(
                        transaction.type,
                        transaction.category,
                        transaction.account,
                        transaction.date,
                        transaction.description,
                        transaction.sum,
                        transaction.icon,
                        transaction.color
                ));
            }

        }
        else if(mState == State.INCOME)
        {
            mState = State.ACCOUNT;
            swapBtnText.setText("Accounts");

            for(int i = 0; i < categoriesAccount.length; i++)
            {
                gridModelArrayList.add(new GridModel(categoriesAccount[i], btnIconArrayAccount[i], ctgColorArrayAccount[i], (int)dataFromDB.getCategoriesAccounts().get(i).account_sum));
            }

            List<Transaction> transactionsAll = dataFromDB.getTransactionsAll();
            for (int i = 0; i < transactionsAll.size(); i++)
            {
                Transaction transaction = transactionsAll.get(i);
                transactionsList.add(new ListData(
                        transaction.type,
                        transaction.category,
                        transaction.account,
                        transaction.date,
                        transaction.description,
                        transaction.sum,
                        transaction.icon,
                        transaction.color
                ));
            }
        }
        else if(mState == State.ACCOUNT)
        {
            mState = State.EXPENSE;
            swapBtnText.setText("Expense");

            for(int i = 0; i < categoriesExpense.length; i++)
            {
                gridModelArrayList.add(new GridModel(categoriesExpense[i], btnIconArrayExpense[i], ctgColorArrayExpense[i], (int)dataFromDB.getCategoriesExpense().get(i).sum_month));
            }

            List<Transaction> transactionsExpense = dataFromDB.getTransactionsExpense();
            for (int i = 0; i < transactionsExpense.size(); i++)
            {
                Transaction transaction = transactionsExpense.get(i);
                transactionsList.add(new ListData(
                        transaction.type,
                        transaction.category,
                        transaction.account,
                        transaction.date,
                        transaction.description,
                        transaction.sum,
                        transaction.icon,
                        transaction.color
                ));
            }

        }
        setupPieChart(mState);
        adapter = new GridAdapter(getApplicationContext(), gridModelArrayList);
        listAdapter = new ListAdapter(transactionsList, getApplicationContext(), listener);
        ctgGridView.setAdapter(adapter);
        recyclerView.setAdapter(listAdapter);
    }

    // BottomSheet Category
    private void showBottomSheetCategory(DataFromDB dataFromDB, int btnIndex, boolean editTransaction) {
        ListData old_transaction = null;
        if(editTransaction) old_transaction = transactionsList.get(btnIndex);

        final ListData transaction_old = old_transaction;

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        if(!editTransaction) bottomSheetDialog.setContentView(R.layout.bottom_sheet_category);
        else bottomSheetDialog.setContentView(R.layout.bottom_sheet_list);

        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();

        // Categories drop down menu
        AutoCompleteTextView autoCompleteTextViewCategoty = bottomSheetDialog.findViewById(R.id.actvCategory);
        String[] categories = {};

        // can be optimized ???
        if(mState == State.EXPENSE) categories = categoriesExpense;
        else if(mState == State.INCOME) categories = categoriesIncome;
        else
        {
            for(int i = 0; i < categoriesIncome.length; i++) {
                if (transaction_old.category.equals(categoriesIncome[i])) {
                    categories = categoriesIncome;
                    break;
                }
            }
            for(int i = 0; i < categoriesExpense.length; i++)
            {
                if(transaction_old.category.equals(categoriesExpense[i]))
                {
                    categories = categoriesExpense;
                    break;

                }
            }
        }


        List<String> categoriesList = Arrays.asList(categories);
        if(editTransaction) autoCompleteTextViewCategoty.setText(transaction_old.category);
        else autoCompleteTextViewCategoty.setText(categoriesList.get(btnIndex));
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(
                this, R.layout.drop_down_item, categoriesList);
        autoCompleteTextViewCategoty.setAdapter(adapterCategory);

        // Accounts drop down menu
        AutoCompleteTextView autoCompleteTextViewAccount = bottomSheetDialog.findViewById(R.id.actvAccount);
        String[] accounts = getResources().getStringArray(R.array.accounts);
        List<String> accountsList = Arrays.asList(accounts);
        autoCompleteTextViewAccount.setText(accountsList.get(0));
        if(editTransaction) autoCompleteTextViewAccount.setText(transaction_old.account);
        ArrayAdapter<String> adapterAccount = new ArrayAdapter<>(
                this, R.layout.drop_down_item, accountsList);
        autoCompleteTextViewAccount.setAdapter(adapterAccount);

        // Calendar
        btnPickDate = bottomSheetDialog.findViewById(R.id.btnBottomSheetPickDate);
        mDate = editTransaction ? transaction_old.date : new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        btnPickDate.setText(mDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.pennychet.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.example.pennychet.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        TextInputLayout tfBottomSheetSum = bottomSheetDialog.findViewById(R.id.textFieldBottomSheetSum);
        TextView tvBottomSheetSum = bottomSheetDialog.findViewById(R.id.textViewBottomSheetSum);
        TextView tvBottomSheetDescription = bottomSheetDialog.findViewById(R.id.textViewBottomSheetDescription);
        Button btnBottomSheetOk = bottomSheetDialog.findViewById(R.id.btnBottomSheetOk);
        Button btnBottomSheetCancel = bottomSheetDialog.findViewById(R.id.btnBottomSheetCancel);
        Button btnBottomSheetDelete = bottomSheetDialog.findViewById(R.id.btnBottomSheetDelete);
        if(editTransaction)
        {
            tvBottomSheetDescription.setText(transaction_old.description);
            tvBottomSheetSum.setText(String.valueOf(transaction_old.sum));
        }

        btnBottomSheetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tfBottomSheetSum.setErrorEnabled(false);

                if(tvBottomSheetSum.getText().toString().isEmpty())
                {
                    tfBottomSheetSum.setErrorEnabled(true);
                    tfBottomSheetSum.setError("Required field");
                }
                else
                {
                    tfBottomSheetSum.setError(null);
                    tfBottomSheetSum.setErrorEnabled(false);

                    // DB
                    String category = String.valueOf(autoCompleteTextViewCategoty.getText());
                    String account = String.valueOf(autoCompleteTextViewAccount.getText());
                    String description = String.valueOf(tvBottomSheetDescription.getText());
                    double sum = Double.parseDouble(String.valueOf(tvBottomSheetSum.getText()));
                    String date = String.valueOf(mDate);

                    int indexCategory = adapterCategory.getPosition(autoCompleteTextViewCategoty.getText().toString());
                    int indexAccount = adapterAccount.getPosition(autoCompleteTextViewAccount.getText().toString());

                    Transaction transaction = new Transaction();

                    if(mState == State.EXPENSE) {
                        transaction.type = "Expense";
                        transaction.icon = btnIconArrayExpense[indexCategory];
                        transaction.color = ctgColorArrayExpense[indexCategory];
                    }
                    else if (mState == State.INCOME)
                    {
                        transaction.type = "Income";
                        transaction.icon = btnIconArrayIncome[indexCategory];
                        transaction.color = ctgColorArrayIncome[indexCategory];
                    }
                    else
                    {
                        transaction.type = "Account";
                        transaction.icon = btnIconArrayAccount[indexCategory];
                        transaction.color = ctgColorArrayAccount[indexCategory];
                    }

                    transaction.category = category;
                    transaction.account = account;
                    transaction.description = description;
                    transaction.sum = sum;
                    transaction.date = date;
                    if(editTransaction)
                    {
                        dataFromDB.getTransactionDao().updateCategory(transaction.category, transaction_old.category);
                        dataFromDB.getTransactionDao().updateAccount(transaction.account, transaction_old.category);
                        dataFromDB.getTransactionDao().updateDescription(transaction.description, transaction_old.category);
                        dataFromDB.getTransactionDao().updateSum(transaction.sum, transaction_old.category);
                        dataFromDB.getTransactionDao().updateDate(transaction.date, transaction_old.category);
                        dataFromDB.getTransactionDao().updateIcon(transaction.icon, transaction_old.category);
                        dataFromDB.getTransactionDao().updateColor(transaction.color, transaction_old.category);

                        transactionsList.get(btnIndex).category = transaction.category;
                        transactionsList.get(btnIndex).account = transaction.account;
                        transactionsList.get(btnIndex).description = transaction.description;
                        transactionsList.get(btnIndex).sum = transaction.sum;
                        transactionsList.get(btnIndex).date = transaction.date;
                        transactionsList.get(btnIndex).imgid = transaction.icon;
                        transactionsList.get(btnIndex).colorid = transaction.color;
                    }
                    else {
                        dataFromDB.getTransactionDao().insertAll(transaction);
                        transactionsList.add(new ListData(
                                transaction.type,
                                transaction.category,
                                transaction.account,
                                transaction.date,
                                transaction.description,
                                transaction.sum,
                                transaction.icon,
                                transaction.color
                        ));
                    }
                    listAdapter = new ListAdapter(transactionsList, getApplicationContext(), listener);
                    recyclerView.setAdapter(listAdapter);
                    dataFromDB.getAllTransactions();        // ??? Optimisation

                    double accumulatedSum;
                    double accountSum;
                    if(editTransaction) sum = transaction.sum - transaction_old.sum;
                    if(mState == State.EXPENSE)
                    {
                        dataFromDB.getCategoriesExpense().get(indexCategory).sum_month += sum;
                        dataFromDB.getCategoriesExpense().get(indexCategory).sum_year += sum;
                        dataFromDB.getCategoriesAccounts().get(indexAccount).account_sum -= sum;
                        accumulatedSum = dataFromDB.getCategoriesExpense().get(indexCategory).sum_month;
                        dataFromDB.getCategoryDao().updateSumMonth(accumulatedSum, category);
                        updatePieChart(pieChart, pieDataSetExpense, indexCategory, accumulatedSum);
                        gridModelArrayList.get(indexCategory).setSum((int)dataFromDB.getCategoriesExpense().get(indexCategory).sum_month);
                    }
                    else
                    {
                        dataFromDB.getCategoriesIncome().get(indexCategory).sum_month += sum;
                        dataFromDB.getCategoriesIncome().get(indexCategory).sum_year += sum;
                        dataFromDB.getCategoriesAccounts().get(indexAccount).account_sum += sum;
                        accumulatedSum = dataFromDB.getCategoriesIncome().get(indexCategory).sum_month;
                        dataFromDB.getCategoryDao().updateSumMonth(accumulatedSum, category);
                        updatePieChart(pieChart, pieDataSetIncome, indexCategory, accumulatedSum);
                        gridModelArrayList.get(indexCategory).setSum((int)dataFromDB.getCategoriesIncome().get(indexCategory).sum_month);
                    }
                    // Update account state
                    accountSum = dataFromDB.getCategoriesAccounts().get(indexAccount).account_sum;
                    dataFromDB.getAccountDao().updateAccountSum(accountSum, account);
                    updatePieChart(pieChart, pieDataSetAccount, indexAccount, accountSum);

                    adapter.notifyDataSetChanged();
                    ctgGridView.invalidate();

                    bottomSheetDialog.dismiss();
                }
            }
        });

        btnBottomSheetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    // BottomSheet Account
    private void showBottomSheetAccount(DataFromDB dataFromDB, int btnIndex) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_account);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();

        // Accounts drop down menu
        AutoCompleteTextView autoCompleteTextViewAccount = bottomSheetDialog.findViewById(R.id.actvAccount);
        String[] accounts = getResources().getStringArray(R.array.accounts);
        List<String> accountsList = Arrays.asList(accounts);
        autoCompleteTextViewAccount.setText(accountsList.get(btnIndex));
        ArrayAdapter<String> adapterAccount = new ArrayAdapter<>(
                this, R.layout.drop_down_item, accountsList);
        autoCompleteTextViewAccount.setAdapter(adapterAccount);

        // Transfer accounts drop down menu
        AutoCompleteTextView autoCompleteTextViewAccountDestination = bottomSheetDialog.findViewById(R.id.actvAccountDestination);
        String[] destinationAccounts = getResources().getStringArray(R.array.accounts);
        List<String> destinationAccountsList = Arrays.asList(destinationAccounts);
        autoCompleteTextViewAccountDestination.setText(destinationAccountsList.get(0));
        ArrayAdapter<String> adapterDestinationAccount = new ArrayAdapter<>(
                this, R.layout.drop_down_item, destinationAccountsList);
        autoCompleteTextViewAccountDestination.setAdapter(adapterDestinationAccount);

        TextView tvBottomSheetInitSum = bottomSheetDialog.findViewById(R.id.textViewBottomSheetInitSum);

        TextView tvBottomSheetTransferSum = bottomSheetDialog.findViewById(R.id.textViewBottomSheetTransferSum);
        TextInputLayout tfBottomSheetTransferSum = bottomSheetDialog.findViewById(R.id.textFieldBottomSheetTranferSum);

        Button btnBottomSheetOk = bottomSheetDialog.findViewById(R.id.btnBottomSheetOk);
        Button btnBottomSheetCancel = bottomSheetDialog.findViewById(R.id.btnBottomSheetCancel);

        TextInputLayout textFieldBottomSheetAccountDestination = bottomSheetDialog.findViewById(R.id.textFieldBottomSheetAccountDestination);

        int accountIndex = adapterAccount.getPosition(autoCompleteTextViewAccount.getText().toString());
        tvBottomSheetInitSum.setText(String.valueOf(dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum));

        // Transfer
        Button btnTransfer = bottomSheetDialog.findViewById(R.id.btnBottomSheetTransfer);
        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int accountIndex = adapterAccount.getPosition(autoCompleteTextViewAccount.getText().toString());
                int accountIndexDestination = adapterAccount.getPosition(autoCompleteTextViewAccountDestination.getText().toString());

                textFieldBottomSheetAccountDestination.setErrorEnabled(false);
                tfBottomSheetTransferSum.setErrorEnabled(false);

                if (autoCompleteTextViewAccountDestination.getText().toString().equals(autoCompleteTextViewAccount.getText().toString())) {
                    textFieldBottomSheetAccountDestination.setErrorEnabled(true);
                    textFieldBottomSheetAccountDestination.setError("Account and Destination account must not be the same");
                }
                else if (tvBottomSheetTransferSum.getText().toString().isEmpty())
                {
                    tfBottomSheetTransferSum.setErrorEnabled(true);
                    tfBottomSheetTransferSum.setError("Enter transfer sum");
                }
                else {
                    textFieldBottomSheetAccountDestination.setError(null);
                    textFieldBottomSheetAccountDestination.setErrorEnabled(false);
                    tfBottomSheetTransferSum.setError(null);
                    tfBottomSheetTransferSum.setErrorEnabled(false);

                    double transfer_sum = Double.parseDouble(tvBottomSheetTransferSum.getText().toString());
                    double init_sum = dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum;
                    double init_sum_dest = dataFromDB.getCategoriesAccounts().get(accountIndexDestination).account_sum;

                    dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum = init_sum - transfer_sum;
                    dataFromDB.getCategoriesAccounts().get(accountIndexDestination).account_sum = init_sum_dest + transfer_sum;

                    init_sum = dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum;
                    init_sum_dest = dataFromDB.getCategoriesAccounts().get(accountIndexDestination).account_sum;

                    dataFromDB.getAccountDao().updateAccountSum(init_sum, dataFromDB.getCategoriesAccounts().get(accountIndex).name);
                    dataFromDB.getAccountDao().updateAccountSum(init_sum_dest, dataFromDB.getCategoriesAccounts().get(accountIndexDestination).name);

                    updatePieChart(pieChart, pieDataSetAccount, accountIndex, max(init_sum, 0));
                    updatePieChart(pieChart, pieDataSetAccount, accountIndexDestination, max(init_sum_dest, 0));
                    gridModelArrayList.get(accountIndex).setSum((int) init_sum);
                    gridModelArrayList.get(accountIndexDestination).setSum((int) init_sum_dest);

                    adapter.notifyDataSetChanged();
                    ctgGridView.invalidate();

                    ///

                    String category = "Account";
                    String account = dataFromDB.getCategoriesAccounts().get(accountIndex).name;
                    String accountDest = dataFromDB.getCategoriesAccounts().get(accountIndexDestination).name;
                    String date = "TODO";

                    Transaction transaction = new Transaction();

                    transaction.type = "Transfer";
                    transaction.icon = R.drawable.ic_baseline_category_24;
                    transaction.color = ctgColorArrayAccount[accountIndex];

                    transaction.category = category;
                    transaction.account = account;
                    transaction.description = accountDest;
                    transaction.sum = transfer_sum;
                    transaction.date = date;
                    dataFromDB.getTransactionDao().insertAll(transaction);

                    transactionsList.add(new ListData(
                            transaction.type,
                            transaction.category,
                            transaction.account,
                            transaction.date,
                            transaction.description,
                            transaction.sum,
                            transaction.icon,
                            transaction.color
                    ));
                    listAdapter = new ListAdapter(transactionsList, getApplicationContext(), listener);
                    recyclerView.setAdapter(listAdapter);
                    dataFromDB.getAllTransactions();        // ??? Optimisation

                    Toast.makeText(MainActivity.this, "Transfer completed", Toast.LENGTH_SHORT
                    ).show();

                    bottomSheetDialog.dismiss();
                }
            }
        });

        btnBottomSheetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int accountIndex = adapterAccount.getPosition(autoCompleteTextViewAccount.getText().toString());

                // DB
                double init_sum = dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum;

                if (!tvBottomSheetInitSum.getText().toString().isEmpty() &&
                        !tvBottomSheetInitSum.getText().toString().equals(String.valueOf(init_sum))) {

                    init_sum = Double.parseDouble(tvBottomSheetInitSum.getText().toString());

                    dataFromDB.getAccountDao().updateAccountSum(init_sum, dataFromDB.getCategoriesAccounts().get(accountIndex).name);
                    dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum = init_sum;
                    updatePieChart(pieChart, pieDataSetAccount, accountIndex, max(init_sum, 0));
                    gridModelArrayList.get(accountIndex).setSum((int) dataFromDB.getCategoriesAccounts().get(accountIndex).account_sum);
                }
                adapter.notifyDataSetChanged();
                ctgGridView.invalidate();

                bottomSheetDialog.dismiss();
            }
        });

        btnBottomSheetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    ArrayList<PieEntry> pieChartCategories(DataFromDB dataFromDB, State state){
        ArrayList<PieEntry> pieChartCtg = new ArrayList<>();
        List<Category> categories;
        List<Account> accounts;
        if(state == State.ACCOUNT)
        {
            accounts = dataFromDB.getCategoriesAccounts();
            for(int i = 0; i < accounts.size(); i++)
            {
                pieChartCtg.add(new PieEntry((int)accounts.get(i).account_sum, ""));
            }
        }
        else
        {
            if(state == State.EXPENSE)
                categories = dataFromDB.getCategoriesExpense();
            else
                categories = dataFromDB.getCategoriesIncome();

            for(int i = 0; i < categories.size(); i++)
            {
                pieChartCtg.add(new PieEntry((int)categories.get(i).sum_month, ""));
            }
        }
        return pieChartCtg;
    }

    void setupPieChart(State state)
    {
        int[] colorClassArray;
        PieDataSet pieDataSet;

        if(state == State.EXPENSE) {
            colorClassArray = ctgColorArrayExpense;
            pieDataSet = pieDataSetExpense;
        }
        else if(state == State.INCOME) {
            colorClassArray = ctgColorArrayIncome;
            pieDataSet = pieDataSetIncome;
        }
        else
        {
            colorClassArray = ctgColorArrayAccount;
            pieDataSet = pieDataSetAccount;
        }

        pieChart = findViewById(R.id.pieChart);
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawRoundedSlices(false);
        pieChart.setUsePercentValues(false);
        pieChart.setHoleRadius(65);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10);
        pieDataSet.setSliceSpace(1);
        pieChart.animateX(400);
        pieChart.setTouchEnabled(false);
//        pieChart.setCenterText("Expenses" );
//        pieChart.setCenterTextSize(14f);
//        pieChart.setCenterTextColor(Color.BLACK);
    }

    void updatePieChart(PieChart pieChart, PieDataSet pieDataSet, int entryIndex, double newData)
    {
        pieDataSet.getEntries().set(entryIndex, new PieEntry((int)newData, ""));
        pieChart.notifyDataSetChanged();
        pieChart.animateX(400);
        pieChart.invalidate();
    }

    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.calendar_toolbar_btn) {
            return true;
        }
        // TODO

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        mDate = String.format("%d.%d.%d", day, month+1, year);
        btnPickDate.setText(mDate);
    }
}
