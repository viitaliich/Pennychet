package com.example.pennychet;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import com.example.pennychet.database.AccumulatedExpense;
import com.example.pennychet.database.AccumulatedExpenseDao;
import com.example.pennychet.database.AppDatabase;
import com.example.pennychet.database.DataFromDB;
import com.example.pennychet.database.Expense;
import com.example.pennychet.database.ExpenseDao;
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

    // Main screen category buttons
    TextView tvCategoryT1;
    ImageButton btn_ctg_T1;

    TextView tvCategoryT2;
    ImageButton btn_ctg_T2;

    TextView tvCategoryT3;
    ImageButton btn_ctg_T3;

    TextView tvCategoryT4;
    ImageButton btn_ctg_T4;

    TextView tvCategoryL1;
    ImageButton btn_ctg_L1;

    TextView tvCategoryL2;
    ImageButton btn_ctg_L2;

    TextView tvCategoryR1;
    ImageButton btn_ctg_R1;

    TextView tvCategoryR2;
    ImageButton btn_ctg_R2;

    TextView tvCategoryB1;
    ImageButton btn_ctg_B1;

    TextView tvCategoryB2;
    ImageButton btn_ctg_B2;

    TextView tvCategoryB3;
    ImageButton btn_ctg_B3;

    TextView tvCategoryB4;
    ImageButton btn_ctg_B4;

    // Calendar in category
    Button btnPickDate;

    // Floating Action Button (FAB)
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;

    // Drawer Layout
    private DrawerLayout drawerLayout;

    // PieChart
    PieChart pieChart;
    int[] colorClassArray = new int[]{Color.LTGRAY, Color.CYAN, Color.BLACK, Color.BLUE};
    private ArrayList<PieEntry> dataValues1(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(1, "01"));
        dataVals.add(new PieEntry(2, "02"));
        dataVals.add(new PieEntry(3, "03"));
        dataVals.add(new PieEntry(4, "04"));
        dataVals.add(new PieEntry(5, "05"));
        dataVals.add(new PieEntry(6, "06"));
        dataVals.add(new PieEntry(7, "07"));
        dataVals.add(new PieEntry(8, "08"));
        dataVals.add(new PieEntry(9, "09"));
        return dataVals;
    }

    //DB
    ExpenseDao expenseDao;
    AccumulatedExpenseDao accumulatedExpenseDao;
    List<Expense> expenses;

    // Calculation API

    void getAllDataFromDB(DataFromDB dataFromDB)
    {
        dataFromDB.expensesCategoryT1 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[0]);
        dataFromDB.expensesCategoryT2 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[1]);
        dataFromDB.expensesCategoryT3 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[2]);
        dataFromDB.expensesCategoryT4 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[3]);
        dataFromDB.expensesCategoryL1 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[4]);
        dataFromDB.expensesCategoryL2 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[5]);
        dataFromDB.expensesCategoryR1 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[6]);
        dataFromDB.expensesCategoryR2 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[7]);
        dataFromDB.expensesCategoryB1 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[8]);
        dataFromDB.expensesCategoryB2 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[9]);
        dataFromDB.expensesCategoryB3 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[10]);
        dataFromDB.expensesCategoryB4 = expenseDao.getAllByCategory(dataFromDB.categoriesNames[11]);

        for(int i = 0; i < 12; i++)
        {
            dataFromDB.accumulatedExpensesByCategory[i] =
                    accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[i]);
        }

//        dataFromDB.accumulatedExpensesCategoryT1 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[0]);
//        dataFromDB.accumulatedExpensesCategoryT2 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[1]);
//        dataFromDB.accumulatedExpensesCategoryT3 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[2]);
//        dataFromDB.accumulatedExpensesCategoryT4 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[3]);
//        dataFromDB.accumulatedExpensesCategoryL1 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[4]);
//        dataFromDB.accumulatedExpensesCategoryL2 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[5]);
//        dataFromDB.accumulatedExpensesCategoryR1 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[6]);
//        dataFromDB.accumulatedExpensesCategoryR2 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[7]);
//        dataFromDB.accumulatedExpensesCategoryB1 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[8]);
//        dataFromDB.accumulatedExpensesCategoryB2 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[9]);
//        dataFromDB.accumulatedExpensesCategoryB3 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[10]);
//        dataFromDB.accumulatedExpensesCategoryB4 = accumulatedExpenseDao.getByCategory(dataFromDB.categoriesNames[11]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: [STARTED]");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        // allowMainThreadQueries() not recommended ???
        DataFromDB dataFromDB = new DataFromDB();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Expenses").allowMainThreadQueries().build();
        expenseDao = db.expenseDao();
        accumulatedExpenseDao = db.accumulatedExpenseDao();

        getAllDataFromDB(dataFromDB);

        // PieChart
        pieChart = findViewById(R.id.pieChart);

        PieDataSet pieDataSet = new PieDataSet(dataValues1(), "label");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawRoundedSlices(false);
        pieChart.setHoleRadius(80);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        Description description = pieChart.getDescription();
        description.setEnabled(false);
        pieChart.invalidate();

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

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

        // Main screen categories buttons
        tvCategoryT1 = findViewById(R.id.tv_ctg_T1);
        tvCategoryT2 = findViewById(R.id.tv_ctg_T2);
        tvCategoryT3 = findViewById(R.id.tv_ctg_T3);
        tvCategoryT4 = findViewById(R.id.tv_ctg_T4);
        tvCategoryL1 = findViewById(R.id.tv_ctg_L1);
        tvCategoryL2 = findViewById(R.id.tv_ctg_L2);
        tvCategoryR1 = findViewById(R.id.tv_ctg_R1);
        tvCategoryR2 = findViewById(R.id.tv_ctg_R2);
        tvCategoryB1 = findViewById(R.id.tv_ctg_B1);
        tvCategoryB2 = findViewById(R.id.tv_ctg_B2);
        tvCategoryB3 = findViewById(R.id.tv_ctg_B3);
        tvCategoryB4 = findViewById(R.id.tv_ctg_B4);

        if(dataFromDB.accumulatedExpensesByCategory[0] == null) {
            tvCategoryT1.setText("UAH 0");
        } else
            tvCategoryT1.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[0].sum);

        if(dataFromDB.accumulatedExpensesByCategory[1] == null) {
            tvCategoryT2.setText("UAH 0");
        } else
            tvCategoryT2.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[1].sum);

        if(dataFromDB.accumulatedExpensesByCategory[2] == null) {
            tvCategoryT3.setText("UAH 0");
        } else
            tvCategoryT3.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[2].sum);

        if(dataFromDB.accumulatedExpensesByCategory[3] == null) {
            tvCategoryT4.setText("UAH 0");
        } else
            tvCategoryT4.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[3].sum);

        if(dataFromDB.accumulatedExpensesByCategory[4] == null) {
            tvCategoryL1.setText("UAH 0");
        } else
            tvCategoryL1.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[4].sum);

        if(dataFromDB.accumulatedExpensesByCategory[5] == null) {
            tvCategoryL2.setText("UAH 0");
        } else
            tvCategoryL2.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[5].sum);

        if(dataFromDB.accumulatedExpensesByCategory[6] == null) {
            tvCategoryR1.setText("UAH 0");
        } else
            tvCategoryR1.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[6].sum);

        if(dataFromDB.accumulatedExpensesByCategory[7] == null) {
            tvCategoryR2.setText("UAH 0");
        } else
            tvCategoryR2.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[7].sum);

        if(dataFromDB.accumulatedExpensesByCategory[8] == null) {
            tvCategoryB1.setText("UAH 0");
        } else
            tvCategoryB1.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[8].sum);

        if(dataFromDB.accumulatedExpensesByCategory[9] == null) {
            tvCategoryB2.setText("UAH 0");
        } else
            tvCategoryB2.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[9].sum);

        if(dataFromDB.accumulatedExpensesByCategory[10] == null) {
            tvCategoryB3.setText("UAH 0");
        } else
            tvCategoryB3.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[10].sum);

        if(dataFromDB.accumulatedExpensesByCategory[11] == null) {
            tvCategoryB4.setText("UAH 0");
        } else
            tvCategoryB4.setText("UAH " + dataFromDB.accumulatedExpensesByCategory[11].sum);


        btn_ctg_T1 = findViewById(R.id.btn_ctg_T1);
        btn_ctg_T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 0;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_T2 = findViewById(R.id.btn_ctg_T2);
        btn_ctg_T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 1;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_T3 = findViewById(R.id.btn_ctg_T3);
        btn_ctg_T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 2;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_T4 = findViewById(R.id.btn_ctg_T4);
        btn_ctg_T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 3;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_L1 = findViewById(R.id.btn_ctg_L1);
        btn_ctg_L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 4;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_L2 = findViewById(R.id.btn_ctg_L2);
        btn_ctg_L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 5;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_R1 = findViewById(R.id.btn_ctg_R1);
        btn_ctg_R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 6;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_R2 = findViewById(R.id.btn_ctg_R2);
        btn_ctg_R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 7;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_B1 = findViewById(R.id.btn_ctg_B1);
        btn_ctg_B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 8;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_B2 = findViewById(R.id.btn_ctg_B2);
        btn_ctg_B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 9;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_B3 = findViewById(R.id.btn_ctg_B3);
        btn_ctg_B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 10;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });

        btn_ctg_B4 = findViewById(R.id.btn_ctg_B4);
        btn_ctg_B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnIndex = 11;
                showBottomSheetDialog(dataFromDB, btnIndex);
            }
        });
    }

    // BottomSheet
    private void showBottomSheetDialog(DataFromDB dataFromDB, int btnIndex) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_main);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();

        // Categories drop down menu
        AutoCompleteTextView autoCompleteTextViewCategoty = bottomSheetDialog.findViewById(R.id.actvCategory);
        String[] categories = getResources().getStringArray(R.array.categories);
        List<String> categoriesList = Arrays.asList(categories);
        autoCompleteTextViewCategoty.setText(categoriesList.get(btnIndex));
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(
                this, R.layout.drop_down_item, categoriesList);
        autoCompleteTextViewCategoty.setAdapter(adapterCategory);

        // Accounts drop down menu
        AutoCompleteTextView autoCompleteTextViewAccount = bottomSheetDialog.findViewById(R.id.actvAccount);
        String[] accounts = getResources().getStringArray(R.array.accounts);
        List<String> accountsList = Arrays.asList(accounts);
        autoCompleteTextViewAccount.setText(accountsList.get(0));
        ArrayAdapter<String> adapterAccount = new ArrayAdapter<>(
                this, R.layout.drop_down_item, accountsList);
        autoCompleteTextViewAccount.setAdapter(adapterAccount);

        // Calendar
        btnPickDate = bottomSheetDialog.findViewById(R.id.btnBottomSheetPickDate);
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        btnPickDate.setText(currentDate);
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

        btnBottomSheetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvBottomSheetSum.getText().toString().isEmpty())
                {
                    tfBottomSheetSum.setError("Required field");
                }
                else {
                    tfBottomSheetSum.setError(null);

                    // DB
                    String category = String.valueOf(autoCompleteTextViewCategoty.getText());
                    String account = String.valueOf(autoCompleteTextViewAccount.getText());
                    String description = String.valueOf(tvBottomSheetDescription.getText());
                    double sum = Double.parseDouble(String.valueOf(tvBottomSheetSum.getText()));
                    String date = String.valueOf(btnPickDate.getText());

                    // TODO: API
                    Expense expense = new Expense();
                    expense.category = category;
                    expense.account = account;
                    expense.description = description;
                    expense.sum = sum;
                    expense.date = date;
                    expenseDao.insertAll(expense);

                    double accumulatedSum = 0;
                    if(dataFromDB.accumulatedExpensesByCategory[btnIndex] == null) {
                        Log.i("@@@", "null");
                        AccumulatedExpense accumulatedExpense = new AccumulatedExpense();
                        accumulatedExpense.category = category;
                        accumulatedExpense.sum = sum;
                        accumulatedExpenseDao.insertAll(accumulatedExpense);
                        accumulatedSum += sum;
                    }
                    else
                    {
                        dataFromDB.accumulatedExpensesByCategory[btnIndex].sum += sum;
                        accumulatedSum = dataFromDB.accumulatedExpensesByCategory[btnIndex].sum;
                        accumulatedExpenseDao.updateSum(dataFromDB.accumulatedExpensesByCategory[btnIndex].sum, category);
                    }
                    // Change main screen values
                    switch (btnIndex){
                        case 0:
                            tvCategoryT1.setText("UAH " + accumulatedSum);
                            break;
                        case 1:
                            tvCategoryT2.setText("UAH " + accumulatedSum);
                            break;
                        case 2:
                            tvCategoryT3.setText("UAH " + accumulatedSum);
                            break;
                        case 3:
                            tvCategoryT4.setText("UAH " + accumulatedSum);
                            break;
                        case 4:
                            tvCategoryL1.setText("UAH " + accumulatedSum);
                            break;
                        case 5:
                            tvCategoryL2.setText("UAH " + accumulatedSum);
                            break;
                        case 6:
                            tvCategoryR1.setText("UAH " + accumulatedSum);
                            break;
                        case 7:
                            tvCategoryR2.setText("UAH " + accumulatedSum);
                            break;
                        case 8:
                            tvCategoryB1.setText("UAH " + accumulatedSum);
                            break;
                        case 9:
                            tvCategoryB2.setText("UAH " + accumulatedSum);
                            break;
                        case 10:
                            tvCategoryB3.setText("UAH " + accumulatedSum);
                            break;
                        case 11:
                            tvCategoryB4.setText("UAH " + accumulatedSum);
                            break;
                        default:
                            break;      // ???

                    }

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

    // Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.search_toolbar_btn) {
            return true;
        }
        // TODO

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        btnPickDate.setText(day + "." + (month+1) + "." + year);    // ???
    }
}
