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
import com.example.pennychet.database.AppDatabase;
import com.example.pennychet.database.DataFromDB;
import com.example.pennychet.database.Expense;
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

    String mCurrency = "UAH";

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

    // Calendar button inside category
    Button btnPickDate;
    String mDate;

    // Floating Action Button (FAB)
    FloatingActionButton mAddFab, mAddAlarmFab, mAddPersonFab;
    TextView addAlarmActionText, addPersonActionText;
    Boolean isAllFabsVisible;

    // PieChart
    PieChart pieChart;
    PieDataSet pieDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB
        // allowMainThreadQueries() not recommended ???
        DataFromDB dataFromDB = new DataFromDB();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Expenses").allowMainThreadQueries().build();
        dataFromDB.setExpenseDao(db.expenseDao());
        dataFromDB.setAccumulatedExpenseDao(db.accumulatedExpenseDao());

        dataFromDB.getAllDataFromDB();

        // PieChart
        int[] colorClassArray = new int[]{
                getResources().getColor(R.color.ctgT1),
                getResources().getColor(R.color.ctgT2),
                getResources().getColor(R.color.ctgT3),
                getResources().getColor(R.color.ctgT4),
                getResources().getColor(R.color.ctgL1),
                getResources().getColor(R.color.ctgL2),
                getResources().getColor(R.color.ctgR1),
                getResources().getColor(R.color.ctgR2),
                getResources().getColor(R.color.ctgB1),
                getResources().getColor(R.color.ctgB2),
                getResources().getColor(R.color.ctgB3),
                getResources().getColor(R.color.ctgB4),
        };

        setupPieChart(dataFromDB, colorClassArray);
        pieChart.invalidate();

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

        tvCategoryT1.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[0].sum));
        tvCategoryT2.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[1].sum));
        tvCategoryT3.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[2].sum));
        tvCategoryT4.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[3].sum));
        tvCategoryL1.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[4].sum));
        tvCategoryL2.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[5].sum));
        tvCategoryR1.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[6].sum));
        tvCategoryR2.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[7].sum));
        tvCategoryB1.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[8].sum));
        tvCategoryB2.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[9].sum));
        tvCategoryB3.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[10].sum));
        tvCategoryB4.setText(String.format("%s %s", mCurrency, dataFromDB.getAccumulatedExpensesByCategory()[11].sum));

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
        mDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
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

        btnBottomSheetOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvBottomSheetSum.getText().toString().isEmpty())
                {
                    tfBottomSheetSum.setError("Required field");
                }
                else {
                    tfBottomSheetSum.setError(null);

                    pieChart.notifyDataSetChanged();
                    pieChart.invalidate();

                    // DB
                    String category = String.valueOf(autoCompleteTextViewCategoty.getText());
                    String account = String.valueOf(autoCompleteTextViewAccount.getText());
                    String description = String.valueOf(tvBottomSheetDescription.getText());
                    double sum = Double.parseDouble(String.valueOf(tvBottomSheetSum.getText()));
                    String date = String.valueOf(mDate);

                    Expense expense = new Expense();
                    expense.category = category;
                    expense.account = account;
                    expense.description = description;
                    expense.sum = sum;
                    expense.date = date;
                    dataFromDB.getExpenseDao().insertAll(expense);

                    double accumulatedSum = 0;
                    if(dataFromDB.getAccumulatedExpensesByCategory()[btnIndex] == null) {
                        AccumulatedExpense accumulatedExpense = new AccumulatedExpense();
                        accumulatedExpense.category = category;
                        accumulatedExpense.sum = sum;
                        dataFromDB.getAccumulatedExpenseDao().insertAll(accumulatedExpense);
                        accumulatedSum += sum;
                    }
                    else
                    {
                        dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum += sum;
                        accumulatedSum = dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum;
                        dataFromDB.getAccumulatedExpenseDao().updateSum(dataFromDB.getAccumulatedExpensesByCategory()[btnIndex].sum, category);
                    }

                    updatePieChart(pieChart, pieDataSet, btnIndex, accumulatedSum);

                    // Change main screen values
                    switch (btnIndex){
                        case 0:
                            tvCategoryT1.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 1:
                            tvCategoryT2.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 2:
                            tvCategoryT3.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 3:
                            tvCategoryT4.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 4:
                            tvCategoryL1.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 5:
                            tvCategoryL2.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 6:
                            tvCategoryR1.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 7:
                            tvCategoryR2.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 8:
                            tvCategoryB1.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 9:
                            tvCategoryB2.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 10:
                            tvCategoryB3.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        case 11:
                            tvCategoryB4.setText(String.format("%s %s", mCurrency, accumulatedSum));
                            break;
                        default:
                            Log.e(TAG, "Error in update of accumulated value");
                            break;
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

    ArrayList<PieEntry> pieChartCategories(AccumulatedExpense[] accumulatedExpensesByCategory){
        ArrayList<PieEntry> pieChartCtg = new ArrayList<>();
        for(int i = 0; i < 12; i++)     // number of categories ???
        {
            pieChartCtg.add(new PieEntry((int)accumulatedExpensesByCategory[i].sum, ""));
        }
        return pieChartCtg;
    }

    void setupPieChart(DataFromDB dataFromDB, int[] colorClassArray)
    {
        pieChart = findViewById(R.id.pieChart);
        pieDataSet = new PieDataSet(pieChartCategories(dataFromDB.getAccumulatedExpensesByCategory()), "label");
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
